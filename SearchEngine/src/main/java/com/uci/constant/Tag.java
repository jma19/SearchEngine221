package com.uci.constant;

/**
 * Created by junm5 on 2/28/17.
 */
public enum Tag {
    URL(30), ANCHOR(50), TITLE(50), BODY(1), TWOGRAM_TITLE(100), TWOGRAM_ANCHOR(100), TWOGRAM_BODY(1);
    private int value;

    Tag(int value) {
        this.value = value;
    }

    public int getWight() {
        return value;
    }

}
