package com.uci.parser;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by junm5 on 2/24/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =  TextProcessor.class)
public class TextProcessorTest {

    @Autowired
    private TextProcessor textProcessor;

    @Test
    public void should_test_filter_stop_words() throws Exception {
        List<String> list = Lists.newArrayList("a", "application", "brought");
    }

}