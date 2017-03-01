package com.uci.indexer;

import com.uci.ServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Created by junm5 on 3/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class AnchorTextProcessorTest {
    @Autowired
    private AnchorTextProcessor anchorTextProcessor;
    @Test
    public void should_generate_anchor_text_and_save_into_files() throws Exception {
        anchorTextProcessor.parseAnchorTextFromFile();
        anchorTextProcessor.saveIntoRedis();
    }

    @Test
    public void should_get_anchor_text() throws Exception {
        String anchorText = anchorTextProcessor.getAnchorTextFromRedis("http://vision.ics.uci.edu/people/30.html");
        assertThat(anchorText != null, is(true));
        System.out.println(anchorText);
    }

    @Test
    public void clear_all_anchor_text() throws Exception {

    }
}