package com.uci.indexer;

import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.constant.Tag;
import com.uci.db.DBHandler;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import com.uci.mode.IndexEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by junm5 on 3/11/17.
 */
public abstract class Indexer {
    private Table table;
    @Autowired
    private DBHandler dbHandler;
    private int docSize;

    public void setTable(Table table) {
        this.table = table;
    }

    TreeMap<String, List<IndexEntry>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

    public abstract void indexize(Document document);

    public List<IndexEntry> getIndexEntities(String term) {
        List<IndexEntry> termPoses = dbHandler.get(table, term, List.class);
        return termPoses == null ? new ArrayList<>() : termPoses;
    }

    protected Map<String, Set<BaseEntry>> join(List<Map<String, BaseEntry>> maps) {
        Map<String, Set<BaseEntry>> res = new HashMap<>();
        for (Map<String, BaseEntry> map : maps) {
            join(res, map);
        }
        return res;
    }

    protected void updateIndexMap(int id, List<Map<String, BaseEntry>> maps) {
        Map<String, Set<BaseEntry>> res = new HashMap<>();
        for (Map<String, BaseEntry> map : maps) {
            join(res, map);
        }
        for (String key : res.keySet()) {
            IndexEntry termPos = new IndexEntry(id);
            termPos.getBaseEntries().addAll(res.get(key));
            List<IndexEntry> termPoseList = indexMap.get(key);
            if (termPoseList == null) {
                termPoseList = new ArrayList<>();
                indexMap.put(key, termPoseList);
            }
            termPoseList.add(termPos);
        }
    }

    protected void join(Map<String, Set<BaseEntry>> map1, Map<String, BaseEntry> map2) {
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

    protected Map<String, BaseEntry> buildPosMap(Tag tag, List<String> tokens) {
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
            baseEntry.setTermFre(baseEntry.getTermFre() + 1);
        }
        return map;
    }


    public void saveIndexesToRedis() {
        for (String key : indexMap.keySet()) {
            System.out.println("storing term: " + key);
            dbHandler.put(table, key, indexMap.get(key));
        }
        System.out.println("index number: " + indexMap.size());
        System.out.println("save indexes to redis success!!!");
        indexMap.clear();
    }

    public double getIDF(String term) {
        if (docSize == 0) {
            docSize = dbHandler.get(Table.DOCUMENT, Constant.SIZE, Integer.class);
        }
        List<IndexEntry> indexEntries = (List<IndexEntry>) dbHandler.get(table, term, List.class);
        if (indexEntries == null || indexEntries.isEmpty()) {
            System.out.println("return empty list !!!");
            return 0;
        }
        return Math.log10((double) docSize / indexEntries.size());
    }

    public boolean contains(String index) {
        return dbHandler.containsKey(table, index);
    }


    public void calculateTFIDF() {
        if (docSize == 0) {
            docSize = dbHandler.get(Table.DOCUMENT, Constant.SIZE, Integer.class);
        }
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
