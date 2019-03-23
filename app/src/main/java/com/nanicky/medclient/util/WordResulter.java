package com.nanicky.medclient.util;

import java.util.ArrayList;
import java.util.List;

public class WordResulter {
    public List<WordResult> words = new ArrayList<>();
    String result;

    public WordResulter(List<WordResult> words, String result) {
        this.words = words;
        this.result = result;
    }

    WordResult check(int index) {
        for(WordResult wr: words) {
            int start = wr.start;
            int end = wr.end;
            if (index >= start && index <= end) {
                return wr;
            }
        }
        return null;
    }
}
