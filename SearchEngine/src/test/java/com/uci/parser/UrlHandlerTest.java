package com.uci.parser;

import com.uci.indexer.UrlHandler;
import com.uci.mode.URLPath;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by junm5 on 2/23/17.
 */
public class UrlHandlerTest {
    private String prefix = "WEBPAGES_RAW/";
    @Test
    public void should_load_url_from_josn_file() throws Exception {
        UrlHandler handler = new UrlHandler();
        assertThat(handler.size(), is(20730));
    }

    @Test
    public void should_pr() throws Exception {
        UrlHandler urlHandler = new UrlHandler();
        while (urlHandler.hashNext()){
            URLPath next = urlHandler.next();
            String path = prefix + next.getPath();
            System.out.println(path);

        }
    }
}