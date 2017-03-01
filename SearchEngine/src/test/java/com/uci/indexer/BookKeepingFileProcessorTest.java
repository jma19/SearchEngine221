package com.uci.indexer;

import com.uci.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by junm5 on 2/25/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class BookKeepingFileProcessorTest {

    @Autowired
    private BookKeepingFileProcessor bookKeepingFileProcessor;


    @Test
    public void readFileIntoDocumnet() throws Exception {
        bookKeepingFileProcessor.readFileIntoDocument();
    }



}