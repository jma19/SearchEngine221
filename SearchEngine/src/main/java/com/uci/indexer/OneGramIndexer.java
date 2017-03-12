package com.uci.indexer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.uci.constant.Constant;
import com.uci.constant.Table;
import com.uci.constant.Tag;
import com.uci.io.MyFileWriter;
import com.uci.mode.IndexEntry;
import com.uci.mode.BaseEntry;
import com.uci.mode.Document;
import com.uci.db.DBHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.uci.utils.JsonUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by junm5 on 2/22/17.
 */
@Component
public class OneGramIndexer extends Indexer {

    @PostConstruct
    private void init() {
        setTable(Table.TERM);
    }

    @Autowired
    private TextProcessor textProcessor;

    @Override
    public void indexize(Document document) {
        Map<String, BaseEntry> posTitleMap = getEntryMap(Tag.TITLE, document.getTitle());
        Map<String, BaseEntry> posAnchorMap = getEntryMap(Tag.ANCHOR, document.getAnchorText());
        Map<String, BaseEntry> posBodyMap = getEntryMap(Tag.BODY, document.getBody());
        Map<String, BaseEntry> posUrlMap = getEntryMap(Tag.URL, document.getUrl());

        List<Map<String, BaseEntry>> maps = Lists.newArrayList(posTitleMap, posAnchorMap, posBodyMap, posUrlMap);
        updateIndexMap(document.getId(), maps);
    }

    private Map<String, BaseEntry> getEntryMap(Tag tag, String text) {
        if (Strings.isNullOrEmpty(text)) {
            return new HashMap<>();
        }
        List<String> tokens = getTokens(tag, text);
        return buildPosMap(tag, tokens);
    }

    private List<String> getTokens(Tag tag, String contxt) {
        List<String> tokens = (tag == Tag.URL) ? textProcessor.getTokensByUrl(contxt)
                : textProcessor.getTokens(contxt);
        return textProcessor.stemstop(tokens);
    }


}
