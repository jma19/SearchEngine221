package com.uci.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import com.uci.utils.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.uci.utils.Test.buildAbstract;

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
        List<String> queryList = textProcessor.stemstop(tokens);
        Set<String> queryFin = queryList.stream().filter(query1 -> oneGramIndexer.contains(query1)).collect(Collectors.toSet());

        if (queryFin.size() == 1) {
            return queryOneWord(Lists.newArrayList(queryFin).get(0));
        } else if (queryFin.size() >= 2) {
            List<Abstract> abstracts = buildAbstract(query);
            abstracts.addAll(queryMultiWords(queryTwoGrams(tokens), Sets.newHashSet(queryFin)));
            return abstracts;
        }
        return new ArrayList<>();
    }

    //get two gram result
    public List<IndexEntry> queryTwoGrams(List<String> tokens) {
        if (tokens.size() < 2) {
            return Lists.newArrayList();
        }
        List<IndexEntry> res = Lists.newArrayList();
        for (int i = 1; i < tokens.size(); i++) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(stemmer.stem(tokens.get(i - 1))).append(stemmer.stem(tokens.get(i)));
            res.addAll(twoGramIndexer.getIndexEntities(stringBuffer.toString()));
        }
        return res;
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

    public List<Abstract> queryMultiWords(List<IndexEntry> list, Set<String> terms) {
        //key is the docId, value is the tf-idf scores
        Map<Integer, Double> scoreMap = new HashMap<>();
        for (IndexEntry indexEntry : list) {
            scoreMap.put(indexEntry.getId(), scoreMap.getOrDefault(indexEntry.getId(), 0.) + indexEntry.getTfIdf());
        }

        for (String key : terms) {
            List<IndexEntry> indexEntities = oneGramIndexer.getIndexEntities(key);
            for (IndexEntry entry : indexEntities) {
                scoreMap.put(entry.getId(), scoreMap.getOrDefault(entry.getId(), 0.) + entry.getTfIdf());
            }
        }
        List<Pair> res = Lists.newArrayList();
        for (Integer docId : scoreMap.keySet()) {
            res.add(new Pair(docId, scoreMap.get(docId)));
        }
        List<Abstract> abstractsByPairs = getAbstractsByPairs(res);
        List<Abstract> abstracts = removeDuplicate(abstractsByPairs);
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
