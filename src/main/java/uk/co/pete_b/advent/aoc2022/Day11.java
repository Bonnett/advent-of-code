package uk.co.pete_b.advent.aoc2022;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {
    public static Long calculateMonkeyBusiness(final List<String> monkeyRules, int iterations, boolean divideByThree) {
        final List<Monkey> monkeys = parseMonkeys(monkeyRules);
        final Map<Integer, Long> monkeyInspections = new HashMap<>();

        final BigInteger divisor = BigInteger.valueOf(3L);
        final BigInteger modulus = BigInteger.valueOf(monkeys.stream().map(x -> x.divisor.longValue()).reduce(1L, (a, b) -> a * (b)));
        final Function<BigInteger, BigInteger> divisorFn = divideByThree ? (x) -> x.divide(divisor) : (x) -> x.mod(modulus);

        for (int i = 0; i < iterations; i++) {
            for (Monkey monkey : monkeys) {
                final Iterator<BigInteger> iterator = monkey.items().iterator();
                while (iterator.hasNext()) {
                    final BigInteger item = iterator.next();
                    final BigInteger updated = divisorFn.apply(monkey.operationFn().apply(item));
                    if (monkey.testFn().apply(updated)) {
                        monkeys.get(monkey.trueMonkey()).items().add(updated);
                    } else {
                        monkeys.get(monkey.falseMonkey()).items().add(updated);
                    }
                    monkeyInspections.compute(monkey.monkeyId(), (id, count) -> {
                        if (count == null) {
                            count = 0L;
                        }
                        count++;

                        return count;
                    });
                    iterator.remove();
                }
            }
        }

        final List<Long> sortedValues = monkeyInspections.values().stream().sorted(Comparator.reverseOrder()).toList();

        return sortedValues.get(0) * sortedValues.get(1);
    }

    private static List<Monkey> parseMonkeys(List<String> monkeyRules) {
        final List<Monkey> monkeys = new ArrayList<>();
        for (int i = 0; i < monkeyRules.size(); i++) {
            final int monkeyId = Integer.parseInt(monkeyRules.get(i++).substring(7, 8));
            final List<BigInteger> startingItems = Arrays.stream(monkeyRules.get(i++).substring(18).split(", "))
                    .map(BigInteger::new)
                    .collect(Collectors.toList());
            final String operation = monkeyRules.get(i++);
            final Function<BigInteger, BigInteger> operationFn = determineOperation(operation.substring(19));

            final BigInteger divisor = new BigInteger(monkeyRules.get(i++).substring(21));
            final Function<BigInteger, Boolean> testFn = (x) -> x.mod(divisor).equals(new BigInteger("0"));

            final int trueMonkey = Integer.parseInt(monkeyRules.get(i++).substring(29));
            final int falseMonkey = Integer.parseInt(monkeyRules.get(i++).substring(30));

            final Monkey monkey = new Monkey(monkeyId, startingItems, operationFn, divisor, testFn, trueMonkey, falseMonkey);
            monkeys.add(monkey);
        }

        return monkeys;
    }

    private static Function<BigInteger, BigInteger> determineOperation(final String operation) {
        if (operation.equals("old * old")) {
            return (x) -> x.multiply(x);
        } else if (operation.startsWith("old +")) {
            final BigInteger addition = new BigInteger(operation.substring(6));
            return (x) -> x.add(addition);
        } else if (operation.startsWith("old *")) {
            final BigInteger multiplication = new BigInteger(operation.substring(6));
            return (x) -> x.multiply(multiplication);
        }

        throw new IllegalArgumentException("Invalid operation: " + operation);
    }

    private record Monkey(int monkeyId, List<BigInteger> items, Function<BigInteger, BigInteger> operationFn, BigInteger divisor,
                          Function<BigInteger, Boolean> testFn, int trueMonkey, int falseMonkey) {
    }
}
