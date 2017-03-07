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
        Page source = addPage(url);
        source.setOutputNumber(outlinks.size());

        //create destination Pages
        if (outlinks == null || outlinks.isEmpty()) {
            return;
        }
        for (String outUrl : outlinks) {
            Page page = addPage(outUrl);
            List<String> inputPages = page.getInputPages();
            if (inputPages == null) {
                inputPages = new ArrayList<>();
                page.setInputPages(inputPages);
            }
            inputPages.add(source.getUrl());
        }
    }

    private Page addPage(String url) {
        Page page = map.get(url);
        if (page == null) {
            page = new Page(url);
            map.put(url, page);
        }
        return page;
    }

    public void savePrScores(Map<Integer, Double> maps) {
        for (Integer key : maps.keySet()) {
            dbHandler.put(Table.RANK, String.valueOf(key), maps.get(key));
        }
    }

    public Map<String, Page> getGraph() {
        return this.map;
    }

    public double getPrScore(int docId) {
        Double res = dbHandler.get(Table.RANK, String.valueOf(docId), Double.class);
        return res == null ? 0 : res;
    }
}
