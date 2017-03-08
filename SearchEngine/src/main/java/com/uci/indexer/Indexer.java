package com.uci.indexer;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.google.common.base.Strings;
import com.google.common.math.BigIntegerMath;
import com.google.gson.reflect.TypeToken;
import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.constant.Tag;
import com.uci.io.MyFileReader;
import com.uci.io.MyFileWriter;
import com.uci.mode.IndexEntry;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import com.uci.db.DBHandler;
import com.uci.mode.Page;
import javafx.scene.control.Tab;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.uci.utils.JsonUtils;
import com.uci.utils.SysPathUtil;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
@Component
public class Indexer {

    @Autowired
    private TextProcessor textProcessor;

    private TreeMap<String, List<IndexEntry>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
    private String indexFile = StopWordsFilter.class.getClassLoader().getResource("index.txt").getPath();


    @Autowired
    private DBHandler dbHandler;

    private Integer docSize;

    private Log logger = LogFactory.getLog(Indexer.class);


    /**
     * load index into memory
     */
//    @PostConstruct
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
        return dbHandler.containsKey(Table.TERM, index);
    }

    public List<IndexEntry> getIndexEntity(String term) {
        List<IndexEntry> list = (List<IndexEntry>) dbHandler.get(Table.TERM, term, List.class);
        return list;
//        return indexMap.get(term);
    }

    public void indexize(Document document) {
        Map<String, BaseEntry> posTitleMap = getEntryMap(Tag.TITLE, document.getTitle());
        Map<String, BaseEntry> posAnchorMap = getEntryMap(Tag.ANCHOR, document.getAnchorText());
        Map<String, BaseEntry> posBodyMap = getEntryMap(Tag.BODY, document.getBody());
        Map<String, BaseEntry> posUrlMap = getEntryMap(Tag.URL, document.getUrl());

        Map<String, Set<BaseEntry>> res = join(posTitleMap, posAnchorMap, posBodyMap, posUrlMap);

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
                                             Map<String, BaseEntry> map3,
                                             Map<String, BaseEntry> map4) {
        Map<String, Set<BaseEntry>> maps = new HashMap<>();
        join(maps, map1);
        join(maps, map2);
        join(maps, map3);
        join(maps, map4);
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
        List<String> tokens = getTokens(tag, text);
        return buildPosMap(tag, tokens);
    }

    private List<String> getTokens(Tag tag, String contxt) {
        List<String> tokens = (tag == Tag.URL) ? textProcessor.getTokensByUrl(contxt)
                : textProcessor.getTokens(contxt);
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

    public void saveIndexesToFiles() {
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

    public void saveIndexesToRedis() {
        System.out.println("index number: " + indexMap.size());
        for (String key : indexMap.keySet()) {
            System.out.println("storing term: " + key);
            dbHandler.put(Table.TERM, key, indexMap.get(key));
        }
        indexMap.clear();
        System.out.println("save indexes to redis success!!!");
    }


    /**
     * @param term
     * @return
     */
    public List<IndexEntry> getIndexEntities(String term) {
        List<IndexEntry> termPoses = dbHandler.get(Table.TERM, term, List.class);
        return termPoses == null ? new ArrayList<>() : termPoses;
    }

    public void calculateTFIDF() {
        docSize = dbHandler.get(Table.DOCUMENT, Constant.SIZE, Integer.class);
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

    public double getIDF(String term) {
        if (docSize == null) {
            docSize = dbHandler.get(Table.DOCUMENT, Constant.SIZE, Integer.class);
            logger.info(String.format("document size = %d", docSize));
        }
        List<IndexEntry> indexEntries = (List<IndexEntry>) dbHandler.get(Table.TERM, term, List.class);
        if (indexEntries == null || indexEntries.isEmpty()) {
            logger.info(String.format("term=%s is empty", term));
            return 0;
        }
        return Math.log10((double) docSize / indexEntries.size());
    }
}
