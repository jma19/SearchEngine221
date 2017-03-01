package com.uci.service;

import com.uci.ServerApplication;
import com.uci.mode.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by junm5 on 2/27/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class DBRepositoryTest {
    @Autowired
    private DBHandler cacheManager;

    @Test
    public void should_add_and_get_document() throws Exception {
        Document document = new Document().setId(1).setUrl("http://www.uci.com").setText("I want to be a tester").setTitle("This is a Test");
//        cacheManager.put("1", document);
        Document document1 = cacheManager.get("9166", Document.class);
        System.out.println(document1);
    }
}