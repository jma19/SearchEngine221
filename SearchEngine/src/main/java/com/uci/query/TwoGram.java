package com.uci.query;

import com.uci.mode.WordFreq;

import java.util.*;


public class TwoGram {

    public static List<WordFreq> comTwoGram(String query) {
        String que = query.trim().replaceAll("[^A-Z0-9a-z]+", " ").toLowerCase();
        String[] q = que.trim().split(" +");
        List<String> list = Arrays.asList(q);//need to do stemming and stopping word filter;
        Map<String, Integer> map = new HashMap<String, Integer>();
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                String twoword = list.get(i) + " " + list.get(j);
                map.put(twoword, map.getOrDefault(twoword, 0) + 1);
            }
        }
        List<WordFreq> output = new ArrayList<WordFreq>();

        for (String key : map.keySet()) {
            WordFreq tmp = new WordFreq(key);
            tmp.word = key;
            tmp.freq = map.get(key);
            output.add(tmp);
        }
        Collections.sort(output, new Comparator<WordFreq>() {
            public int compare(WordFreq a, WordFreq b) {
                return a.getfreq() != b.getfreq() ? b.getfreq() - a.getfreq() : a.word.compareTo(b.word);
            }
        });
        return output;
    }

}
