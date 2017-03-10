package com.uci.indexer;

import com.google.common.collect.Lists;
import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.io.MyFileReader;
import com.uci.mode.Page;
import com.uci.mode.URLPath;
import com.uci.db.DBHandler;
import com.uci.pr.PageRank;
import com.uci.pr.PageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private PageRepository pageRepository;

    private int i = 0;

    private String prefix = StopWordsFilter.class.getClassLoader().getResource("WEBPAGES_RAW").getPath();

    //1 - 18660
    public void process() {
        long start = System.currentTimeMillis();
        Map<String, Integer> urlDoc = new HashMap<>();
        while (bookUrlRepository.hashNext()) {
            URLPath urlPath = bookUrlRepository.next();
            String path = prefix + "/" + urlPath.getPath();
            MyFileReader myFileReader = new MyFileReader(path);
            String html = myFileReader.readAll();
            if (html != null && !html.isEmpty()) {
                try {
                    Document doc = Jsoup.parse(html);
                    com.uci.mode.Document document = Htmlparser.generateDocument(doc, urlPath.getUrl());
                    if (document != null) {
                        i++;
//                        document.setId(i).setAnchorText(dbHandler.get(Table.ANCHOR, urlPath.getUrl(), String.class));
//                        buildDocumentIndex(document);
//                        dbHandler.put(Table.DOCUMENT, String.valueOf(i), document);
                        System.out.println("generate document index i = " + i);
                        Map<String, String> outgoingLinks = Htmlparser.getOutgoingLinks(doc, urlPath.getUrl());
                        if (!outgoingLinks.isEmpty()) {
                            Set<String> outLinks = outgoingLinks.keySet();
                            pageRepository.addLinks(urlPath.getUrl(), Lists.newArrayList(outLinks));
                        }
                        urlDoc.put(urlPath.getUrl(), i);
                        System.out.println("finish add link for document i = " + i);
                    }
                } catch (Exception exp) {
                    System.out.println("parsing failed: " + path);
                    exp.printStackTrace();
                } finally {
                    myFileReader.close();
                }
            }
        }
//        System.out.println("document size = " + i);
//        dbHandler.put(Table.DOCUMENT, Constant.SIZE, i);
//        System.out.println("saving indexing.....");
//        indexer.calculateTFIDF();
////        indexer.saveIndexesToFiles();
//        indexer.saveIndexesToRedis();
//        System.out.println("saving indexing success!!!");
//        System.out.println("calculating page rank.....");
        Map<String, Page> graph = pageRepository.getGraph();
        PageRank.calc(graph);
        System.out.println("saving page rank.....");
        System.out.println("url doc size = " + urlDoc.size());
        pageRepository.savePrScores(join(graph, urlDoc));
        System.out.println("saving page rank success!!!");
        System.out.println("time cost: " + ((System.currentTimeMillis() - start) / 1000));

    }

    private Map<Integer, Double> join(Map<String, Page> map, Map<String, Integer> urlDoc) {
        Map<Integer, Double> res = new HashMap<>();
        Set<String> set = urlDoc.keySet();
        for (String key : set) {
            Page page = map.get(key);
            if(page == null){
                System.out.println("null url"+":"+key);
                continue;
            }
            res.put(urlDoc.get(key), page.getScore());
        }
        return res;
    }

    private void buildDocumentIndex(com.uci.mode.Document document) {
        indexer.indexize(document);
    }
}
