package uk.co.pete_b.advent.aoc2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public class Day19 {
    private static final Pattern INPUT_PATTERN = Pattern.compile("^([a-z]+) ([0-9]+) ([0-9]+) ([0-9]+)$");

    private static final int INPUT_REGISTER_1 = 0;
    private static final int INPUT_REGISTER_2 = 1;
    private static final int OUTPUT_REGISTER = 2;

    public static int getRegisterZero(final List<String> input, final int registerZeroStart) {
        final int startIp = Integer.valueOf(input.get(0).substring(4));
        final List<String> operations = new ArrayList<>(input.subList(1, input.size()));
        final Computer computer = new Computer(startIp);
        computer.registers[0] = registerZeroStart;

        while (registerZeroStart != 1 || computer.instructionPointer != 1) {
            if (computer.instructionPointer >= operations.size()) {
                break;
            }

            final String execution = operations.get(computer.instructionPointer);
            final Matcher matcher = INPUT_PATTERN.matcher(execution);

            if (matcher.matches()) {
                final int[] inputs = new int[3];
                final String operation = matcher.group(1);
                for (int i = 1; i < matcher.groupCount(); i++) {
                    inputs[i - 1] = Integer.parseInt(matcher.group(i + 1));
                }

                if (!computer.execute(operation, inputs)) {
                    break;
                }
            }
        }

        int sumOfDivisors = 0;
        for (int i = 1; i <= computer.registers[2]; i++) {
            if (computer.registers[2] % i == 0) {
                sumOfDivisors += i;
            }
        }

        return (registerZeroStart == 0) ? computer.registers[0] : sumOfDivisors;
    }

    public static class Computer {

        private int ipBinding;
        private int instructionPointer;
        private int[] registers;

        private final Map<String, Consumer<int[]>> functionMap = new HashMap<>();

        public Computer(final int ipBinding) {
            this.ipBinding = ipBinding;
            this.instructionPointer = 0;
            this.registers = new int[6];
            this.functionMap.put("addr", this::addr);
            this.functionMap.put("addi", this::addi);
            this.functionMap.put("mulr", this::mulr);
            this.functionMap.put("muli", this::muli);
            this.functionMap.put("setr", this::setr);
            this.functionMap.put("seti", this::seti);
            this.functionMap.put("gtir", this::gtir);
            this.functionMap.put("gtri", this::gtri);
            this.functionMap.put("gtrr", this::gtrr);
            this.functionMap.put("eqir", this::eqir);
            this.functionMap.put("eqri", this::eqri);
            this.functionMap.put("eqrr", this::eqrr);
        }

        public boolean execute(final String function, final int[] inputs) {
            this.registers[this.ipBinding] = this.instructionPointer;
            this.functionMap.get(function).accept(inputs);
            this.instructionPointer = this.registers[this.ipBinding];
            this.instructionPointer++;

            return true;
        }

        private void addr(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] + this.registers[inputs[INPUT_REGISTER_2]];
        }

        private void addi(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] + inputs[INPUT_REGISTER_2];
        }

        private void mulr(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] * this.registers[inputs[INPUT_REGISTER_2]];
        }

        private void muli(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] * inputs[INPUT_REGISTER_2];
        }

        private void setr(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]];
        }

        private void seti(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1];
        }

        private void gtir(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1] > this.registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
        }

        private void gtri(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] > inputs[INPUT_REGISTER_2] ? 1 : 0;
        }

        private void gtrr(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] > this.registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
        }

        private void eqir(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = inputs[INPUT_REGISTER_1] == this.registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
        }

        private void eqri(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] == inputs[INPUT_REGISTER_2] ? 1 : 0;
        }

        private void eqrr(final int[] inputs) {
            this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] == this.registers[inputs[INPUT_REGISTER_2]] ? 1 : 0;
        }
    }
}
