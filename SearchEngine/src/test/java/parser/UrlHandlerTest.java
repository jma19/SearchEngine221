package parser;

import mode.URLPath;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by junm5 on 2/23/17.
 */
public class UrlHandlerTest {
    @Test
    public void should_load_url_from_josn_file() throws Exception {
        UrlHandler handler = new UrlHandler();
        assertThat(handler.size(), is(18637));
    }
}