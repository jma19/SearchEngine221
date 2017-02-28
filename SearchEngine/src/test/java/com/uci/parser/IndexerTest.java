package com.uci.parser;

import com.google.common.collect.Lists;
import com.uci.indexer.Indexer;
import com.uci.mode.IndexEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by junm5 on 2/23/17.
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes =  Indexer.class)
public class IndexerTest {
    @Autowired
    private Indexer indexer;
    @Test
    public void should_load_index_into_memeory() throws Exception {
        List<IndexEntry> termPoses = indexer.getIndexEntities("world");
        System.out.println(termPoses);
    }

    @Test
    public void should_index_input_documnet() throws Exception {
        Indexer indexer = new Indexer();
        indexer.indexize(1, buildTestTokens());
        indexer.indexize(2, buildTestTokens());
        indexer.saveIndexes();
    }

    @Test
    public void should_compute_tf_idf() throws Exception {

    }

    private List<String> buildTestTokens() {
        return Lists.newArrayList("hello", "world", "you", "are", "so", "smart");
    }
}