package uk.co.pete_b.advent.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public class Day05 {
    public static Answer findLowestLocationSeedNumber(final List<String> maps) {
        final List<LongUnaryOperator> functions = new ArrayList<>();
        final List<Long> initialSeeds = Arrays.stream(maps.get(0).substring(7).split(" ")).mapToLong(Long::parseLong).boxed().toList();

        String currentMap = null;
        final List<String> rules = new ArrayList<>();
        for (int i = 2; i < maps.size(); i++) {
            final String rule = maps.get(i);
            if (rule.isEmpty() && currentMap != null) {
                // Create the rule
                functions.add(createRule(rules));
                rules.clear();
                continue;
            }

            if (rule.contains("-to-")) {
                currentMap = rule.substring(0, rule.indexOf(" "));
            } else {
                rules.add(rule);
            }
        }

        // And the last rule
        functions.add(createRule(rules));
        LongStream value = initialSeeds.stream().mapToLong(Long::longValue);

        for (LongUnaryOperator function : functions) {
            value = value.map(function);
        }

        final long lowestIndividualLocation = value.boxed().min(Long::compareTo).orElseThrow();

        final List<SeedRange> seedRanges = new ArrayList<>();
        for (int i = 0; i < initialSeeds.size(); i++) {
            seedRanges.add(new SeedRange(initialSeeds.get(i), initialSeeds.get(++i)));
        }

        final long lowestValue = seedRanges.stream().map(seedRange -> {
            LongStream lowestStream = LongStream.range(seedRange.startValue, seedRange.startValue + seedRange.rangeSize).parallel();
            for (LongUnaryOperator function : functions) {
                lowestStream = lowestStream.map(function);
            }
            return lowestStream.min().orElseThrow();
        }).min(Long::compareTo).orElseThrow();

        return new Answer(lowestIndividualLocation, lowestValue);
    }

    private static LongUnaryOperator createRule(List<String> ruleDescriptions) {
        final List<LongUnaryOperator> rules = new ArrayList<>();
        for (String ruleDescription : ruleDescriptions) {
            final List<Long> parts = Arrays.stream(ruleDescription.split(" ")).mapToLong(Long::parseLong).boxed().toList();
            final Long start = parts.get(1);
            final Long target = parts.get(0);
            final Long range = parts.get(2);
            final LongUnaryOperator rule = (x) -> {
                if (x >= start && x <= start + range) {
                    return (x - start) + target;
                }

                return -1L;
            };
            rules.add(rule);
        }

        return (x) -> {
            for (LongUnaryOperator rule : rules) {
                final long result = rule.applyAsLong(x);
                if (result != -1L) {
                    return result;
                }
            }

            return x;
        };
    }

    private record SeedRange(long startValue, long rangeSize) {
    }

    public record Answer(long lowestIndividualLocation, long lowestRangeLocation) {
    }
}
