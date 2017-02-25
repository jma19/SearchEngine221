package com.uci.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by junm5 on 2/24/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes =  StopWordsFilter.class)
@AutoConfigureMockMvc
public class StopWordsFilterTest {
    @Autowired
    private StopWordsFilter stopWordsFilter;

    @Test
    public void testName() throws Exception {
        Set<String> stopWordList = stopWordsFilter.getStopWordList();
        System.out.println(stopWordList);
    }
}