package com.uci.api;

import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.db.DBHandler;
import com.uci.indexer.Indexer;
import com.uci.indexer.TextProcessor;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/miya")
public class MiyaApi {
    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private Indexer indexer;

    @Autowired
    private DBHandler dbHandler;

    @Autowired
    private PageRepository pageRepository;

    @RequestMapping(path = "/query/{query}", method = RequestMethod.GET)
    public List<Abstract> query(@PathVariable String query) {
        System.out.println("receiving :" + query);
        List<Abstract> abstractList = getAbstractList(query);
        return abstractList;
    }

//    private List<Abstract> buildAbstractsTest() {
//        List<Abstract> abstracts = Lists.newArrayList();
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
//        return abstracts;
//
//    }

    public List<Abstract> getAbstractList(String query) {
        if (query == null || query.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> tokens = textProcessor.getTokens(query);
        List<String> queryList = textProcessor.stemstop(tokens);
        List<String> queryFin = queryList.stream().filter(query1 -> indexer.contains(query1)).collect(Collectors.toList());

        if (queryFin.size() == 1) {
            return queryOneWord(queryFin.get(0));

        } else if (queryFin.size() >= 2) {
            return queryMultiWords(queryFin);
        }
        return new ArrayList<>();
    }

    public List<Abstract> queryOneWord(String term) {
        List<IndexEntry> indexEntities = indexer.getIndexEntities(term);
        if (indexEntities == null || indexEntities.isEmpty()) {
            return new ArrayList<>();
        }
        // order by term frequency desc
        List<IndexEntry> tempRes = indexEntities.stream()
                .sorted((o1, o2) -> o2.getTermFre() - o1.getTermFre())
                .limit(10).collect(Collectors.toList());

        return getAbstractsByIndexEntry(tempRes);
    }

    public List<Abstract> getAbstractsByIndexEntry(List<IndexEntry> indexEntries) {
        List<Abstract> res = new ArrayList<>();
        for (IndexEntry indexEntry : indexEntries) {
            int docId = indexEntry.getId();
            Document doc = dbHandler.get(Table.DOCUMENT, String.valueOf(docId), Document.class);
            res.add(new Abstract().setDesc(doc.getBody().substring(0, 200))
                    .setUrl(doc.getUrl())
                    .setTitle(doc.getTitle()));
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
            query[index] = indexer.getIDF(key) * (1 + Math.log10(freMap.get(key)));
            List<IndexEntry> indexEntities = indexer.getIndexEntities(key);
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
            double dot = Constant.alpha * dot(query, docV) + (1 - Constant.alpha) * pageRepository.getPrScore(docId) ;
            list.add(new Pair(docId, dot));
        }
        List<Pair> pairs = list.stream()
                        .sorted().limit(10).collect(Collectors.toList());
        return getAbstractsByPairs(pairs);
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
                    .setUrl(document.getUrl()));
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

    private final int HALF_LEN_OF_ABSTRACT = 25 / 2;

    private String generateAbstract(List<String> tokens, int pos) {
        int start = pos - HALF_LEN_OF_ABSTRACT, end = pos + HALF_LEN_OF_ABSTRACT;
        if (start < 0) {
            start = 0;
        }
        if (end >= tokens.size()) {
            end = tokens.size() - 1;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int k = start; k <= end; k++) {
            stringBuffer.append(tokens.get(k));
        }
        stringBuffer.append("........");
        return stringBuffer.toString();
    }
}
