package uk.co.pete_b.advent.aoc2023;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Day19 {
    public static int sumRatingAcceptedParts(final List<String> partSetup) {
        int sum = 0;

        final Map<String, Function<Map<String, Integer>, Result>> functionMap = new HashMap<>();
        boolean buildingFunctions = true;
        for (String setup : partSetup) {
            if (setup.isBlank()) {
                buildingFunctions = false;
                continue;
            }

            if (buildingFunctions) {
                int bracketIndex = setup.indexOf("{");
                final String fnName = setup.substring(0, bracketIndex);
                final String fnSteps = setup.substring(bracketIndex + 1, setup.length() - 1);
                final String[] steps = fnSteps.split(",");
                final Function<Map<String, Integer>, Result> fn = getFunction(steps, functionMap);

                functionMap.put(fnName, fn);

            } else {
                final Map<String, Integer> startSetup = new HashMap<>();
                final String[] values = setup.substring(1, setup.length() - 1).split(",");
                for (String value : values) {
                    final String[] parts = value.split("=");
                    startSetup.put(parts[0], Integer.parseInt(parts[1]));
                }

                if (functionMap.get("in").apply(startSetup) == Result.ACCEPTED) {
                    sum += startSetup.values().stream().mapToInt(Integer::intValue).sum();
                }

            }
        }

        return sum;
    }

    private static Function<Map<String, Integer>, Result> getFunction(String[] steps, Map<String, Function<Map<String, Integer>, Result>> functionMap) {
        final List<PredicateToApply> predicates = new ArrayList<>();
        Function<Map<String, Integer>, Result> elseStatement = null;
        for (String step : steps) {
            if (step.contains(":")) {
                elseStatement = null;
                final String[] parts = step.split(":");
                final Predicate<Map<String, Integer>> predicate = getPredicate(parts[0]);
                final Function<Map<String, Integer>, Result> res = (x) -> Result.getResult(parts[1]).orElseGet(() -> functionMap.get(parts[1]).apply(x));
                predicates.add(new PredicateToApply(predicate, res));
            } else {
                elseStatement = (x) -> Result.getResult(step).orElseGet(() -> functionMap.get(step).apply(x));
            }
        }

        if (elseStatement == null) {
            throw new IllegalStateException();
        }

        final Function<Map<String, Integer>, Result> finalElseStatement = elseStatement;
        return (x) -> {
            for (PredicateToApply predicate : predicates) {
                if (predicate.predicate.test(x)) {
                    return predicate.functionToApply.apply(x);
                }
            }

            return finalElseStatement.apply(x);
        };
    }

    private static Predicate<Map<String, Integer>> getPredicate(String predicate) {
        final String var = predicate.substring(0, 1);
        final int targetValue = Integer.parseInt(predicate.substring(2));
        final String comparator = predicate.substring(1, 2);
        return (x) -> {
            if (comparator.equals(">")) {
                return x.get(var) > targetValue;
            } else {
                return x.get(var) < targetValue;
            }
        };
    }

    public static long sumDistinctParts(final List<String> partSetup) {
        final Map<String, Range> rangeSetup = new HashMap<>();
        rangeSetup.put("x", new Range(1, 4000));
        rangeSetup.put("m", new Range(1, 4000));
        rangeSetup.put("a", new Range(1, 4000));
        rangeSetup.put("s", new Range(1, 4000));

        final List<Map<String, Range>> ranges = new ArrayList<>(Collections.singletonList(rangeSetup));

        Map<String, PredicateFn> functionMap = new HashMap<>();

        for (String setup : partSetup) {
            if (setup.isEmpty()) {
                break;
            }
            int bracketIndex = setup.indexOf("{");
            final String fnName = setup.substring(0, bracketIndex);
            final String fnSteps = setup.substring(bracketIndex + 1, setup.length() - 1);
            final String[] steps = fnSteps.split(",");

            String result = null;
            List<PredicateOption> options = new ArrayList<>();
            for (String step : steps) {
                if (step.contains(":")) {
                    final String[] parts = step.split(":");
                    options.add(new PredicateOption(parts[0], parts[1]));
                } else {
                    result = step;
                }
            }

            functionMap.put(fnName, new PredicateFn(options, result));
        }

        final List<Map<String, Range>> allRanges = applyFunction(functionMap, "in", ranges);

        return allRanges.stream().mapToLong(range -> range.values().stream().map(Range::size).reduce(1L, (i, j) -> i * j)).sum();
    }

    private static List<Map<String, Range>> applyFunction(final Map<String, PredicateFn> functionMap, final String functionName, final List<Map<String, Range>> ranges) {
        final PredicateFn fn = functionMap.get(functionName);
        List<Map<String, Range>> rangesToApply = new ArrayList<>(ranges);
        final List<Map<String, Range>> appliedRanges = new ArrayList<>();

        for (PredicateOption option : fn.options) {
            final PredicateResult result = applyRangePredicate(option.predicate, rangesToApply);
            if (option.result.equals("A")) {
                appliedRanges.addAll(result.matchingPredicate);
            } else if (!option.result.equals("R")) {
                appliedRanges.addAll(applyFunction(functionMap, option.result, result.matchingPredicate));
            }

            rangesToApply = result.notMatchingPredicate;
        }

        if (fn.result.equals("A")) {
            appliedRanges.addAll(rangesToApply);
            return appliedRanges;
        } else if (fn.result.equals("R")) {
            return appliedRanges;
        } else {
            appliedRanges.addAll(applyFunction(functionMap, fn.result, rangesToApply));
            return appliedRanges;
        }
    }

    private static PredicateResult applyRangePredicate(final String predicate, final List<Map<String, Range>> currentRanges) {
        final String var = predicate.substring(0, 1);
        final int targetValue = Integer.parseInt(predicate.substring(2));
        final String comparator = predicate.substring(1, 2);

        final List<Map<String, Range>> matching = new ArrayList<>();
        final List<Map<String, Range>> notMatching = new ArrayList<>();

        for (Map<String, Range> currentRange : currentRanges) {
            final Range targetRange = currentRange.get(var);
            if (comparator.equals(">")) {
                if (targetRange.start <= targetValue && targetValue < targetRange.stop) {
                    final Map<String, Range> noMatch = new HashMap<>(currentRange);
                    noMatch.put(var, new Range(targetRange.start, targetValue));
                    notMatching.add(noMatch);

                    final Map<String, Range> match = new HashMap<>(currentRange);
                    match.put(var, new Range(targetValue + 1, targetRange.stop));
                    matching.add(match);
                } else if (targetRange.stop < targetValue) {
                    notMatching.add(currentRange);
                } else if (targetRange.start > targetValue) {
                    matching.add(currentRange);
                }
            } else {
                if (targetRange.start < targetValue && targetValue <= targetRange.stop) {
                    final Map<String, Range> match = new HashMap<>(currentRange);
                    match.put(var, new Range(targetRange.start, targetValue - 1));
                    matching.add(match);

                    final Map<String, Range> noMatch = new HashMap<>(currentRange);
                    noMatch.put(var, new Range(targetValue, targetRange.stop));
                    notMatching.add(noMatch);
                } else if (targetRange.stop < targetValue) {
                    notMatching.add(currentRange);
                } else if (targetRange.start > targetValue) {
                    matching.add(currentRange);
                }
            }
        }

        return new PredicateResult(matching, notMatching);
    }

    private enum Result {
        ACCEPTED("A"), REJECTED("R");

        private final String result;

        Result(final String result) {
            this.result = result;
        }

        public static Optional<Result> getResult(final String result) {
            for (Result res : Result.values()) {
                if (res.result.equals(result)) {
                    return Optional.of(res);
                }
            }

            return Optional.empty();
        }
    }

    private record PredicateToApply(Predicate<Map<String, Integer>> predicate, Function<Map<String, Integer>, Result> functionToApply) {
    }

    private record Range(int start, int stop) {
        public long size() {
            return stop - start + 1;
        }
    }

    private record PredicateResult(List<Map<String, Range>> matchingPredicate, List<Map<String, Range>> notMatchingPredicate) {
    }

    private record PredicateFn(List<PredicateOption> options, String result) {
    }

    private record PredicateOption(String predicate, String result) {
    }
}
