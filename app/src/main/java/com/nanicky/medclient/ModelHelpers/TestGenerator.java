package com.nanicky.medclient.ModelHelpers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class TestGenerator {

    private static String[] massKeyWords = {"один", "апельсин", "весна", "сложность", "армия", "вечеринка"};
    private static String AB = "0123456789Аыаофщшпоукфкпжщзуофкзщпкофщузжопкзщжфущокзщж";
    private static SecureRandom rnd = new SecureRandom();

    public WordResulter generateTest() {
        List<WordResult> wordResults = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < massKeyWords.length; i++) {
            int k = (int) (Math.random() * 9 + 1);
            sb.append(randomString(k));
            int start = sb.length();
            sb.append(massKeyWords[i]);
            int end = sb.length();
            WordResult wordResult = new WordResult(start, end);
            wordResults.add(wordResult);
        }

        return new WordResulter(wordResults, sb.toString());
    }


    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

}

