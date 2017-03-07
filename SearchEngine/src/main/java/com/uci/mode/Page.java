package com.uci.mode;

import java.util.List;
import java.util.Set;

/**
 * Created by junm5 on 3/3/17.
 */
public class Page {
    private String url;
    private Double score = 1.0;
    private Integer outputNumber;
    private List<String> inputPages;
    private boolean isVisited;

    public boolean isVisited() {
        return isVisited;
    }

    public Page setVisited(boolean visited) {
        isVisited = visited;
        return this;
    }

    public Page() {
        super();
    }

    public Page(String url) {
        this.url = url;
    }

    public Double getScore() {
        return score;
    }

    public Page setScore(Double score) {
        this.score = score;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Page setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getOutputNumber() {
        return outputNumber;
    }

    public Page setOutputNumber(Integer outputNumber) {
        this.outputNumber = outputNumber;
        return this;
    }

    public List<String> getInputPages() {
        return inputPages;
    }

    public Page setInputPages(List<String> inputPages) {
        this.inputPages = inputPages;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return url != null ? url.equals(page.url) : page.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", outputNumber=" + outputNumber +
                ", inputPages=" + inputPages +
                '}';
    }
}
