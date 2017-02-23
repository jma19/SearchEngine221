package mode;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junm5 on 2/22/17.
 */
public class IndexManager {
    private Map<String, Index> indexMap = new HashMap<>();

    @PostConstruct
    private void loadData(){

    }

    public void addIndex(String key, int docId, int pos){

    }

    private void saveData(){
    }
}
