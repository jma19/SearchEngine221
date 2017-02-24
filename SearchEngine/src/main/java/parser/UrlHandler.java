package parser;

import io.MyFileReader;
import mode.URLPath;
import org.springframework.stereotype.Service;
import utils.UrlChecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/22/17.
 */
@Service
public class UrlHandler {
    private MyFileReader myFileReader;
    private String filePath = "WEBPAGES_RAW/bookkeeping.json";
    private Iterator<URLPath> iterator;


    public UrlHandler() {
        try {
            this.myFileReader = new MyFileReader(filePath);
            List<URLPath> urlPaths = new ArrayList<>();
            String urls = myFileReader.readAll();
            String[] entites = urls.split(",");
            for (int i = 0; i < entites.length; i++) {
                if (!entites[i].trim().isEmpty()) {
                    String[] split = entites[i].trim().split(":");
                    urlPaths.add(new URLPath(split[0].trim(), split[1].trim()));
                }
            }

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
