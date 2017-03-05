package com.uci.pr;

import com.uci.mode.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by junm5 on 3/3/17.
 */
@Service
public class PageResposity {

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
            Set<Page> inputPages = page.getInputPages();
            if (inputPages == null) {
                inputPages = new HashSet<>();
            }
            inputPages.add(source);
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

}
