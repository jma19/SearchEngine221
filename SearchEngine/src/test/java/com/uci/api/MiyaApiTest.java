package com.uci.api;

import com.uci.ServerApplication;
import com.uci.mode.Abstract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by junm5 on 3/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@WebAppConfiguration
public class MiyaApiTest {
    @Autowired
    private MiyaApi miyaApi;

    @Test
    public void should_query_one_word() throws Exception {
        miyaApi.query("ICS");
        List<Abstract> query = miyaApi.query("student affairs");
        System.out.println(query);
    }
}