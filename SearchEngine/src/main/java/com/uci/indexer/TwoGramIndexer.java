package com.uci.indexer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.constant.Tag;
import com.uci.db.DBHandler;
import com.uci.io.MyFileWriter;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import com.uci.mode.IndexEntry;
import com.uci.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
@Component
public class TwoGramIndexer {

    @Autowired
    private TextProcessor textProcessor;

    private TreeMap<String, List<IndexEntry>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
    private String indexFile = StopWordsFilter.class.getClassLoader().getResource("index.txt").getPath();


    @Autowired
    private DBHandler dbHandler;

    private Integer docSize;

    private Log logger = LogFactory.getLog(TwoGramIndexer.class);

    /**
     * load index into memory
     */
    public boolean contains(String index) {
        return dbHandler.containsKey(Table.TERM, index);
    }

    public List<IndexEntry> getIndexEntity(String term) {
        List<IndexEntry> list = (List<IndexEntry>) dbHandler.get(Table.TERM, term, List.class);
        return list;
    }

    public void indexize(Document document) {
        Map<String, BaseEntry> posTitleTwoGramMap = getEntryMap(Tag.TWOGRAM_TITLE, document.getTitle());
        Map<String, BaseEntry> posBodyTwoGramMap = getEntryMap(Tag.TWOGRAM_BODY, document.getBody());

        List<Map<String, BaseEntry>> maps = Lists.newArrayList(posTitleTwoGramMap, posBodyTwoGramMap);
        Map<String, Set<BaseEntry>> res = join(maps);

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

    private Map<String, Set<BaseEntry>> join(List<Map<String, BaseEntry>> maps) {
        Map<String, Set<BaseEntry>> res = new HashMap<>();
        for (Map<String, BaseEntry> map : maps) {
            join(res, map);
        }
        return res;
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
        List<String> tokens;
        if (tag == Tag.TWOGRAM_TITLE || tag == Tag.TWOGRAM_BODY) {
            tokens = getNGrams(text, 2);
        } else {
            tokens = getTokens(tag, text);
        }
        return buildPosMap(tag, tokens);
    }

    private List<String> getTokens(Tag tag, String contxt) {
        List<String> tokens = (tag == Tag.URL) ? textProcessor.getTokensByUrl(contxt)
                : textProcessor.getTokens(contxt);
        return textProcessor.stemstop(tokens);
    }

    public List<String> getNGrams(String contxt, int n) {
        List<String> tokens = textProcessor.getTokens(contxt);
        tokens = textProcessor.stemstop(tokens);
        return getNGrams(tokens, n);
    }

    public List<String> getNGrams(List<String> tokens, int n) {
        List<String> res = new ArrayList();
        for (int i = n - 1; i < tokens.size(); i++) {
            StringBuffer temp = new StringBuffer();
            for (int j = i - n + 1; j <= i; j++) {
                temp.append(tokens.get(j));
            }
            res.add(temp.toString());
        }
        return res;
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

    public void saveIndexesToRedis() {
        for (String key : indexMap.keySet()) {
            System.out.println("storing term: " + key);
            dbHandler.put(Table.TERM, key, indexMap.get(key));
        }
        indexMap.clear();
        System.out.println("index number: " + indexMap.size());
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

}
