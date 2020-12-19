package uk.co.pete_b.advent.aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day19 {
    public static long countMatchingLines(final String inputData, boolean withAmendedRules) {
        final String[] data = inputData.split("\n");
        final Map<Integer, String> inputRule = new HashMap<>();
        final List<String> dataToMatch = new ArrayList<>();
        for (String datum : data) {
            if (datum.startsWith("a") || datum.startsWith("b")) {
                dataToMatch.add(datum);
            } else if (datum.length() != 0) {
                final int colonIndex = datum.indexOf(":");
                final int ruleNumber = Integer.parseInt(datum.substring(0, colonIndex));
                String ruleValue = datum.substring(colonIndex + 1).trim();
                if (ruleValue.startsWith("\"")) {
                    ruleValue = ruleValue.substring(1, ruleValue.length() - 1);
                }

                inputRule.put(ruleNumber, ruleValue);
            }
        }

        final Pattern rule = Pattern.compile("^" + buildRegularExpression(inputRule, withAmendedRules, 0) + "$");

        return dataToMatch.stream().filter(rule.asMatchPredicate()).count();
    }

    private static String buildRegularExpression(final Map<Integer, String> inputRule, final boolean withAmendedRules, int ruleNumber) {

        if (withAmendedRules && ruleNumber == 8) {
            return buildRegularExpression(inputRule, true, 42) + "+";
        } else if (withAmendedRules && ruleNumber == 11) {
            final String rule42 = buildRegularExpression(inputRule, true, 42);
            final String rule31 = buildRegularExpression(inputRule, true, 31);

            final StringBuilder rule = new StringBuilder();
            rule.append("(");

            // Technically this doesn't solve every possible option - however the input data has a low max length so we can just loop up to 4 repeated patterns
            for (int i = 1; i < 5; i++) {
                rule.append(String.format("%s{%d}%s{%d}|", rule42, i, rule31, i));
            }
            rule.deleteCharAt(rule.length() - 1);
            rule.append(")");

            return rule.toString();
        } else {

            final StringBuilder compiledRule = new StringBuilder();
            final String rule = inputRule.get(ruleNumber);
            if (rule.contains("|")) {
                compiledRule.append("(");
            }

            final String[] parts = rule.split(" ");
            for (String part : parts) {
                if (part.matches("[ab]")) {
                    compiledRule.append(part);
                } else if (part.equals("|")) {
                    compiledRule.append(part);
                } else {
                    compiledRule.append(buildRegularExpression(inputRule, withAmendedRules, Integer.parseInt(part)));
                }
            }

            if (rule.contains("|")) {
                compiledRule.append(")");
            }

            return compiledRule.toString();
        }
    }
}
