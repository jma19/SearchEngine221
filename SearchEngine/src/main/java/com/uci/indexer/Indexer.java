package com.uci.indexer;

import com.google.gson.reflect.TypeToken;
import com.uci.io.MyFileReader;
import com.uci.io.MyFileWriter;
import com.uci.mode.IndexEntry;
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

    /**
     * {
     * 'World': [1: [0,5,7]], [3: [2 5 9]]
     * }
     *
     * @param docId
     * @param tokens
     */
    public void indexize(Integer docId, List<String> tokens) {
        Map<String, List<Integer>> posMap = buildPosMap(tokens);
        for (String key : posMap.keySet()) {

            IndexEntry termPos = new IndexEntry(docId);
            termPos.setPos(posMap.get(key));

            List<IndexEntry> termPoseList = indexMap.get(key);
            if (termPoseList == null) {
                termPoseList = new ArrayList();
                indexMap.put(key, termPoseList);
            }
            termPoseList.add(termPos);

        }
    }

    private Map<String, List<Integer>> buildPosMap(List<String> tokens) {
        Map<String, List<Integer>> map = new HashMap<>();
        if (tokens == null || tokens.isEmpty()) {
            return map;
        }
        for (int i = 0; i < tokens.size(); i++) {
            String key = tokens.get(i);
            List<Integer> poses = map.get(key);
            if (poses == null) {
                poses = new ArrayList<>();
                map.put(key, poses);
            }
            poses.add(i);
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
