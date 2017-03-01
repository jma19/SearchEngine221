package com.uci.indexer;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by junm5 on 3/1/17.
 */
@Service
public class AnchorTextProcessor {

    private Map<String, Set<String>> archors = new HashMap<>();


    public void add(String url, String text) {
        Set<String> set = archors.get(url);
        if (set == null) {
            set = new HashSet<>();
            archors.put(url, set);
        }
        set.add(text);
    }

    public void add(Document document, String url) {
        Map<String, String> outgoingLinks = Htmlparser.getOutgoingLinks(document, url);
        for (String key : outgoingLinks.keySet()) {
            add(key, outgoingLinks.get(key));
        }
    }

    public String getAnchorText(String url) {
        Set<String> set = archors.get(url);
        return set == null ?
                null : set.stream().reduce((x, y) -> x + y).get();
    }

}
