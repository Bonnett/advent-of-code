package uk.co.pete_b.advent.aoc2022;

import java.util.*;

public class Day03 {

    private static final Map<Character, Integer> PRIORITY_MAP = new HashMap<>();

    static {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < alphabet.length(); i++) {
            PRIORITY_MAP.put(alphabet.charAt(i), i + 1);
        }

        for (int i = 0; i < alphabet.length(); i++) {
            PRIORITY_MAP.put(alphabet.toUpperCase().charAt(i), i + 27);
        }

    }

    public static int sumPriorities(final List<String> rucksacks) {
        int priorityTotal = 0;

        for (String rucksack : rucksacks) {
            final String compartmentOne = rucksack.substring(0, rucksack.length() / 2);
            final String compartmentTwo = rucksack.substring(rucksack.length() / 2);
            final Set<Character> compartmentOneItems = parseCompartment(compartmentOne);
            final Set<Character> compartmentTwoItems = parseCompartment(compartmentTwo);

            final Character sharedChar = compartmentOneItems.stream().filter(compartmentTwoItems::contains).findFirst().orElseThrow();
            priorityTotal += PRIORITY_MAP.get(sharedChar);
        }

        return priorityTotal;
    }

    public static int sumPrioritiesInGroupsOfThree(final List<String> rucksacks) {
        int priorityTotal = 0;

        for (int i=0; i<rucksacks.size(); i++) {
            final String rucksackOne = rucksacks.get(i);
            final String rucksackTwo = rucksacks.get(++i);
            final String rucksackThree = rucksacks.get(++i);
            final Set<Character> rucksackOneItems = parseCompartment(rucksackOne);
            final Set<Character> rucksackTwoItems = parseCompartment(rucksackTwo);
            final Set<Character> rucksackThreeItems = parseCompartment(rucksackThree);

            final Character sharedChar = rucksackOneItems.stream().filter(rucksackTwoItems::contains).filter(rucksackThreeItems::contains).findFirst().orElseThrow();
            priorityTotal += PRIORITY_MAP.get(sharedChar);
        }

        return priorityTotal;
    }

    private static Set<Character> parseCompartment(final String compartment) {
        final Set<Character> items = new TreeSet<>();
        for (int i = 0; i < compartment.length(); i++) {
            items.add(compartment.charAt(i));
        }

        return items;
    }
}
