package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Day24 {
    public static long findMonad(final List<String> rules, boolean highest) {

        // This is based on this python:
        // https://www.reddit.com/r/adventofcode/comments/rnejv5/comment/hpu84cj/?utm_source=reddit&utm_medium=web2x&context=3
        final List<Triple<Integer, Integer, Integer>> constants = new ArrayList<>();
        // 14 Characters
        for (int i = 0; i < 14; i++) {
            // Each set of instructions is 18
            int offset = i * 18;
            // Div operation on Z
            int a = Integer.parseInt(rules.get(4 + offset).substring(6));
            // Add operation on X
            int b = Integer.parseInt(rules.get(5 + offset).substring(6));
            // Add operation on Y
            int c = Integer.parseInt(rules.get(15 + offset).substring(6));
            constants.add(Triple.of(a, b, c));
        }

        final Map<Integer, Map<Integer, List<Pair<Integer, Integer>>>> levels = new HashMap<>();
        buildLevels(levels, constants, 13, Set.of(0));

        final StringBuilder solution = new StringBuilder();
        final List<Integer> digits = new ArrayList<>();
        if (solve(levels, 0, digits, highest, solution)) {
            // Check the output
            final ALU alu = new ALU(rules);
            alu.runProgramme(solution.toString());
            final SimplifiedALU simplifiedALU = new SimplifiedALU();
            if (alu.isValidProgramme() && simplifiedALU.runComputer(solution.toString())) {
                return Long.parseLong(solution.toString());
            }
        }

        return -1L;
    }

    private static void buildLevels(final Map<Integer, Map<Integer, List<Pair<Integer, Integer>>>> levels,
                                    final List<Triple<Integer, Integer, Integer>> constants,
                                    final int i, final Set<Integer> keyset) {

        final Triple<Integer, Integer, Integer> nums = constants.get(i);
        int A = nums.getLeft();
        int B = nums.getMiddle();
        int C = nums.getRight();
        final Map<Integer, List<Pair<Integer, Integer>>> pairsMap = new HashMap<>();
        for (int w = 9; w > 0; w--) {
            final int finalW = w;
            for (Integer z : keyset) {
                for (int a = 0; a < A; a++) {
                    int pz = z * A + a;
                    if (pz % 26 + B == w) {
                        if (pz / A == z) {
                            pairsMap.compute(pz, (integer, pairs) -> {
                                if (pairs == null) {
                                    pairs = new ArrayList<>();
                                }
                                pairs.add(Pair.of(finalW, z));
                                return pairs;
                            });
                        }
                    }

                    pz = Math.round((((float) z - w - C) / 26) * A + a);
                    if (pz % 26 + B != w) {
                        if (pz / A * 26 + w + C == z) {
                            pairsMap.compute(pz, (integer, pairs) -> {
                                if (pairs == null) {
                                    pairs = new ArrayList<>();
                                }
                                pairs.add(Pair.of(finalW, z));
                                return pairs;
                            });
                        }
                    }
                }
            }
        }

        levels.put(i, pairsMap);

        if (i > 0) {
            buildLevels(levels, constants, i - 1, new TreeSet<>(pairsMap.keySet()).descendingSet());
        }
    }

    private static boolean solve(final Map<Integer, Map<Integer, List<Pair<Integer, Integer>>>> levels,
                                 final int z, final List<Integer> digits, final boolean highest,
                                 final StringBuilder solution) {
        if (digits.size() == 14) {
            solution.append(StringUtils.join(digits, ""));
            return true;
        }

        if (!levels.get(digits.size()).containsKey(z)) {
            return false;
        }

        final List<Pair<Integer, Integer>> pairs = levels.get(digits.size()).get(z);
        pairs.sort(Comparator.comparingInt(Pair::getLeft));
        if (highest) {
            Collections.reverse(pairs);
        }

        for (Pair<Integer, Integer> pair : pairs) {
            final List<Integer> possibleSolution = new ArrayList<>(digits);
            possibleSolution.add(pair.getLeft());
            if (solve(levels, pair.getRight(), possibleSolution, highest, solution)) {
                return true;
            }
        }

        return false;
    }

    private static class ALU {
        private static final String VARIABLE_REGEX = "[wxyz]";

        private final Map<String, AtomicInteger> variables = new HashMap<>();
        private final List<FunctionToApply> functionsToRun = new ArrayList<>();

        private record FunctionToApply(String rule, TriFunction<Map<String, AtomicInteger>, String, Supplier<String>,
                Boolean> function, String variableOne, Supplier<String> variableTwo) {
            @Override
            public String toString() {
                return rule;
            }
        }

        private int index = 0;
        private String inputValue;

        public ALU(final List<String> inputRules) {
            this.initialise();
            final Map<String, TriFunction<Map<String, AtomicInteger>, String, Supplier<String>, Boolean>> functionMap = new HashMap<>();
            functionMap.put("inp", this::inp);
            functionMap.put("add", this::add);
            functionMap.put("div", this::div);
            functionMap.put("mul", this::mul);
            functionMap.put("mod", this::mod);
            functionMap.put("eql", this::eql);

            for (String rule : inputRules) {
                String[] parts = rule.split(" ");
                final TriFunction<Map<String, AtomicInteger>, String, Supplier<String>, Boolean> fn = functionMap.get(parts[0]);
                if (parts.length == 2) {
                    this.functionsToRun.add(new FunctionToApply(rule, fn, parts[1], this.getNextNumber()));
                } else {
                    this.functionsToRun.add(new FunctionToApply(rule, fn, parts[1], () -> parts[2]));
                }
            }
        }

        public Supplier<String> getNextNumber() {
            return () -> {
                final String x = this.inputValue.substring(this.index, this.index + 1);
                this.index++;
                return x;
            };
        }

        public void runProgramme(final String input) {
            initialise();
            this.inputValue = input;
            this.index = 0;
            for (FunctionToApply fn : functionsToRun) {
                if (fn.rule.startsWith("inp")) {
                    fn.function.apply(this.variables, fn.variableOne, this.getNextNumber());
                } else {
                    fn.function.apply(this.variables, fn.variableOne, fn.variableTwo);
                }
            }
        }

        public boolean isValidProgramme() {
            return this.variables.get("z").get() == 0;
        }

        private void initialise() {
            this.variables.clear();
            this.variables.put("w", new AtomicInteger(0));
            this.variables.put("x", new AtomicInteger(0));
            this.variables.put("y", new AtomicInteger(0));
            this.variables.put("z", new AtomicInteger(0));
        }

        private boolean inp(final Map<String, AtomicInteger> vars, final String parameter, final Supplier<String> valueToAdd) {
            vars.get(parameter).set(Integer.parseInt(valueToAdd.get()));
            return true;
        }

        private boolean add(final Map<String, AtomicInteger> vars, final String varA, final Supplier<String> varB) {
            int toAdd = getValue(vars, varB);
            vars.get(varA).addAndGet(toAdd);
            return true;
        }

        private boolean mul(final Map<String, AtomicInteger> vars, final String varA, final Supplier<String> varB) {
            vars.get(varA).set(vars.get(varA).get() * getValue(vars, varB));
            return true;
        }

        private boolean div(final Map<String, AtomicInteger> vars, final String varA, final Supplier<String> varB) {
            final int numB = getValue(vars, varB);

            if (numB == 0) {
                return false;
            }

            vars.get(varA).set(Math.floorDiv(vars.get(varA).get(), numB));
            return true;
        }

        private boolean mod(final Map<String, AtomicInteger> vars, final String varA, final Supplier<String> varB) {
            final int a = vars.get(varA).get();
            final int b = Integer.parseInt(varB.get());
            if (a < 0 || b <= 0) {
                return false;
            }
            vars.get(varA).set(a % b);
            return true;
        }

        private boolean eql(final Map<String, AtomicInteger> vars, final String varA, final Supplier<String> varB) {
            final int b = getValue(vars, varB);
            vars.get(varA).set(vars.get(varA).get() == b ? 1 : 0);
            return true;
        }

        private int getValue(final Map<String, AtomicInteger> vars, Supplier<String> supplier) {
            final String value = supplier.get();
            final int output;
            if (value.matches(VARIABLE_REGEX)) {
                output = vars.get(value).get();
            } else {
                output = Integer.parseInt(supplier.get());
            }
            return output;
        }
    }

    private static class SimplifiedALU {
        private static final boolean[] Z_DIVIDED_26 = {false, false, false, true, false, true, true, false, false, false, true, true, true, true};
        private static final int[] X_OFFSET = {14, 15, 13, -10, 14, -3, -14, 12, 14, 12, -6, -6, -2, -9};
        private static final int[] Y_OFFSET = {8, 11, 2, 11, 1, 5, 10, 6, 1, 11, 9, 14, 11, 2};

        public boolean runComputer(final String input) {
            int z = 0;
            for (int i = 0; i < input.length(); i++) {
                int w = Integer.parseInt(input.substring(i, i + 1));
                int x = z % 26;
                int y = 25;
                z = Z_DIVIDED_26[i] ? z / 26 : z;

                x = (x + X_OFFSET[i] == w) ? 0 : 1;
                y = (y * x) + 1;
                z *= y;
                y = x * (w + Y_OFFSET[i]);
                z += y;
            }

            return z == 0;
        }
    }
}
