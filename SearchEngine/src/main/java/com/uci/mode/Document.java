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

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
