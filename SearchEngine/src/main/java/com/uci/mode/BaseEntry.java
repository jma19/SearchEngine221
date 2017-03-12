package com.uci.mode;

import com.uci.constant.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junm5 on 2/28/17.
 */
public class BaseEntry {
    private Tag tag;
    private List<Integer> pos;
    private int termFre;

    public BaseEntry setTermFre(int termFre) {
        this.termFre = termFre;
        return this;
    }

    public Tag getTag() {
        return tag;
    }

    public BaseEntry() {
        pos = new ArrayList<>();
    }

    public BaseEntry setTag(Tag name) {
        this.tag = name;
        return this;
    }

    public int getTermFre() {
        return pos.size();
    }

    public List<Integer> getPos() {
        return pos;
    }

    public BaseEntry setPos(List<Integer> pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "tag='" + tag + '\'' +
                ", pos=" + pos +
                '}';
    }
}
