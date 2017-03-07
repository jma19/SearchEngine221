package com.uci.indexer;

import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.io.MyFileReader;
import com.uci.mode.URLPath;
import com.uci.db.DBHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * parsing html file into document and generating index file
 * Created by junm5 on 2/25/17.
 */
@Service
public class BookKeepingFileProcessor {

    @Autowired
    private BookUrlRepository bookUrlRepository;

    @Autowired
    private Indexer indexer;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private AnchorTextProcessor anchorTextProcessor;

    private int i = 0;

    private String prefix = StopWordsFilter.class.getClassLoader().getResource("WEBPAGES_RAW").getPath();

    //1 - 18660
    public void process() {
        while (bookUrlRepository.hashNext()) {
            URLPath urlPath = bookUrlRepository.next();
            String path = prefix + "/" + urlPath.getPath();
            MyFileReader myFileReader = new MyFileReader(path);
            String html = myFileReader.readAll();
            if (html != null && !html.isEmpty()) {
                try {
                    Document doc = Jsoup.parse(html);
                    anchorTextProcessor.add(doc, urlPath.getUrl());
                    com.uci.mode.Document document = Htmlparser.generateDocument(doc, urlPath.getUrl());
                    if (document != null) {
                        i++;
                        document.setId(i).setAnchorText(dbHandler.get(Table.ANCHOR, urlPath.getUrl(), String.class));
                        buildDocumentIndex(document);
                        dbHandler.put(Table.DOCUMENT, String.valueOf(i), document);
                        System.out.println("generate document index i = " + i);
                    }
                } catch (Exception exp) {
                    System.out.println("parsing failed: " + path);
                    exp.printStackTrace();
                } finally {
                    myFileReader.close();
                }
            }
        }
        System.out.println("document size = " + i);
        dbHandler.put(Table.DOCUMENT, Constant.SIZE, i);
        System.out.println("saving indexing.....");
        indexer.calculateTFIDF();
        indexer.saveIndexesToFiles();
        indexer.saveIndexesToRedis();
    }

    private void buildDocumentIndex(com.uci.mode.Document document) {
        indexer.indexize(document);
    }
}
