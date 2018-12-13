package uk.co.pete_b.advent.aoc2018;

import java.util.*;

public class Day12 {
    private static final int RULE_LENGTH = 5;

    public static long calculateTotal(final List<String> input, final long iterations) {
        final String startState = input.get(0).substring(15);
        final Map<String, Character> rules = getRules(input.subList(2, input.size()));

        boolean foundCycle = false;
        long previousCycleTotal = -1;
        long cycleOffset = -1;
        long cycleMultiplier = -1;

        final String padding = "....";
        int startOffset = 4;
        String inputState = padding + startState + padding;
        final Set<String> routeList = new HashSet<>();

        for (int iteration = 1; iteration <= iterations; iteration++) {
            final StringBuilder sb = new StringBuilder(padding);
            for (int charIndex = 2; charIndex < inputState.length() - 2; charIndex++) {
                String key;
                int startIndex = charIndex - 2;
                int endIndex = charIndex + 3;
                key = inputState.substring(startIndex, endIndex);
                sb.append(rules.getOrDefault(key, '.'));
            }

            sb.append(padding);
            inputState = sb.toString();
            startOffset += 2;
            final String key = inputState.substring(inputState.indexOf('#') - 5, 1 + inputState.lastIndexOf('#'));
            if (foundCycle) {
                long total = getTotal(startOffset, inputState);
                cycleMultiplier = total - previousCycleTotal;
                cycleOffset = total - cycleMultiplier * iteration;
                break;
            }
            if (routeList.contains(key)) {
                foundCycle = true;
                previousCycleTotal = getTotal(startOffset, inputState);
            }

            routeList.add(key);
        }

        long totalAtEndOfIterations = cycleMultiplier * iterations + cycleOffset;

        return foundCycle ? totalAtEndOfIterations : getTotal(startOffset, inputState);
    }

    private static long getTotal(int startOffset, String inputState) {
        long total = 0;
        for (int i = 0; i < inputState.length(); i++) {
            total += inputState.charAt(i) == '#' ? i - startOffset : 0;
        }
        return total;
    }

    private static Map<String, Character> getRules(List<String> input) {
        final Map<String, Character> rules = new HashMap<>();
        for (final String line : input) {
            final String rule = line.substring(0, RULE_LENGTH);
            final Character output = line.charAt(9);
            rules.put(rule, output);
        }

        return rules;
    }
}
