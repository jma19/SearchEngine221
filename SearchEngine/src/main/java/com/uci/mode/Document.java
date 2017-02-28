package com.uci.mode;

/**
 * Created by junm5 on 2/23/17.
 */
public class Document {
    private Integer id;
    private String url;
    private String title;
    private String text;

    public Integer getId() {
        return id;
    }

    public Document setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Document setText(String text) {
        this.text = text;
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
    public String getTitle(){
        return title;
    }

//    public Document setDescription(String description) {
//        this.description = description;
//        return this;
//    }
//    public String getDescription() {
//        return description;
//    }
//    public Document setBody(String body) {
//        this.body = body;
//        return this;
//    }
//    public String getBody(){
//        return body;
//    }
//
//    public Document setHeader(String header) {
//        this.header = header;
//        return this;
//    }
//
//    public String getHeader() {
//        return header;
//    }

    @Override
    public String toString() {
        return "Document{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
