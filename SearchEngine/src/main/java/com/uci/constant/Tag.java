package com.uci.constant;

/**
 * Created by junm5 on 2/28/17.
 */
public enum Tag {
    URL(100), ANCHOR(100), TITLE(100), BODY(1);
    private int value;

    Tag(int value) {
        this.value = value;
    }

    public int getWight() {
        return value;
    }

}
