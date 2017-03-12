import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class Google {


    public static void main(String[] args) {
        Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
        String YOUR_SEARCH_STRING_GOES_HERE = "machine learning";
        try {
            com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(YOUR_SEARCH_STRING_GOES_HERE);
            list.setKey("AIzaSyBaEI0o8NdDcq1lsoaAvly-ptTo2VnyLRY");
            list.setCx("000871406312733320210:n0ht0gelmoc");
            Search results = list.setSiteSearch("ics.uci.edu").execute();
            List<Result> items = results.getItems();
            for (Result res : items){
                System.out.println(res.getLink());
            }
           

          

        } catch (IOException e) {
         
                 e.printStackTrace();
            //      }
        }


    }
}
