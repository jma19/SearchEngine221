package com.uci.mode;

/**
 * Created by junm5 on 3/1/17.
 */
public class Pair implements Comparable<Pair> {
    private int id;
    private double score;
    private double tiidf;

    public Pair() {
        super();
    }

    public Pair(int id, double score, double tiidf) {
        this.id = id;
        this.score = score;
        this.tiidf = tiidf;
    }

    public double getTiidf() {
        return tiidf;
    }

    public Pair setTiidf(double tiidf) {
        this.tiidf = tiidf;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(Pair o) {
        return this.getTiidf() == o.getTiidf() ? 0 : (this.getTiidf() < o.getTiidf() ? 1 : -1);
//                (this.score - o.score) == 0 ?
//                (this.getTiidf() == o.getTiidf() ? 0 : (this.getTiidf() < o.getTiidf() ? 1 : -1)) :
//                (this.score < o.score ? 1 : -1);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "id=" + id +
                ", score=" + score +
                ", tiidf=" + tiidf +
                '}';
    }
}
