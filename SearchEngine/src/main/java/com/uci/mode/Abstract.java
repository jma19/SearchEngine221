package com.uci.mode;

/**
 * Created by junm5 on 2/24/17.
 */
public class Abstract {
    private String url;
    private String title;
    private String desc;

    public String getUrl() {
        return url;
    }

    public Abstract setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Abstract setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Abstract setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toString() {
        return "Abstract{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
