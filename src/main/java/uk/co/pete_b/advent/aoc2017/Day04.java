package uk.co.pete_b.advent.aoc2017;

import java.util.*;

public class Day04 {
    public static boolean isPassphraseValidPart1(final String passphrase) {
        final String[] parts = passphrase.split("\\s");
        final Set<String> uniqueWords = new HashSet<>();
        final List<String> allWords = new ArrayList<>();
        for (String part : parts) {
            uniqueWords.add(part);
            allWords.add(part);
        }

        return uniqueWords.size() == allWords.size();
    }

    public static boolean isPassphraseValidPart2(final String passphrase) {
        final String[] parts = passphrase.split("\\s");
        final Set<String> uniqueWords = new HashSet<>();
        final List<String> allWords = new ArrayList<>();
        for (String part : parts) {
            char[] chars = part.toCharArray();
            Arrays.sort(chars);
            uniqueWords.add(new String(chars));
            allWords.add(part);
        }

        return uniqueWords.size() == allWords.size();
    }
}
