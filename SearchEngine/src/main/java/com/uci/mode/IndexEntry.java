package com.uci.mode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by junm5 on 2/22/17.
 */
public class IndexEntry {
    //document id
    private int id;
    private double tfIdf;
    private Set<BaseEntry> baseEntries;

    public IndexEntry(int id) {
        this.id = id;
        baseEntries = new HashSet<>();
    }

    public int getTermFre() {
        int fre = 0;
        for (BaseEntry baseEntry : baseEntries) {
            fre += baseEntry.getTermFre() * baseEntry.getTag().getWight();
        }
        return fre;
    }

    public Set<BaseEntry> getBaseEntries() {
        return baseEntries;
    }

    public IndexEntry setBaseEntries(Set<BaseEntry> baseEntries) {
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexEntry that = (IndexEntry) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
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
