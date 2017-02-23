package parser;

import com.google.gson.reflect.TypeToken;
import io.MyFileReader;
import io.MyFileWriter;
import mode.TermPos;
import utils.JsonUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
public class Indexer {

    private TreeMap<String, List<TermPos>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

    private String indexFile = "index.txt";

    /**
     * load index into memory
     */
    @PostConstruct
    private void loadIndexes() {
        MyFileReader fileReader = null;
        try {
            fileReader = new MyFileReader(indexFile);
            String line;
            while ((line = fileReader.readLines()) != null) {
                if (!line.isEmpty()) {
                    String[] split = line.split(":");
                    List<TermPos> termPoses = JsonUtils.fromJson(split[1], new TypeToken<List<TermPos>>() {
                    });
                    indexMap.put(split[0], termPoses);
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
            if (termPos == null) {
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
        MyFileWriter.createFile(indexFile);
        MyFileWriter myFileWriter = null;
        try {
            myFileWriter = new MyFileWriter(indexFile, true);
            for (String key : indexMap.keySet()) {
                List<TermPos> termPoses = indexMap.get(key);
                String text = new StringBuffer().append(key).append(":").append(JsonUtils.toJson(termPoses)).toString();
                myFileWriter.writeLine(text);
            }
        } finally {
            myFileWriter.close();
        }

    }

}
