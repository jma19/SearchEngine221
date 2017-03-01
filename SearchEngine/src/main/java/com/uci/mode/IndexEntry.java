package com.uci.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junm5 on 2/22/17.
 */
public class IndexEntry {
    //document id
    private int id;
    private double tfIdf;
    private List<Integer> pos;

    public double getTfIdf() {
        return tfIdf;
    }

    public IndexEntry setTfIdf(double tfIdf) {
        this.tfIdf = tfIdf;
        return this;
    }

    public IndexEntry() {
        super();
    }

    public int getTermFre() {
        return pos.size();
    }

    public IndexEntry(int id) {
        this.id = id;
        pos = new ArrayList();
    }

    public void addPos(Integer pos) {
        this.pos.add(pos);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getPos() {
        return pos;
    }

    public void setPos(List<Integer> pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "IndexEntry{" +
                "id=" + id +
                ", tfIdf=" + tfIdf +
                ", pos=" + pos +
                '}';
    }
}
