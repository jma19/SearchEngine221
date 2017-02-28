package com.uci.indexer;

import com.uci.io.MyFileReader;
import com.uci.utils.SysPathUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/22/17.
 * this service is aim to filter out the stop words in the tokens
 */
@Component(value = "stopWordsFilter")
public class StopWordsFilter {

    private Set<String> stopWordsContainer = new HashSet<>();
//    private static String STOP_WORDS_PATH = SysPathUtil.getSysPath() + "/SearchEngine/conf/stopword.txt";
    private static String STOP_WORDS_PATH = SysPathUtil.getSysPath() + "/conf/stopword.txt";

    /**
     * load stop words from stopword.txt file into memory
     */
    @PostConstruct
    private void loadWords() {
        MyFileReader myFileReader = null;
        try {
            myFileReader = new MyFileReader(STOP_WORDS_PATH);
            String stopWords = myFileReader.readAll().replace("\n", "");
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

    public Set<String> getStopWordList() {
        return stopWordsContainer;
    }

    /**
     * filter all stop words from token list
     *
     * @param tokens
     */
    public List<String> filter(List<String> tokens) {
        if (tokens == null || tokens.isEmpty() || stopWordsContainer.isEmpty()) {
            return tokens;
        }
        return tokens.stream().filter(s -> !stopWordsContainer.contains(s))
                .collect(Collectors.toList());
    }

}
