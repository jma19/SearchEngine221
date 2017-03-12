package com.uci.mode;

/**
 * Created by junm5 on 2/24/17.
 */
public class Abstract implements Comparable<Abstract> {
    private String url;
    private String title;
    private String desc;
    private double score;

    public double getScore() {
        return score;
    }

    public Abstract setScore(double score) {
        this.score = score;
        return this;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Abstract that = (Abstract) o;

        return desc != null ? desc.equals(that.desc) : that.desc == null;

    }

    @Override
    public int hashCode() {
        String temp = desc;
        if (desc != null && desc.length() > 100) {
            temp = desc.substring(0, 100);
        }
        return temp != null ? temp.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Abstract{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Abstract o) {
        return this.getScore() == o.getScore() ? 0 : (this.getScore() < o.getScore() ? 1 : -1);
    }
}
