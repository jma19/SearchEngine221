package parser;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by junm5 on 2/23/17.
 */
public class IndexerTest {

    @Test
    public void should_index_input_documnet() throws Exception {
        Indexer indexer = new Indexer();
        indexer.indexize(1, buildTestTokens());
        indexer.indexize(2, buildTestTokens());
        indexer.saveIndexes();
    }

    private List<String> buildTestTokens() {
        return Lists.newArrayList("hello", "world", "you", "are", "so", "smart");
    }
}