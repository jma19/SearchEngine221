package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.indexer.Indexer;
import com.uci.indexer.TextProcessor;
import com.uci.mode.Abstract;
import com.uci.mode.IndexEntry;
import com.uci.mode.Response;
import com.uci.service.DBHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/miya")
public class MiyaApi {
    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private Indexer indexer;

    @RequestMapping(path = "/query", method = RequestMethod.GET)
    public Response greeting(@RequestParam(value = "query", required = false) String query) {
        return Response.success(buildAbstracts());
    }

    private List<Abstract> buildAbstracts() {
        List<Abstract> abstracts = Lists.newArrayList();
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        abstracts.add(new Abstract().setUrl("https://www.ics.uci.edu/faculty/area/").setTitle("ICS Research Areas").setDesc("Curiosity about the world and a commitment to solving problems are the passions that drive ICS faculty. Their research in the information and computer sciences are applicable to many scholarly and scientific fields. But our faculty don't do it alone, students work side-by-side with nationally renowned professors to advance knowledge and improve lives. Below is a"));
        return abstracts;

    }

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

    private List<Abstract> queryOneWord(String term) {
        List<IndexEntry> indexEntities = indexer.getIndexEntities(term);
        if (indexEntities == null || indexEntities.isEmpty()) {
            return new ArrayList<>();
        }
        // order by term frequency desc
        List<IndexEntry> tempRes = indexEntities.stream()
                .sorted((o1, o2) -> o2.getTermFre() - o1.getTermFre())
                .limit(10).collect(Collectors.toList());

        return transform(tempRes);
    }

    private List<Abstract> transform(List<IndexEntry> indexEntries) {
//        List<Abstract> res = new ArrayList<>();
//        for (IndexEntry indexEntry : indexEntries) {
//            int docId = indexEntry.getId();
//            Document doc = dbHandler.get(String.valueOf(docId), Document.class);
//            List<String> tokens = doc.getTokens();
//            List<Integer> pos = indexEntry.getPos();
//            int position = pos.get(0);
//            //25 words
//            res.add(new Abstract().setDesc(generateAbstract(tokens, position))
//                    .setUrl(doc.getUrl())
//                    .setTitle(doc.getTitle()));
//        }
        return null;
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

    private List<Abstract> queryMultiWords(List<String> terms) {

        return null;
    }
}
