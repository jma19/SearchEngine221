package com.uci.indexer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by junm5 on 2/25/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TextProcessor.class)
@ComponentScan("com.uci.*")
@AutoConfigureMockMvc
public class BookKeepingFileProcessorTest {

    @Autowired
    private BookKeepingFileProcessor bookKeepingFileProcessor;

    @Test
    public void readFileIntoDocumnet() throws Exception {
        bookKeepingFileProcessor.readFileIntoDocumnet();
    }


}