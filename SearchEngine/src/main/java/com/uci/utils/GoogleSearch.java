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

    public static List<String> GS(String query, Long num) {
        List tmp = new ArrayList<>();
        try{
                Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
                com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(query);
                list.setKey("AIzaSyBaEI0o8NdDcq1lsoaAvly-ptTo2VnyLRY");
                list.setCx("000871406312733320210:n0ht0gelmoc");
                Search results = list.setSiteSearch("ics.uci.edu").setStart(num).execute();
                List<Result> items = results.getItems();

                for (Result res : items) {
                    tmp.add(res.getLink());
                }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return tmp;
    }
    public static List<String> GenerateList(String a){
        List<String> result = new ArrayList<>();
        for (int i = 0; i<2; i++) {
            Long n = Long.valueOf(10 * i + 1);
            result.addAll(GS(a,n));
        }
        return result;

    }

    public static void main(String[] args) {
        String a = "computer games";
        List<String> res = GenerateList(a);
        for(String s : res) {
            System.out.println(s);
        }
    }

}
