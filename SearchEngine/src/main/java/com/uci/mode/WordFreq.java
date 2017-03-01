
package com.uci.mode;

public class WordFreq {

    public String word;
    public int freq;

    public WordFreq(String word) {
        this.word = word;
        this.freq = 0;
    }

    public String getword() {
        return word;
    }

    public int getfreq() {
        return freq;
    }

    public String toString() {
        return word + ":" + freq;
    }

}
