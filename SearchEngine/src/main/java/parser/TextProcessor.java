package parser;

import io.MyFileReader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by junm5 on 1/17/17.
 */
@Service
public class TextProcessor {

    private static String PATTERN = "[a-z0-9A-Z]+";
    private static Pattern compile;

    public TextProcessor() {
        compile = Pattern.compile(PATTERN);
    }

    public static List<String> tokenize(String filePath) {
        if (filePath == null || filePath.equals("")) {
            System.out.println("Input file is null or empty");
            return new ArrayList();
        }
        MyFileReader fileReader = null;
        try {
            fileReader = new MyFileReader(filePath);
            //readLines all data from files
            String txt = fileReader.readAll().trim();
            return getTokens(txt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileReader.close();
        }
        return new ArrayList();
    }

    public static void print(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());
        Comparator<Map.Entry<String, Integer>> valueComparator = (o1, o2) -> o2.getValue() - o1.getValue();
        Collections.sort(list, valueComparator);

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public static Map<String, Integer> buildFreMap(List<String> input) {
        HashMap<String, Integer> res = new HashMap();
        if (input == null || input.isEmpty()) {
            return res;
        }
        for (String token : input) {
            res.put(token, res.getOrDefault(token, 0) + 1);
        }
        return res;
    }

    public static List<String> getTokens(String input) {
        Matcher matcher = compile.matcher(input.toLowerCase());
        List<String> res = new ArrayList();
        while (matcher.find()) {
            res.add(matcher.group());
        }
        return res;
    }
    public static List<String> stemstop (List<String> tokens){
        List<String> res = new ArrayList<>();
        StopWordsFilter stopWordsFilter = new StopWordsFilter();
        tokens = stopWordsFilter.filter(tokens);
        Stemmer stemmer = new Stemmer();
        for(String s : tokens){
            String out = stemmer.stem(s);
            res.add(out);
        }
        return res;
    }
}