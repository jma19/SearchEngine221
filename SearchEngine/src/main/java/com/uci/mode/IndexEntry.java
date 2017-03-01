package com.uci.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by junm5 on 2/22/17.
 */
public class IndexEntry {
    //document id
    private int id;
    private double tfIdf;

    private List<BaseEntry> baseEntries;

    public IndexEntry(int id) {
        this.id = id;
        baseEntries = new ArrayList<>();
    }

    public int getTermFre() {
        int fre = 0;
        for (BaseEntry baseEntry : baseEntries) {
            fre += baseEntry.getTermFre() * baseEntry.getTag().getWight();
        }
        return fre;
    }

    public List<BaseEntry> getBaseEntries() {
        return baseEntries;
    }

    public void setBaseEntries(List<BaseEntry> baseEntries) {
        this.baseEntries = baseEntries;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IndexEntry{" +
                "id=" + id +
                ", tfIdf=" + tfIdf +
                ", baseEntries=" + baseEntries +
                '}';
    }
}
