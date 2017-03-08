package com.uci.pr;

import com.uci.mode.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by junm5 on 3/3/17.
 */
public class PageRank {
    //PR(A) = (1-d) + d ∑ (PR(Ti)/C(Ti))
    private static final double d = 0.85;

    public static void calculatePR(Map<String, Page> pageMap, int iterNum) {
        while (iterNum-- > 0) {
            Set<String> pages = pageMap.keySet();
            Map<String, Double> tmp = new HashMap();
            for (String page : pages) {
                double res = 1 - d;
                List<String> inputPages = pageMap.get(page).getInputPages();
                if (inputPages == null || inputPages.isEmpty()) {
                    pageMap.get(page).setScore(res);
                    return;
                }
                for (String temp : inputPages) {
                    Page sor = pageMap.get(temp);
                    res += d * sor.getScore() / sor.getOutputNumber();
                }
                tmp.put(page, res);
                pageMap.get(page).setScore(res);
            }
            for(String key: tmp.keySet()){
                Double value = tmp.get(key);
                pageMap.get(pageMap).setScore(value);
            }

        }
    }
}
