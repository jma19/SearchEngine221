package com.uci.mode;

import java.util.regex.Pattern;

/**
 * Created by junm5 on 3/1/17.
 */
public class Pair implements Comparable<Pair> {
    private int id;
    private double score;

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
        return (this.score - o.score) == 0 ? 0 : (this.score > o.score ? 1 : -1);
    }
}
