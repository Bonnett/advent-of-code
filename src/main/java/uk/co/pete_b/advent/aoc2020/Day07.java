package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^(.+) bags contain (?:([0-9]+ .+ bags?(?:, )?)+|(no other bags)).$");
    private static final Pattern BAG_PATTERN = Pattern.compile("^([0-9]+) (.+) bags?$");
    private static final String SHINY_GOLD = "shiny gold";

    public static Answer countGoldBags(final String inputRules) {
        final Map<String, List<Pair<String, Integer>>> bagCollection = new HashMap<>();

        final String[] lines = inputRules.split("\n");
        for (String line : lines) {
            final Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.find()) {
                if (matcher.group(3) != null) {
                    bagCollection.put(matcher.group(1), Collections.emptyList());
                } else {
                    final String[] bags = matcher.group(2).split(", ");
                    final String bagColor = matcher.group(1);
                    final List<Pair<String, Integer>> containedBags = new ArrayList<>();

                    for (String bag : bags) {
                        final Matcher bagMatcher = BAG_PATTERN.matcher(bag);
                        if (bagMatcher.find()) {
                            containedBags.add(Pair.of(bagMatcher.group(2), Integer.parseInt(bagMatcher.group(1))));
                        }
                    }

                    bagCollection.put(bagColor, containedBags);
                }
            }
        }

        final long containingGoldBag = bagCollection.entrySet()
                .stream()
                .filter(color -> !color.getKey().equals(SHINY_GOLD))
                .filter(bag -> containsGoldBag(bagCollection, Pair.of(bag.getKey(), bag.getValue())))
                .count();


        // Take one off to include the bag we're searching as it's the shiny gold bag
        final int bagsContained = calculateContents(bagCollection, bagCollection.get(SHINY_GOLD)) - 1;

        return new Answer(containingGoldBag, bagsContained);
    }

    private static int calculateContents(final Map<String, List<Pair<String, Integer>>> bagCollection, final List<Pair<String, Integer>> contents) {
        int numberContained = 1;

        for (Pair<String, Integer> content : contents) {
            numberContained += (content.getValue() * calculateContents(bagCollection, bagCollection.get(content.getKey())));
        }

        return numberContained;
    }

    private static boolean containsGoldBag(final Map<String, List<Pair<String, Integer>>> bagCollection, final Pair<String, List<Pair<String, Integer>>> contents) {
        if (contents.getKey().equals(SHINY_GOLD)) {
            return true;
        } else {
            for (Pair<String, Integer> bag : contents.getValue()) {
                if (containsGoldBag(bagCollection, Pair.of(bag.getKey(), bagCollection.get(bag.getKey())))) {
                    return true;
                }
            }
        }

        return false;
    }

    public static class Answer {
        private final long numberContainingGoldBags;
        private final int bagsInsideGoldBag;

        Answer(long numberContainingGoldBags, int bagsInsideGoldBag) {
            this.numberContainingGoldBags = numberContainingGoldBags;
            this.bagsInsideGoldBag = bagsInsideGoldBag;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).numberContainingGoldBags == this.numberContainingGoldBags && ((Answer) otherAnswer).bagsInsideGoldBag == this.bagsInsideGoldBag;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Bags containing a shiny gold bag: %d, Number of bags inside a shiny gold bag: %d", numberContainingGoldBags, bagsInsideGoldBag);
        }
    }
}
