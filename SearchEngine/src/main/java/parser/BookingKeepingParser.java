package parser;

import io.MyFileReader;
import io.MyFileWriter;
import mode.URLPath;
import utils.JsonUtils;
import utils.UrlChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/23/17.
 */
public class BookingKeepingParser {

    public List<URLPath> loadUrlPaths(String filePath) {
        MyFileReader myFileReader = null;
        try {
            myFileReader = new MyFileReader(filePath);
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
            return urlPaths.stream()
                    .filter(urlPath -> UrlChecker.isValid(urlPath.getUrl()))
                    .map(urlPath -> urlPath.setUrl("http://" + urlPath.getUrl()))
                    .collect(Collectors.toList());
        } finally {
            myFileReader.close();
        }
    }

    public void saveToJosn(List<URLPath> urlPaths, String validUrl) {
        System.out.println(urlPaths.size());
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
}
