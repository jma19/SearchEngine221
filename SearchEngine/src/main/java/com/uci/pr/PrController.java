package com.uci.pr;

import com.google.common.collect.Lists;
import com.uci.indexer.BookUrlRepository;
import com.uci.indexer.Htmlparser;
import com.uci.indexer.StopWordsFilter;
import com.uci.io.MyFileReader;
import com.uci.mode.URLPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * Created by junm5 on 3/6/17.
 */
@Service
public class PrController {
    @Autowired
    private BookUrlRepository bookUrlRepository;

    @Autowired
    private PageRepository pageRepository;

    private String prefix = PrController.class.getClassLoader().getResource("WEBPAGES_RAW").getPath();

    //1 - 18660
    public void process() {
        while (bookUrlRepository.hashNext()) {
            URLPath urlPath = bookUrlRepository.next();
            String path = prefix + "/" + urlPath.getPath();
            MyFileReader myFileReader = new MyFileReader(prefix);
            String html = myFileReader.readAll();
            if (html != null && !html.isEmpty()) {
                try {
                    Document doc = Jsoup.parse(html);
                    Map<String, String> outgoingLinks = Htmlparser.getOutgoingLinks(doc, urlPath.getUrl());
                    if (!outgoingLinks.isEmpty()) {
                        Set<String> outLinks = outgoingLinks.keySet();
                        pageRepository.addLinks(urlPath.getUrl(), Lists.newArrayList(outLinks));
                    }
                } catch (Exception exp) {
                    System.out.println("parsing failed: " + path);
                    exp.printStackTrace();
                } finally {
                    myFileReader.close();
                }
            }
        }
        Map<String, Double> prScore = pageRepository.calculatePrScore();
        pageRepository.savePrScores(prScore);
    }
}