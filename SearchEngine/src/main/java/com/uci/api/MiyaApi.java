package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.constant.Table;
import com.uci.db.DBHandler;
import com.uci.indexer.OneGramIndexer;
import com.uci.indexer.Stemmer;
import com.uci.indexer.TextProcessor;
import com.uci.indexer.TwoGramIndexer;
import com.uci.mode.Abstract;
import com.uci.mode.Document;
import com.uci.mode.IndexEntry;
import com.uci.mode.Pair;
import com.uci.pr.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/miya")
public class MiyaApi {
    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private OneGramIndexer oneGramIndexer;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private TwoGramIndexer twoGramIndexer;

    @Autowired
    private Stemmer stemmer;

    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public List<Abstract> query(@PathVariable String query) {
        System.out.println("receiving :" + query);
        List<Abstract> abstractList = getAbstractList(query);
        return abstractList;
    }

    public List<Abstract> getAbstractList(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> tokens = textProcessor.getTokens(query);
        List<Abstract> res = new ArrayList<>();
        if (tokens.size() == 2) {
            res = queryTwoGrams(tokens);
        }
        if (!res.isEmpty()) {
            return res;
        }
        List<String> queryList = textProcessor.stemstop(tokens);
        List<String> queryFin = queryList.stream().filter(query1 ->
                oneGramIndexer.contains(query1)).collect(Collectors.toList());

        if (queryFin.size() == 1) {
            res.addAll(queryOneWord(queryFin.get(0)));
        } else if (queryFin.size() >= 2) {
            res.addAll(queryMultiWords(queryFin));
        }
        return res;
    }

    //get two gram result
    public List<Abstract> queryTwoGrams(List<String> tokens) {
        if (tokens.size() != 2) {
            return Lists.newArrayList();
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < tokens.size(); i++) {
            stringBuffer.append(stemmer.stem(tokens.get(i)));
        }
        List<IndexEntry> indexEntities = twoGramIndexer.getIndexEntities(stringBuffer.toString());
        List<Abstract> abstracts = removeDuplicate(getAbstractsByIndexEntry(indexEntities));
        return abstracts.stream().sorted().collect(Collectors.toList());

    }

    public List<Abstract> queryOneWord(String term) {
        List<IndexEntry> indexEntities = oneGramIndexer.getIndexEntities(term);
        if (indexEntities == null || indexEntities.isEmpty()) {
            return new ArrayList<>();
        }
        List<Abstract> abstracts = removeDuplicate(getAbstractsByIndexEntry(indexEntities));
        return abstracts.stream().sorted().limit(10).collect(Collectors.toList());
    }

    public List<Abstract> getAbstractsByIndexEntry(List<IndexEntry> indexEntries) {
        List<Abstract> res = new ArrayList<>();
        for (IndexEntry indexEntry : indexEntries) {
            int docId = indexEntry.getId();
            Document doc = dbHandler.get(Table.DOCUMENT, String.valueOf(docId), Document.class);

            String body = doc.getBody().length() > 200 ?
                    doc.getBody().substring(0, 200) : doc.getBody();
            res.add(new Abstract().setDesc(body)
                    .setUrl(doc.getUrl())
                    .setTitle(doc.getTitle())
                    .setScore(indexEntry.getTermFre()));
        }
        return res;
    }

    public List<Abstract> queryMultiWords(List<String> terms) {
        Map<String, Integer> freMap = textProcessor.buildFreMap(terms);
        int size = freMap.size();
        double[] query = new double[size];
        Map<Integer, double[]> docMap = new HashMap<>();
        int index = 0;
        for (String key : freMap.keySet()) {
            query[index] = oneGramIndexer.getIDF(key) * (1 + Math.log10(freMap.get(key)));
            List<IndexEntry> indexEntities = oneGramIndexer.getIndexEntities(key);
            for (IndexEntry entry : indexEntities) {
                double[] temp = docMap.get(entry.getId());
                if (temp == null) {
                    temp = new double[size];
                    docMap.put(entry.getId(), temp);
                }
                temp[index] = entry.getTfIdf();
            }
            index++;
        }
        normalize(query);
        //store final sim value for each document
        List<Pair> list = new ArrayList<>();
        for (Integer docId : docMap.keySet()) {
            double[] docV = docMap.get(docId);
            normalize(docV);
            double dot = dot(query, docV) + pageRepository.getPrScore(docId);
            list.add(new Pair(docId, dot));
        }
        List<Pair> pairs = list.stream()
                .sorted().limit(50).collect(Collectors.toList());
        List<Abstract> abstracts = removeDuplicate(getAbstractsByPairs(pairs));
        return abstracts.stream().sorted().collect(Collectors.toList());
    }

    private List<Abstract> getAbstractsByPairs(List<Pair> pairs) {
        List<Abstract> res = new ArrayList<>();
        for (Pair pair : pairs) {
            Document document = dbHandler.get(Table.DOCUMENT, String.valueOf(pair.getId()), Document.class);
            String dob = document.getBody();
            String body = dob.length() < 200 ? dob : dob.substring(0, 200);
            res.add(new Abstract()
                    .setDesc(body)
                    .setTitle(document.getTitle())
                    .setUrl(document.getUrl())
                    .setScore(pair.getScore()));
        }
        return res;
    }

    private double dot(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new ArithmeticException("v1.length is not equal to v2.length");
        }
        double sum = 0;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }

    private double getNormalFactor(double[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(vector[i], 2);
        }
        return Math.sqrt(sum);
    }

    private void normalize(double[] vector) {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(vector[i], 2);
        }
        sum = Math.sqrt(sum);
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= sum;
        }
    }

    private List<Abstract> removeDuplicate(List<Abstract> abstracts) {
        HashMap<Integer, Abstract> map = new HashMap<>();
        for (Abstract abs : abstracts) {
            int hashcode = abs.hashCode();
            Abstract old = map.get(hashcode);
            if (map.containsKey(hashcode)) {
                if (abs.getUrl().length() < old.getUrl().length()) {
                    map.put(hashcode, abs);
                }
            } else {
                map.put(hashcode, abs);
            }
        }
        Collection<Abstract> values = map.values();
        return Lists.newArrayList(values);
    }

}
