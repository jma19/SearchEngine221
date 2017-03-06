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
    private int termFre;
    private List<BaseEntry> baseEntries;

    public IndexEntry(int id) {
        this.id = id;
        baseEntries = new ArrayList<>();
    }

    public IndexEntry setTermFre(int termFre) {
        this.termFre = termFre;
        return this;
    }

    public int getTermFre() {
        termFre = 0;
        if (baseEntries != null) {
            for (BaseEntry baseEntry : baseEntries) {
                termFre += baseEntry.getTermFre() * baseEntry.getTag().getWight();
            }
        }
        return termFre;
    }

    public List<BaseEntry> getBaseEntries() {
        return baseEntries;
    }

    public IndexEntry setBaseEntries(List<BaseEntry> baseEntries) {
        this.baseEntries = baseEntries;
        return this;
    }

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

    public int getId() {
        return id;
    }

    public IndexEntry setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "IndexEntry{" +
                "id=" + id +
                ", tfIdf=" + tfIdf +
                ", termFre=" + termFre +
                ", baseEntries=" + baseEntries +
                '}';
    }
}
