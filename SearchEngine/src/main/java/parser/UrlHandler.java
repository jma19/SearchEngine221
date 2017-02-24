package parser;

import com.google.gson.reflect.TypeToken;
import io.MyFileReader;
import mode.URLPath;
import org.springframework.stereotype.Service;
import utils.JsonUtils;
import utils.UrlChecker;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/22/17.
 */
@Service
public class UrlHandler {
    private MyFileReader myFileReader;
    private String filePath = "bookkeeping.json";
    private Iterator<URLPath> iterator;


    public UrlHandler() {
        try {
            this.myFileReader = new MyFileReader(filePath);
            String json = myFileReader.readAll();
            List<URLPath> urlPaths = JsonUtils.fromJson(json, new TypeToken<List<URLPath>>() {
            });
            List<URLPath> collect = urlPaths.stream().filter(urlPath -> !UrlChecker.isValid(urlPath.getUrl())).collect(Collectors.toList());
            iterator = collect.iterator();
        } finally {
            myFileReader.close();
        }
    }

    public boolean hashNext() {
        return iterator.hasNext();
    }

    public URLPath next() {
        return iterator.next();
    }
}
