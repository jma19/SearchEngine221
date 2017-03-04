//package com.uci.indexer;
//
//import com.uci.mode.URLPath;
//import com.uci.utils.SysPathUtil;
//import org.junit.Test;
//
//import java.util.List;
//
///**
// * Created by junm5 on 2/25/17.
// */
//public class BookUrlParserTest {
//    @Test
//    public void should_save_parsed_path_urls_into_json_file() throws Exception {
//        BookUrlParser bookingKeepingParser = new BookUrlParser();
//        List<URLPath> urlPaths = bookingKeepingParser.loadUrlPathsFrom(SysPathUtil.getSysPath() + "/WEBPAGES_RAW/bookkeeping.tsv");
//        bookingKeepingParser.saveToJosn(urlPaths, SysPathUtil.getSysPath() + "/conf/validUrl.json");
//    }
//
//    @Test
//    public void name() throws Exception {
//        String property = System.getProperty("user.dir");
//        System.out.println(property);
//    }
//}