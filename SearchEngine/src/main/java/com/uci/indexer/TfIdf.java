package com.uci.indexer;

import com.uci.mode.IndexEntry;

import java.util.*;
import java.io.*;
import java.util.Map.Entry;
import java.util.Iterator;

public class TfIdf{

    public static void calculateTFScore(int docsize, TreeMap<String, List<IndexEntry>> map) {
        TreeMap indexMap = map;
        int size = docsize;
        Iterator<Entry<String, List<IndexEntry>>> iterator = indexMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, List<IndexEntry>> entry = iterator.next();
            int df = entry.getValue().size();
            List<IndexEntry> list = entry.getValue();
            for (IndexEntry tp : list) {
                int tf = tp.getTermFre();
                double score = (1 + Math.log10(tf)) * Math.log10((double) size / df);
            }
        }
    }
}