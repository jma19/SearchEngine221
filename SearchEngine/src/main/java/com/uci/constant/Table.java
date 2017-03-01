
package com.uci.constant;

public enum Table {
    DOCUMENT("document"), TERM("term"), ANCHOR("anchor");
    String name;

    Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}