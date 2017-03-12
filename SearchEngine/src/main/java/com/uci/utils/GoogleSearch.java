package com.uci.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class GoogleSearch {

    public static List<String> GoogleSearch(String query) {
        Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
        String q = query;
        List<String> tmp = null;
        try {
            Long num = Long.valueOf(10);
            com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(q);
            list.setKey("AIzaSyBaEI0o8NdDcq1lsoaAvly-ptTo2VnyLRY");
            list.setCx("000871406312733320210:n0ht0gelmoc");

            Search results = list.setSiteSearch("ics.uci.edu").setNum(num).execute();
            List<Result> items = results.getItems();
            tmp = new ArrayList<>();
            for (Result res : items) {
                tmp.add(res.getLink());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return tmp;
    }
    public static void main(String[] args) {
        String a = "computer games";
        List<String> b = GoogleSearch(a);
        for (String s : b) {
            System.out.println(s);
        }
    }

}

