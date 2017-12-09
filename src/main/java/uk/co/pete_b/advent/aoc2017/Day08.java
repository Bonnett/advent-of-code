package uk.co.pete_b.advent.aoc2017;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 {
    public static class Answers {
        private final int largestRegister;
        private final int highestRegister;

        Answers(int largestRegister, int highestRegister) {
            this.largestRegister = largestRegister;
            this.highestRegister = highestRegister;
        }

        public int getLargestRegister() {
            return largestRegister;
        }

        public int getHighestRegister() {
            return highestRegister;
        }
    }

    private static final Pattern INSTRUCTION = Pattern.compile("^([a-z]+) (inc|dec) (-?[0-9]+) if ([a-z]+) ([!=<>]+) (-?[0-9]+)$");

    public static Answers getAnswers(final String input) {
        Map<String, Integer> registers = new HashMap<>();
        String[] instructions = input.split("\r?\n");

        int highestValue = 0;

        for (String instruction : instructions) {

            Matcher match = INSTRUCTION.matcher(instruction);
            if (match.find()) {
                final String register = match.group(1);
                final String operation = match.group(2);
                final int value = Integer.parseInt(match.group(3));
                final String ifRegister = match.group(4);
                final String comparator = match.group(5);
                final int ifValue = Integer.parseInt(match.group(6));

                if (!registers.containsKey(register)) {
                    registers.put(register, 0);
                }
                if (!registers.containsKey(ifRegister)) {
                    registers.put(ifRegister, 0);
                }

                int newValue = registers.get(register);
                int comparatorValue = registers.get(ifRegister);
                final int multiplier = ("inc".equals(operation)) ? 1 : -1;

                if (">".equals(comparator)) {
                    if (comparatorValue > ifValue) {
                        newValue += value * multiplier;
                    }

                } else if (">=".equals(comparator)) {
                    if (comparatorValue >= ifValue) {
                        newValue += value * multiplier;
                    }

                } else if ("==".equals(comparator)) {
                    if (comparatorValue == ifValue) {
                        newValue += value * multiplier;
                    }

                } else if ("<=".equals(comparator)) {
                    if (comparatorValue <= ifValue) {
                        newValue += value * multiplier;
                    }

                } else if ("<".equals(comparator)) {
                    if (comparatorValue < ifValue) {
                        newValue += value * multiplier;
                    }

                } else if ("!=".equals(comparator)) {
                    if (comparatorValue != ifValue) {
                        newValue += value * multiplier;
                    }

                }

                if (newValue > highestValue) {
                    highestValue = newValue;
                }

                registers.put(register, newValue);
            }
        }

        return new Answers(registers.values().stream().max(Integer::compareTo).get(), highestValue);
    }
}
