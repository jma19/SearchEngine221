package com.uci.indexer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.constant.Tag;
import com.uci.db.DBHandler;
import com.uci.io.MyFileWriter;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import com.uci.mode.IndexEntry;
import com.uci.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 2/22/17.
 */
@Component
public class TwoGramIndexer extends Indexer {

    @PostConstruct
    private void init() {
        setTable(Table.TWO_GRAM);
    }

    @Autowired
    private TextProcessor textProcessor;
    @Autowired
    private Stemmer stemmer;

    public void indexize(Document document) {
        Map<String, BaseEntry> posTitleTwoGramMap = getEntryMap(Tag.TWOGRAM_TITLE, document.getTitle());
        Map<String, BaseEntry> anchorTwoGramMap = getEntryMap(Tag.TWOGRAM_ANCHOR, document.getAnchorText());
        Map<String, BaseEntry> posBodyTwoGramMap = getEntryMap(Tag.TWOGRAM_BODY, document.getBody());

        List<Map<String, BaseEntry>> maps = Lists.newArrayList(posTitleTwoGramMap, anchorTwoGramMap, posBodyTwoGramMap);
        updateIndexMap(document.getId(), maps);
    }

    private Map<String, BaseEntry> getEntryMap(Tag tag, String text) {
        if (Strings.isNullOrEmpty(text)) {
            return new HashMap<>();
        }
        List<String> tokens = getNGrams(text, 2);
        return buildPosMap(tag, tokens);
    }

    public List<String> getNGrams(String contxt, int n) {
        List<String> tokens = textProcessor.getTokens(contxt);
        tokens = tokens.stream().map(token -> stemmer.stem(token)).collect(Collectors.toList());
        return getNGrams(tokens, n);
    }

    public List<String> getNGrams(List<String> tokens, int n) {
        List<String> res = new ArrayList();
        for (int i = n - 1; i < tokens.size(); i++) {
            StringBuffer temp = new StringBuffer();
            for (int j = i - n + 1; j <= i; j++) {
                temp.append(tokens.get(j));
            }
            res.add(temp.toString());
        }
        return res;
    }
}
