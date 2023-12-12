package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Day12 {
    public static int sumOfPossibleCombinations(final List<String> rules) {
        return rules.parallelStream().mapToInt(rule -> {
            int space = rule.indexOf(" ");
            final String springLayout = rule.substring(0, space);
            final List<Integer> contiguousDamage = Arrays.stream(rule.substring(space + 1).split(",")).map(Integer::parseInt).toList();

            final List<StringBuilder> possibleLayouts = new ArrayList<>();
            for (int i = 0; i < springLayout.length(); i++) {
                char c = springLayout.charAt(i);
                if (possibleLayouts.isEmpty()) {
                    if (c == '?') {
                        possibleLayouts.add(new StringBuilder("."));
                        possibleLayouts.add(new StringBuilder("#"));
                    } else {
                        possibleLayouts.add(new StringBuilder("" + c));
                    }
                } else {
                    final List<StringBuilder> newSprings = new ArrayList<>();
                    for (StringBuilder sb : possibleLayouts) {
                        if (c == '?') {
                            newSprings.add(new StringBuilder(sb).append('.'));
                            sb.append('#');
                        } else {
                            sb.append(c);
                        }
                    }
                    possibleLayouts.addAll(newSprings);
                }
            }

            return possibleLayouts.parallelStream().mapToInt(possibleLayout -> {
                int sum = 0;
                List<Integer> layout = Arrays.stream(possibleLayout.toString().split("\\.")).map(String::length).filter(x -> x > 0).toList();
                if (layout.equals(contiguousDamage)) {
                    sum++;
                }
                return sum;
            }).sum();
        }).sum();
    }

    public static long sumOfPossibleCombinationsUnfolded(final List<String> rules) {
        return rules.parallelStream().mapToLong(rule -> {
            int space = rule.indexOf(" ");
            final String tempSpringLayout = rule.substring(0, space);
            final String springLayout = StringUtils.join(Arrays.asList(tempSpringLayout, tempSpringLayout, tempSpringLayout, tempSpringLayout, tempSpringLayout), "?");
            final List<Integer> tempContiguousDamage = Arrays.stream(rule.substring(space + 1).split(",")).map(Integer::parseInt).toList();
            final List<Integer> contiguousDamage = new ArrayList<>(tempContiguousDamage);
            contiguousDamage.addAll(tempContiguousDamage);
            contiguousDamage.addAll(tempContiguousDamage);
            contiguousDamage.addAll(tempContiguousDamage);
            contiguousDamage.addAll(tempContiguousDamage);

            return calculatePermutations(new HashMap<>(), new SpringStatus(springLayout, contiguousDamage));
        }).sum();
    }

    // Heavily adapted from Reddit post
    private static long calculatePermutations(final Map<SpringStatus, Long> cache, final SpringStatus status) {
        if (cache.containsKey(status)) {
            return cache.get(status);
        }

        if (status.layout.isEmpty()) {
            return status.contiguousGroups.isEmpty() ? 1 : 0;
        }

        long permutations = 0;
        final char nextSpring = status.layout.charAt(0);
        final String remainder = status.layout.substring(1);

        if (nextSpring == '.') {
            permutations = calculatePermutations(cache, new SpringStatus(remainder, status.contiguousGroups));
        } else if (nextSpring == '?') {
            permutations = calculatePermutations(cache, new SpringStatus('.' + remainder, status.contiguousGroups)) +
                    calculatePermutations(cache, new SpringStatus('#' + remainder, status.contiguousGroups));
        } else if (!status.contiguousGroups.isEmpty()) {
            final int currentDamagedGroupSize = status.contiguousGroups.get(0);
            final List<Integer> nextContiguousGroups = status.contiguousGroups.subList(1, status.contiguousGroups.size());
            if (currentDamagedGroupSize <= status.layout.length() && status.layout.substring(0, currentDamagedGroupSize).matches("[?#]+")) {
                if (currentDamagedGroupSize == status.layout.length()) {
                    permutations = (nextContiguousGroups.isEmpty()) ? 1 : 0;
                } else if (status.layout.charAt(currentDamagedGroupSize) == '.') {
                    permutations = calculatePermutations(cache, new SpringStatus(status.layout.substring(currentDamagedGroupSize + 1), nextContiguousGroups));
                } else if (status.layout.charAt(currentDamagedGroupSize) == '?') {
                    permutations = calculatePermutations(cache, new SpringStatus("." + status.layout.substring(currentDamagedGroupSize + 1), nextContiguousGroups));
                }
            }
        }

        cache.put(status, permutations);
        return permutations;
    }

    private record SpringStatus(String layout, List<Integer> contiguousGroups) {
    }
}
