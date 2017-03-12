
package com.uci.constant;

public enum Table {
    DOCUMENT("document"), TERM("term"), ANCHOR("anchor"), RANK("rank"), TWO_GRAM("two_gram");
    String name;

    Table(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}