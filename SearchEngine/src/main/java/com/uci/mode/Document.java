package com.uci.mode;

import java.util.List;

/**
 * Created by junm5 on 2/23/17.
 */
public class Document {
    private Integer id;
    private String url;
    private String title;
    private String anchorText;
    private String body;
    private List<String> tokens;

    public String getAnchorText() {
        return anchorText;
    }

    public Document setAnchorText(String anchorText) {
        this.anchorText = anchorText;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Document setBody(String body) {
        this.body = body;
        return this;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public Document setTokens(List<String> tokens) {
        this.tokens = tokens;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Document setId(Integer id) {
        this.id = id;
        return this;
    }
    
    public String getUrl() {
        return url;
    }

    public Document setUrl(String url) {
        this.url = url;
        return this;
    }

    public Document setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", anchorText='" + anchorText + '\'' +
                ", body='" + body + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
