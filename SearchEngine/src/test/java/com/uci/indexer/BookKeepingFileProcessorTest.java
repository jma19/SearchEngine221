package com.uci.indexer;

import com.uci.ServerApplication;
import com.uci.pr.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

    @Autowired
    PageRepository pageRepository;

    @Test
    public void readFileIntoDocumnet() throws Exception {
        bookKeepingFileProcessor.process();
    }

    @Test
    public void should_get_all_page_rank_value() throws Exception {
        for (int i = 0; i < 18660; i++) {
            double prScore = pageRepository.getPrScore(i);
            if(prScore != 1 && prScore != 0){
                System.out.println(i + ":" + prScore);
            }
        }
    }


}