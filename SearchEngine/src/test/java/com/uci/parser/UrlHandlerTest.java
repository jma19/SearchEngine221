//package com.uci.parser;
//
//import com.uci.indexer.BookUrlRepository;
//import com.uci.mode.URLPath;
//import org.junit.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//
///**
// * Created by junm5 on 2/23/17.
// */
//public class UrlHandlerTest {
//    private String prefix = "WEBPAGES_RAW/";
//    @Test
//    public void should_load_url_from_josn_file() throws Exception {
//        BookUrlRepository handler = new BookUrlRepository();
//        assertThat(handler.size(), is(20730));
//    }
//
//    @Test
//    public void should_pr() throws Exception {
//        BookUrlRepository urlHandler = new BookUrlRepository();
//        while (urlHandler.hashNext()){
//            URLPath next = urlHandler.next();
//            String path = prefix + next.getPath();
//            System.out.println(path);
//
//        }
//    }
//
//    @Test
//    public void should_split_url() throws Exception {
//        String x = "123#####";
//        String str = "#####";
//        int x1 = x.indexOf(str);
//        System.out.println(x.substring(0, x1));
//        System.out.println(x.substring(x1 + str.length()));
//    }
//}