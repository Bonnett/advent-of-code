package uk.co.pete_b.advent.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class Day16 {

    public static String reorderPrograms(final int listSize, final String input, int iterations) {
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] programs = new char[listSize];
        for (int i = 0; i < listSize; i++) {
            programs[i] = alphabet.charAt(i);
        }

        final String[] instructions = input.split(",");

        List<Instruction> instructionList = new ArrayList<>();

        for (String instruction : instructions) {
            if (instruction.charAt(0) == 's') {
                int spinSize = Integer.parseInt(instruction.substring(1));
                instructionList.add(Instruction.spinInstruction(spinSize));
            } else if (instruction.charAt(0) == 'p') {
                final String[] swaps = instruction.substring(1).split("/");
                final char a = swaps[0].charAt(0);
                final char b = swaps[1].charAt(0);
                instructionList.add(Instruction.partnerInstruction(a, b));
            } else if (instruction.charAt(0) == 'x') {
                final String[] swaps = instruction.substring(1).split("/");
                final int a = Integer.parseInt(swaps[0]);
                final int b = Integer.parseInt(swaps[1]);
                instructionList.add(Instruction.exchangeInstruction(a, b));
            }
        }

        // If we hit a cycle no need to continue
        final List<String> seen = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            final String progStr = new String(programs);
            if (seen.contains(progStr)) {
                return seen.get(iterations % i);
            }
            seen.add(progStr);
            for (Instruction instruction : instructionList) {
                programs = instruction.performInstruction(programs);
            }
        }

        return new String(programs);
    }

    private static class Instruction {

        private enum Step {
            SPIN, EXCHANGE, PARTNER
        }

        private final Step step;
        private int spinSize;
        private int swapA;
        private int swapB;
        private char partnerA;
        private char partnerB;

        private Instruction(Step step, int spinSize) {
            this.step = step;
            this.spinSize = spinSize;
        }

        private Instruction(Step step, int swapA, int swapB) {
            this.step = step;
            this.swapA = swapA;
            this.swapB = swapB;
        }

        private Instruction(Step step, char partnerA, char partnerB) {
            this.step = step;
            this.partnerA = partnerA;
            this.partnerB = partnerB;
        }

        static Instruction spinInstruction(int spin) {
            return new Instruction(Step.SPIN, spin);
        }

        static Instruction exchangeInstruction(int a, int b) {
            return new Instruction(Step.EXCHANGE, a, b);
        }

        static Instruction partnerInstruction(char a, char b) {
            return new Instruction(Step.PARTNER, a, b);
        }

        char[] performInstruction(char[] programs) {
            if (this.step == Step.SPIN) {
                final char[] newPrograms = new char[programs.length];

                System.arraycopy(programs, programs.length - spinSize, newPrograms, 0, spinSize);
                System.arraycopy(programs, 0, newPrograms, spinSize, programs.length - spinSize);

                return newPrograms;

            } else if (this.step == Step.EXCHANGE) {
                final char temp = programs[swapA];
                programs[swapA] = programs[swapB];
                programs[swapB] = temp;

                return programs;
            } else {
                int positionA = -1;
                int positionB = -1;
                for (int i = 0; i < programs.length; i++) {
                    if (programs[i] == partnerA) {
                        positionA = i;

                        continue;
                    } else if (programs[i] == partnerB) {
                        positionB = i;
                    }

                    if (positionA != -1 && positionB != -1) {
                        break;
                    }

                }

                programs[positionA] = partnerB;
                programs[positionB] = partnerA;

                return programs;
            }
        }
    }
}
