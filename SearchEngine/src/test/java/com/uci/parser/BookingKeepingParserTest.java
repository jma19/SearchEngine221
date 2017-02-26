package com.uci.parser;

import com.uci.indexer.BookingKeepingParser;
import com.uci.mode.URLPath;
import org.junit.Test;
import com.uci.utils.SysPathUtil;

import java.util.List;

/**
 * Created by junm5 on 2/23/17.
 */
public class BookingKeepingParserTest {
    @Test
    public void should_load_url_from_josn_file() throws Exception {
        BookingKeepingParser bookingKeepingParser = new BookingKeepingParser();
        List<URLPath> urlPaths = bookingKeepingParser.loadUrlPaths(SysPathUtil.getSysPath() + "/WEBPAGES_RAW/bookkeeping.json");
        bookingKeepingParser.saveToJosn(urlPaths, SysPathUtil.getSysPath() + "/conf/validUrl.json");
    }

    @Test
    public void name() throws Exception {
        String property = System.getProperty("user.dir");
        System.out.println(property);
    }
}