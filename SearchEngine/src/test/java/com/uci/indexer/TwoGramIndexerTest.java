package com.uci.indexer;

import com.uci.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;


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
        List<String> nGrams = twoGramIndexer.getNGrams("machine learning", 2);
        System.out.println(nGrams);
    }
}