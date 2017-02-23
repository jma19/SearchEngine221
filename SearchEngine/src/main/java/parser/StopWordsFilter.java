package parser;

import io.MyFileReader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/22/17.
 * this service is aim to filter out the stop words in the tokens
 */
@Service
public class StopWordsFilter {

    private Set<String> stopWordsContainer = new HashSet<>();
    private String STOP_WORDS_PATH = "stopword.txt";

    @PostConstruct
    private void loadWords() {
        MyFileReader myFileReader = null;
        try {
            myFileReader = new MyFileReader(STOP_WORDS_PATH);
            String stopWords = myFileReader.readAll();
            String[] swords = stopWords.split(",");
            for (String word : swords) {
                if (!word.isEmpty()) {
                    stopWordsContainer.add(word.trim());
                }
            }
        } finally {
            myFileReader.close();
        }
    }

    /**
     * filter all stop words from token list
     *
     * @param tokens
     */
    public List<String> fiter(List<String> tokens) {
        if (tokens == null || tokens.isEmpty() || stopWordsContainer.isEmpty()) {
            return tokens;
        }
        return tokens.stream().filter(s -> stopWordsContainer.contains(s))
                .collect(Collectors.toList());
    }

}
