package com.uci.mode;

/**
 * Created by junm5 on 2/23/17.
 */
public class DocumentEntity {
    public String url;
    public String title;
    public String description;
    public String body;
    public String header;

    public String getUrl() {
        return url;
    }

    public DocumentEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public DocumentEntity setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getTitle(){
        return title;
    }

    public DocumentEntity setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public DocumentEntity setBody(String body) {
        this.body = body;
        return this;
    }
    public String getBody(){
        return body;
    }

    public DocumentEntity setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "DocumentEntity{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}
