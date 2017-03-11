package com.uci.pr;

import com.google.common.collect.Lists;
import com.uci.indexer.StopWordsFilter;
import com.uci.io.MyFileWriter;
import com.uci.mode.IndexEntry;
import com.uci.mode.Page;
import com.uci.utils.JsonUtils;

import java.util.*;

public class PageRank {
    private static final double DampingFactor = 0.85;
    private static final int MAX_ITERATION = 1000;
    private static String prefix = StopWordsFilter.class.getClassLoader().getResource("rank.txt").getPath();


    public static void calc(Map<String, Page> graph) {
        double initPageRank = 1.0 / graph.size();
        for (String key : graph.keySet()) {
            graph.get(key).setScore(initPageRank);
        }

        Map<String, Double> temp = new HashMap<>();
        Set<String> pageKeys = graph.keySet();
        int k = 1; // For Traversing
        int ITERATION_STEP = 1;

        while (ITERATION_STEP++ <= MAX_ITERATION) // Iterations
        {
            // Store the PageRank for All Nodes in Temporary Array
            for (String key : pageKeys) {
                temp.put(key, graph.get(key).getScore());
            }
            for (String key : pageKeys) {
                Page page = graph.get(key);
                List<String> inputPages = page.getInputPages();
                double score = 0;
                if (inputPages != null) {
                    for (String url : inputPages) {
                        score += temp.get(url) * 1.0 / graph.get(url).getOutputNumber();
                    }
                }
                graph.get(key).setScore(score);
            }
            //2021
            System.out.printf("\n After " + ITERATION_STEP + "th Step \n");
            for (String key : pageKeys) {
                System.out.println("Page Rank of " + key + " is :\t" + graph.get(key).getScore());
            }
        }
//       // Add the Damping Factor to PageRank
        for (String key : pageKeys) {
            Page page = graph.get(key);
            page.setScore(1 - DampingFactor + DampingFactor * page.getScore());
        }
        normalize(graph);
        System.out.println("calculate finish....");
    }

    private static void normalize(Map<String, Page> map) {
        Set<String> set = map.keySet();
        double sum = 0;
        for (String key : set) {
            Double score = map.get(key).getScore();
            sum += score;
        }

        double sqrt = Math.sqrt(sum);
        for (String key : set) {
            Page page = map.get(key);
            page.setScore(page.getScore() / sqrt);
        }
    }

    public static void saveRankToFiles(Map<String, Page> graph) {
        MyFileWriter.createFile(prefix);
        MyFileWriter myFileWriter = null;
        try {
            myFileWriter = new MyFileWriter(prefix, true);
            for (String key : graph.keySet()) {
                Double score = graph.get(key).getScore();
                String text = new StringBuffer().append(key).append(":").append(score).toString();
                myFileWriter.writeLine(text);
                myFileWriter.flush();
                System.out.println("storing term : " + key);
            }
            myFileWriter.flush();
        } finally {
            myFileWriter.close();
        }
    }

//    public static void main(String args[]) {
//        //http://codispatch.blogspot.com/2015/12/java-program-implement-google-page-rank-algorithm.html
//        addLinks("A", Lists.newArrayList("B"));
//        addLinks("B", Lists.newArrayList("E"));
//        addLinks("C", Lists.newArrayList("A", "B", "D", "E"));
//        addLinks("D", Lists.newArrayList("C", "E"));
//        addLinks("E", Lists.newArrayList("D"));
//
//        for (String key : map.keySet()) {
//            System.out.println(key + ":" + map.get(key));
//        }
//
//        PageRank.calc(map);
//
//    }
//
//
//    private static Map<String, Page> map = new HashMap<>();
//
//    public static void addLinks(String url, List<String> outlinks) {
//        //create source Page
//        Page source = addPage(url);
//        source.setOutputNumber(outlinks.size());
//
//        //create destination Pages
//        if (outlinks == null || outlinks.isEmpty()) {
//            return;
//        }
//        for (String outUrl : outlinks) {
//            Page page = addPage(outUrl);
//            List<String> inputPages = page.getInputPages();
//            if (inputPages == null) {
//                inputPages = new ArrayList<>();
//                page.setInputPages(inputPages);
//            }
//            inputPages.add(source.getUrl());
//        }
//    }
//
//    private static Page addPage(String url) {
//        Page page = map.get(url);
//        if (page == null) {
//            page = new Page(url);
//            map.put(url, page);
//        }
//        return page;
//    }


}