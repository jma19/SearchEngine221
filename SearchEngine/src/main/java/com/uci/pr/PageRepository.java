package com.uci.pr;

import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.uci.constant.Table;
import com.uci.db.DBHandler;
import com.uci.mode.Page;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by junm5 on 3/3/17.
 */
@Service
public class PageRepository {
    @Autowired
    private DBHandler dbHandler;

    private Map<String, Page> map = new HashMap<>();

    public void addLinks(String url, List<String> outlinks) {
        //create source Page
        Page source = addPage(url, outlinks);

        //create destination Pages
        if (outlinks == null || outlinks.isEmpty()) {
            return;
        }
        for (String outUrl : outlinks) {
            Page page = addPage(outUrl, outlinks);
            List<String> inputPages = page.getInputPages();
            if (inputPages == null) {
                inputPages = new ArrayList<>();
            }
            inputPages.add(url);
        }
    }

    private Page addPage(String url, List<String> outlinks) {
        Page page = map.get(url);
        if (page == null) {
            page = new Page(url);
            map.put(url, page);
            page.setOutputNumber(outlinks.size());
        }
        return page;
    }

    public Double getPrScore(String url){
        return map.get(url).getScore();
    }

    public void savePrScores(Map<String, Double> maps) {
        for (String key : maps.keySet()) {
            dbHandler.put(Table.RANK, key, maps.get(key));
        }
    }

    public double getPrScore(int docId) {
        Double res = dbHandler.get(Table.RANK, String.valueOf(docId), Double.class);
        return res == null ? 0 : res;
    }
}
