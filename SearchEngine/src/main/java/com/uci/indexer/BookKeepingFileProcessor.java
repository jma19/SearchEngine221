package com.uci.indexer;

import com.uci.io.MyFileReader;
import com.uci.mode.URLPath;
import com.uci.utils.SysPathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by junm5 on 2/25/17.
 */
@Service
public class BookKeepingFileProcessor {

    @Autowired
    private BookUrlRepository bookUrlRepository;

    @Autowired
    private Indexer indexer;

    @Autowired
    private TextProcessor textProcessor;
//    private String prefix = SysPathUtil.getSysPath() + "/SearchEngine/WEBPAGES_RAW/";
   private String prefix = SysPathUtil.getSysPath() + "/WEBPAGES_RAW/";

    public void readFileIntoDocument() {
        while (bookUrlRepository.hashNext()) {
            URLPath urlPath = bookUrlRepository.next();
            String path = prefix + urlPath.getPath();
            MyFileReader myFileReader = new MyFileReader(path);
            String html = myFileReader.readAll();
            if (html != null && !html.isEmpty()) {
                try{
                    Document doc = Jsoup.parse(html);
                    com.uci.mode.Document document = new com.uci.mode.Document();
                }catch (Exception exp){
                    System.out.println("parsing failed: " + path);
                    exp.printStackTrace();
                }
            }
        }
    }


}
