package com.uci.indexer;

import com.google.gson.reflect.TypeToken;
import com.uci.io.MyFileReader;
import com.uci.mode.URLPath;
import org.springframework.stereotype.Service;
import com.uci.utils.JsonUtils;
import com.uci.utils.SysPathUtil;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
@Service
public class BookUrlRepository {
    private MyFileReader myFileReader;
    private String validUrl = StopWordsFilter.class.getClassLoader().getResource("validUrl.json").getPath();

    private Iterator<URLPath> iterator;
    private int size = 0;
    List<URLPath> urlPaths;

    @PostConstruct
    private void loadUrls() {
        System.out.println("read the file with path: " + validUrl);
        try {
            this.myFileReader = new MyFileReader(validUrl);
            String urls = myFileReader.readAll();
            urlPaths = JsonUtils.fromJson(urls, new TypeToken<List<URLPath>>() {
            });
            urlPaths.sort((o1, o2) -> o1.getPath().compareTo(o2.getPath()));
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

    public List<URLPath> getURLPaths() {
        return urlPaths;
    }
}
