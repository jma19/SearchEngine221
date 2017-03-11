package com.uci.mode;

/**
 * Created by junm5 on 2/22/17.
 */
public class URLPath {
    private String path;
    private String url;

    public URLPath(){
        super();
    }

    public URLPath(String path, String url) {
        this.path = path;
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public URLPath setPath(String path) {
        this.path = path;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public URLPath setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        URLPath urlPath = (URLPath) o;

        return url != null ? url.equals(urlPath.url) : urlPath.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "URLPath{" +
                "path='" + path + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
