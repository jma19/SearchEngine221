package com.uci.indexer;

import com.google.common.base.Strings;
import com.google.gson.reflect.TypeToken;
import com.uci.constant.Tag;
import com.uci.io.MyFileReader;
import com.uci.io.MyFileWriter;
import com.uci.mode.IndexEntry;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.uci.utils.JsonUtils;
import com.uci.utils.SysPathUtil;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
@Component
public class Indexer {

    @Autowired
    private TextProcessor textProcessor;

    private TreeMap<String, List<IndexEntry>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
    //    private String indexFile = SysPathUtil.getSysPath() + "/SearchEngine/conf/index.txt";
    private String indexFile = SysPathUtil.getSysPath() + "/conf/index.txt";

    /**
     * load index into memory
     */
    @PostConstruct
    public void loadIndexes() {
        MyFileReader fileReader = null;
        try {
            fileReader = new MyFileReader(indexFile);
            String line;
            while ((line = fileReader.readLines()) != null) {
                if (!line.isEmpty()) {
                    int splitIndex = line.indexOf(":");
                    List<IndexEntry> termPoses = JsonUtils.fromJson(line.substring(splitIndex + 1), new TypeToken<List<IndexEntry>>() {
                    });
                    indexMap.put(line.substring(0, splitIndex), termPoses);
                }
            }
        } finally {
            fileReader.close();
        }
    }

    public boolean contains(String index) {
        return indexMap.containsKey(index);
    }

    public List<IndexEntry> getIndexEntity(String term) {
        return indexMap.get(term);
    }


    public void indexize(Document document) {
        Map<String, BaseEntry> posTitleMap = getEntryMap(Tag.TITLE, document.getTitle());
        Map<String, BaseEntry> posAnchorMap = getEntryMap(Tag.ANCHOR, document.getAnchorText());
        Map<String, BaseEntry> posBodyMap = getEntryMap(Tag.BODY, document.getBody());
        Map<String, Set<BaseEntry>> res = join(posTitleMap, posAnchorMap, posBodyMap);

        for (String key : res.keySet()) {
            IndexEntry termPos = new IndexEntry(document.getId());
            termPos.getBaseEntries().addAll(res.get(key));
            List<IndexEntry> termPoseList = indexMap.get(key);
            if (termPoseList == null) {
                termPoseList = new ArrayList<>();
                indexMap.put(key, termPoseList);
            }
            termPoseList.add(termPos);
        }

    }

    private Map<String, Set<BaseEntry>> join(Map<String, BaseEntry> map1,
                                             Map<String, BaseEntry> map2,
                                             Map<String, BaseEntry> map3) {
        Map<String, Set<BaseEntry>> maps = new HashMap<>();
        join(maps, map1);
        join(maps, map2);
        join(maps, map3);
        return maps;
    }

    private void join(Map<String, Set<BaseEntry>> map1, Map<String, BaseEntry> map2) {
        if (map2.isEmpty()) {
            return;
        }
        for (String key : map2.keySet()) {
            Set<BaseEntry> baseEntries = map1.get(key);
            if (baseEntries == null) {
                baseEntries = new HashSet<>();
                map1.put(key, baseEntries);
            }
            baseEntries.add(map2.get(key));
        }
    }

    private Map<String, BaseEntry> getEntryMap(Tag tag, String text) {
        if (Strings.isNullOrEmpty(text)) {
            return new HashMap<>();
        }
        List<String> tokens = getTokens(text);
        return buildPosMap(tag, tokens);
    }

    private List<String> getTokens(String title) {
        List<String> tokens = textProcessor.getTokens(title);
        return textProcessor.stemstop(tokens);
    }

    private Map<String, BaseEntry> buildPosMap(Tag tag, List<String> tokens) {
        Map<String, BaseEntry> map = new HashMap<>();
        if (tokens == null || tokens.isEmpty()) {
            return map;
        }
        for (int i = 0; i < tokens.size(); i++) {
            String key = tokens.get(i);
            BaseEntry baseEntry = map.get(key);
            if (baseEntry == null) {
                baseEntry = new BaseEntry().setTag(tag);
                map.put(key, baseEntry);
            }
            baseEntry.getPos().add(i);
        }
        return map;
    }

    public void saveIndexes() {
        System.out.println("index number: " + indexMap.size());
        MyFileWriter.createFile(indexFile);
        MyFileWriter myFileWriter = null;
        try {
            myFileWriter = new MyFileWriter(indexFile, true);
            for (String key : indexMap.keySet()) {
                List<IndexEntry> termPoses = indexMap.get(key);
                String text = new StringBuffer().append(key).append(":").append(JsonUtils.toJson(termPoses)).toString();
                myFileWriter.writeLine(text);
                myFileWriter.flush();
                System.out.println("storing term : " + key);
            }
            myFileWriter.flush();

        } finally {
            myFileWriter.close();
        }
    }

    /**
     * @param term
     * @return
     */
    public List<IndexEntry> getIndexEntities(String term) {
        List<IndexEntry> termPoses = indexMap.get(term);
        return termPoses == null ? new ArrayList<>() : termPoses;
    }

    public void calculateTFIDF() {
        int docSize = indexMap.size();
        Set<String> keySet = indexMap.keySet();
        for (String key : keySet) {
            List<IndexEntry> indexEntries = indexMap.get(key);
            int df = indexEntries.size();
            for (IndexEntry indexEntry : indexEntries) {
                int tf = indexEntry.getTermFre();
                double score = (1 + Math.log10(tf)) * Math.log10((double) docSize / df);
                indexEntry.setTfIdf(score);
            }
        }

    }

}
