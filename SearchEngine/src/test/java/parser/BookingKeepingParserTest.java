package parser;

import mode.URLPath;
import org.junit.Test;

import java.util.List;

/**
 * Created by junm5 on 2/23/17.
 */
public class BookingKeepingParserTest {
    @Test
    public void should_load_url_from_josn_file() throws Exception {
        BookingKeepingParser bookingKeepingParser = new BookingKeepingParser();
        List<URLPath> urlPaths = bookingKeepingParser.loadUrlPaths("/Users/junm5/workplace/SearchEngine221/WEBPAGES_RAW/bookkeeping.json");
        bookingKeepingParser.saveToJosn(urlPaths, "/Users/junm5/workplace/SearchEngine221/WEBPAGES_RAW/validUrl.json");
    }
}