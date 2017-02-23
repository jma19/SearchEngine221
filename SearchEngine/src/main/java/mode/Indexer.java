package mode;

import parser.TextProcessor;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
public class Indexer {

    private TreeMap<String, List<TermPos>> indexMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));

    @PostConstruct
    private void loadData() {

    }

    public void indexize(Integer docId, List<String> tokens) {
        Map<String, List<Integer>> posMap = buildPosMap(tokens);
        for (String key : posMap.keySet()) {

            TermPos termPos = new TermPos(docId);
            termPos.setPos(posMap.get(key));

            List<TermPos> termPoseList = indexMap.get(key);
            if(termPos == null){
                termPoseList = new ArrayList();
                indexMap.put(key,termPoseList);
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
}
