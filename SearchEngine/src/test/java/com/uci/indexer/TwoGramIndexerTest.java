package com.uci.indexer;

import com.uci.ServerApplication;
import com.uci.mode.*;
import com.uci.mode.IndexEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by junm5 on 3/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class TwoGramIndexerTest {
    @Autowired
    private TwoGramIndexer twoGramIndexer;

    @Test
    public void should_generate_two_gram() throws Exception {
        List<String> nGrams = twoGramIndexer.getNGrams("graduate course", 2);
        List<com.uci.mode.IndexEntry> indexEntities =
                twoGramIndexer.getIndexEntities(nGrams.get(0));
        List<IndexEntry> collect = indexEntities.stream().sorted((o1, o2) -> {
            double v = o2.getTfIdf() - o2.getTfIdf();
            return v == 0 ? 0 : ((v > 0) ? 1 : -1);
        }).collect(Collectors.toList());
        System.out.println(collect);
    }
}