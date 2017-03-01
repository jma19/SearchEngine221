package com.uci.constant;

/**
 * Created by junm5 on 2/28/17.
 */
public enum Tag {
    ANCHOR(100), TITLE(8), BODY(2);
    private int value;

    Tag(int value) {
        this.value = value;
    }

    public int getWight() {
        return value;
    }

}
