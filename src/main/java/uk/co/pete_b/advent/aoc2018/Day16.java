package uk.co.pete_b.advent.aoc2018;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class Day16 {
    private static final Pattern ARRAY_PATTERN = Pattern.compile("^([0-9]+),? ([0-9]+),? ([0-9]+),? ([0-9]+)]?$");

    private static final int OPCODE = 0;
    private static final int INPUT_REGISTER_1 = 1;
    private static final int INPUT_REGISTER_2 = 2;
    private static final int OUTPUT_REGISTER = 3;

    public static int getSolution(final List<String> input, final boolean findMatchingThree) {
        final Map<Integer, BiFunction<int[], int[], int[]>> functionMap = new HashMap<>();
        final List<BiFunction<int[], int[], int[]>> functionList = Arrays.asList(Computer::addr, Computer::addi, Computer::mulr, Computer::muli,
                Computer::banr, Computer::bani, Computer::borr, Computer::bori, Computer::setr, Computer::seti, Computer::gtir, Computer::gtri,
                Computer::gtrr, Computer::eqir, Computer::eqri, Computer::eqrr);

        int totalMatchingThree = 0;
        final Set<BiFunction<int[], int[], int[]>> foundFunctions = new HashSet<>();

        while (functionMap.size() < functionList.size()) {
            final Iterator<String> it = input.iterator();
            while (it.hasNext()) {
                String line = it.next();
                final int[] inputState = new int[4];
                final int[] inputs = new int[4];
                final int[] expectedOutputState = new int[4];
                if (line.startsWith("Before")) {
                    Matcher matcher = ARRAY_PATTERN.matcher(line.substring("Before: [".length()));

                    if (matcher.matches()) {
                        for (int i = 0; i < matcher.groupCount(); i++) {
                            inputState[i] = Integer.parseInt(matcher.group(i + 1));
                        }
                    }

                    line = it.next();
                    matcher = ARRAY_PATTERN.matcher(line);

                    if (matcher.matches()) {
                        for (int i = 0; i < matcher.groupCount(); i++) {
                            inputs[i] = Integer.parseInt(matcher.group(i + 1));
                        }
                    }

                    line = it.next();
                    matcher = ARRAY_PATTERN.matcher(line.substring("After:  [".length()));

                    if (matcher.matches()) {
                        for (int i = 0; i < matcher.groupCount(); i++) {
                            expectedOutputState[i] = Integer.parseInt(matcher.group(i + 1));
                        }
                    }

                    int matchingCount = 0;
                    BiFunction<int[], int[], int[]> lastMatchingFn = null;
                    final BiFunction<int[], int[], int[]> functionToApply = functionMap.get(inputs[OPCODE]);
                    if (findMatchingThree || functionToApply == null) {
                        for (BiFunction<int[], int[], int[]> function : functionList) {
                            if (findMatchingThree || !foundFunctions.contains(function)) {
                                int[] newRegisters = function.apply(inputState, inputs);
                                if (arrayEquals(expectedOutputState, newRegisters)) {
                                    matchingCount++;
                                    lastMatchingFn = function;
                                }
                            }
                        }
                    }

                    if (matchingCount >= 3) {
                        totalMatchingThree++;
                    } else if (matchingCount == 1) {
                        functionMap.put(inputs[OPCODE], lastMatchingFn);
                        foundFunctions.add(lastMatchingFn);
                    }
                }
            }

            if (findMatchingThree) {
                break;
            }
        }

        int[] registers = new int[4];
        if (!findMatchingThree) {
            final Iterator<String> it = input.iterator();
            // Skip to start of opcode list
            while (it.hasNext()) {
                String line = it.next();
                if (line.startsWith("Before")) {
                    it.next();
                    it.next();
                    it.next();
                } else {
                    it.next();
                    // We have our program!
                    break;
                }
            }

            // Read the program
            while (it.hasNext()) {
                final String line = it.next();
                final int[] inputs = new int[4];
                final Matcher matcher = ARRAY_PATTERN.matcher(line);

                if (matcher.matches()) {
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        inputs[i] = Integer.parseInt(matcher.group(i + 1));
                    }
                }

                registers = functionMap.get(inputs[OPCODE]).apply(registers, inputs);
            }
        }

        return findMatchingThree ? totalMatchingThree : registers[0];
    }

    private static boolean arrayEquals(final int[] expectedOutput, final int[] actualOutput) {
        boolean matching = true;
        if (expectedOutput.length != actualOutput.length) {
            matching = false;
        }

        for (int i = 0; i < expectedOutput.length && matching; i++) {
            matching = expectedOutput[i] == actualOutput[i];
        }

        return matching;
    }

    public static class Computer {
        public static int[] addr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] + registers[inputs[INPUT_REGISTER_2]];
            return outputs;
        }

        public static int[] addi(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] + inputs[INPUT_REGISTER_2];
            return outputs;
        }

        public static int[] mulr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] * registers[inputs[INPUT_REGISTER_2]];
            return outputs;
        }

        public static int[] muli(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] * inputs[INPUT_REGISTER_2];
            return outputs;
        }

        public static int[] banr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] & registers[inputs[INPUT_REGISTER_2]];
            return outputs;
        }

        public static int[] bani(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] & inputs[INPUT_REGISTER_2];
            return outputs;
        }

        public static int[] borr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] | registers[inputs[INPUT_REGISTER_2]];
            return outputs;
        }

        public static int[] bori(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] | inputs[INPUT_REGISTER_2];
            return outputs;
        }

        public static int[] setr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]];
            return outputs;
        }

        public static int[] seti(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1];
            return outputs;
        }

        public static int[] gtir(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1] > registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
            return outputs;
        }

        public static int[] gtri(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] > inputs[INPUT_REGISTER_2] ? 1 : 0;
            return outputs;
        }

        public static int[] gtrr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] > registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
            return outputs;
        }

        public static int[] eqir(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1] == registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
            return outputs;
        }

        public static int[] eqri(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] == inputs[INPUT_REGISTER_2] ? 1 : 0;
            return outputs;
        }

        public static int[] eqrr(final int[] registers, final int[] inputs) {
            final int[] outputs = Arrays.copyOf(registers, 4);
            outputs[inputs[OUTPUT_REGISTER]] = registers[inputs[INPUT_REGISTER_1]] == registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
            return outputs;
        }
    }
}
