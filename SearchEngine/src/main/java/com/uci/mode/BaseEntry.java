package com.uci.mode;

import com.uci.constant.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by junm5 on 2/28/17.
 */
public class BaseEntry {
    private Tag tag;
    private Set<Integer> pos;

    public Tag getTag() {
        return tag;
    }
    public BaseEntry(){
        pos = new HashSet<>();
    }
    public BaseEntry setTag(Tag name) {
        this.tag = name;
        return this;
    }

    public int getTermFre() {
        return pos.size();
    }

    public Set<Integer> getPos() {
        return pos;
    }

    public BaseEntry setPos(Set<Integer> pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntry baseEntry = (BaseEntry) o;

        return tag != null ? tag.equals(baseEntry.tag) : baseEntry.tag == null;

    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "tag='" + tag + '\'' +
                ", pos=" + pos +
                '}';
    }
}
