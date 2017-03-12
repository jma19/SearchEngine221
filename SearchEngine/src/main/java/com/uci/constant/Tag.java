package com.uci.constant;

/**
 * Created by junm5 on 2/28/17.
 */
public enum Tag {
    URL(7), ANCHOR(10), TITLE(20), BODY(1), TWOGRAM_TITLE(80), TWOGRAM_BODY(10);
    private int value;

    Tag(int value) {
        this.value = value;
    }

    public int getWight() {
        return value;
    }

}
