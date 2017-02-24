package parser;

import io.MyFileReader;
import io.MyFileWriter;
import mode.TermPos;
import mode.URLPath;
import org.springframework.stereotype.Service;
import utils.JsonUtils;
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
    private String validUrl = "WEBPAGES_RAW/validUrl.json";

    private Iterator<URLPath> iterator;

    public UrlHandler() {
        try {
            this.myFileReader = new MyFileReader(filePath);
            List<URLPath> urlPaths = new ArrayList<>();
            String urls = myFileReader.readAll().trim().replace("\n", "");
            String[] entites = urls.split(",");
            for (int i = 0; i < entites.length; i++) {
                String trim = entites[i].trim();
                if (!trim.isEmpty()) {
                    String[] split = trim.split(":");
                    if (split.length == 2) {
                        urlPaths.add(new URLPath(split[0].trim(), split[1].trim()));
                    }

                }
            }
            List<URLPath> collect = urlPaths.stream()
                    .filter(urlPath -> UrlChecker.isValid(urlPath.getUrl())).collect(Collectors.toList());
            iterator = collect.iterator();
            saveToJosn(collect);
        } finally {
            myFileReader.close();
        }
    }

    public void saveToJosn(List<URLPath> urlPaths) {
        MyFileWriter.createFile(validUrl);
        MyFileWriter myFileWriter = null;
        try {
            myFileWriter = new MyFileWriter(validUrl, true);
            String res = JsonUtils.toJson(urlPaths);
            myFileWriter.writeLine(res);
            myFileWriter.flush();

        } finally {
            myFileWriter.close();
        }
    }

    public boolean hashNext() {
        return iterator.hasNext();
    }

    public URLPath next() {
        return iterator.next();
    }
}
