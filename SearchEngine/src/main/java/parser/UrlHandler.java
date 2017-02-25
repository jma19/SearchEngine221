package parser;

import com.google.gson.reflect.TypeToken;
import io.MyFileReader;
import mode.URLPath;
import org.springframework.stereotype.Service;
import utils.JsonUtils;
import utils.SysPathUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
@Service
public class UrlHandler {
    private MyFileReader myFileReader;
    private String validUrl = SysPathUtil.getSysPath() + "conf/validUrl.json";

    private Iterator<URLPath> iterator;
    private int size = 0;

    public UrlHandler() {
        try {
            this.myFileReader = new MyFileReader(validUrl);
            String urls = myFileReader.readAll();
            List<URLPath> urlPaths = JsonUtils.fromJson(urls, new TypeToken<List<URLPath>>() {
            });
            size = urlPaths.size();
            iterator = urlPaths.iterator();
        } finally {
            myFileReader.close();
        }
    }

    public int size() {
        return size;
    }

    public boolean hashNext() {
        return iterator.hasNext();
    }

    public URLPath next() {
        return iterator.next();
    }
}
