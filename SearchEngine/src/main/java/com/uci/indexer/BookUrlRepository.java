package com.uci.indexer;

import com.google.gson.reflect.TypeToken;
import com.uci.io.MyFileReader;
import com.uci.mode.URLPath;
import org.springframework.stereotype.Service;
import com.uci.utils.JsonUtils;
import com.uci.utils.SysPathUtil;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
@Service
public class BookUrlRepository {
    private MyFileReader myFileReader;
    private String validUrl = SysPathUtil.getSysPath() + "/conf/validUrl.json";

    private Iterator<URLPath> iterator;
    private int size = 0;

    @PostConstruct
    private void loadUrls(){
        System.out.println("read the file with path: "+validUrl);
        try {
            this.myFileReader = new MyFileReader(validUrl);
            String urls = myFileReader.readAll();
            List<URLPath> urlPaths = JsonUtils.fromJson(urls, new TypeToken<List<URLPath>>() {
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
}
