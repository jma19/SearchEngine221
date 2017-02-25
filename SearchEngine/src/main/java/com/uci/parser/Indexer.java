package com.uci.parser;

import com.google.gson.reflect.TypeToken;
import com.uci.io.MyFileReader;
import com.uci.io.MyFileWriter;
import com.uci.mode.TermPos;
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

    private TreeMap<String, List<TermPos>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
    private String indexFile = SysPathUtil.getSysPath() + "/SearchEngine/conf/index.txt";

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
                    List<TermPos> termPoses = JsonUtils.fromJson(line.substring(splitIndex + 1), new TypeToken<List<TermPos>>() {
                    });
                    indexMap.put(line.substring(0, splitIndex), termPoses);
                }
            }
        } finally {
            fileReader.close();
        }
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

            TermPos termPos = new TermPos(docId);
            termPos.setPos(posMap.get(key));

            List<TermPos> termPoseList = indexMap.get(key);
            if (termPoseList == null) {
                termPoseList = new ArrayList();
                indexMap.put(key, termPoseList);
            }
            termPoseList.add(termPos);

        }
    }

    public Map<String, List<Integer>> buildPosMap(List<String> tokens) {
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
        MyFileWriter.createFile(indexFile);
        MyFileWriter myFileWriter = null;
        try {
            myFileWriter = new MyFileWriter(indexFile, true);
            for (String key : indexMap.keySet()) {
                List<TermPos> termPoses = indexMap.get(key);
                String text = new StringBuffer().append(key).append(":").append(JsonUtils.toJson(termPoses)).toString();
                myFileWriter.writeLine(text);
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
    public List<TermPos> getTermPoses(String term) {
        List<TermPos> termPoses = indexMap.get(term);
        return termPoses == null ? new ArrayList<>() : termPoses;
    }

    public Map<String, Double> caculateTFIDF() {
        return null;
    }

}
