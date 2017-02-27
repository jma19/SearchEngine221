package com.uci.api;

import com.google.common.collect.Lists;
import com.uci.indexer.TextProcessor;
import com.uci.mode.Abstract;
import com.uci.mode.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/miya")
public class MiyaApi {
    @Autowired
    private TextProcessor textProcessor;

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
        if (tokens.size() == 1) {

        } else if (tokens.size() >= 2) {

        }

        return null;
    }
}
