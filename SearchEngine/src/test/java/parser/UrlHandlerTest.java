package parser;

import mode.URLPath;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by junm5 on 2/23/17.
 */
public class UrlHandlerTest {
    @Test
    public void should_load_url_from_josn_file() throws Exception {
        UrlHandler handler = new UrlHandler();
        while (handler.hashNext()){
            URLPath next = handler.next();
            System.out.println(next);
        }
    }
}