package uk.co.pete_b.advent.aoc2018;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Computer {

    private static final int INPUT_REGISTER_1 = 0;
    private static final int INPUT_REGISTER_2 = 1;
    private static final int OUTPUT_REGISTER = 2;

    private int ipBinding;
    private int instructionPointer;
    private int[] registers;
    private final boolean debug;
    private BufferedWriter writer;

    private final Map<String, Consumer<int[]>> functionMap = new HashMap<>();

    public Computer(final int ipBinding, int registerZeroValue, boolean debug) {
        this.debug = debug;
        if (debug) {
            try {
                writer = new BufferedWriter(new FileWriter(new File("ComputerDebug.log")));
            } catch (IOException e) {
            }
        }
        this.ipBinding = ipBinding;
        this.instructionPointer = 0;
        this.registers = new int[6];
        this.registers[0] = registerZeroValue;
        this.functionMap.put("addr", this::addr);
        this.functionMap.put("addi", this::addi);
        this.functionMap.put("mulr", this::mulr);
        this.functionMap.put("muli", this::muli);
        this.functionMap.put("banr", this::banr);
        this.functionMap.put("bani", this::bani);
        this.functionMap.put("borr", this::borr);
        this.functionMap.put("bori", this::bori);
        this.functionMap.put("setr", this::setr);
        this.functionMap.put("seti", this::seti);
        this.functionMap.put("gtir", this::gtir);
        this.functionMap.put("gtri", this::gtri);
        this.functionMap.put("gtrr", this::gtrr);
        this.functionMap.put("eqir", this::eqir);
        this.functionMap.put("eqri", this::eqri);
        this.functionMap.put("eqrr", this::eqrr);

    }

    public int getInstructionPointer() {
        return this.instructionPointer;
    }

    public int[] getRegisters() {
        return this.registers;
    }

    public void execute(final Computer.Operation operation) {
        this.registers[this.ipBinding] = this.instructionPointer;

        final StringBuilder sb = new StringBuilder();
        if (this.debug) {
            sb.append("ip=").append(this.instructionPointer).append(" ").append(Arrays.toString(this.registers)).append(" ").append(operation.function).append(" ");
            final String inputStr = Arrays.toString(operation.inputs);
            sb.append(inputStr.substring(1, inputStr.length() - 1).replaceAll(",", "")).append(" ");
        }

        this.functionMap.get(operation.function).accept(operation.inputs);
        this.instructionPointer = this.registers[this.ipBinding];
        this.instructionPointer++;

        if (this.debug) {
            try {
                writer.write(sb.toString() + Arrays.toString(this.registers) + "\r\n");
            } catch (IOException e) {

            }
        }
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

    public void banr(final int[] inputs) {
        this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] & this.registers[inputs[INPUT_REGISTER_2]];
    }

    public void bani(final int[] inputs) {
        this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] & inputs[INPUT_REGISTER_2];
    }

    public void borr(final int[] inputs) {
        this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] | this.registers[inputs[INPUT_REGISTER_2]];
    }

    public void bori(final int[] inputs) {
        this.registers[inputs[OUTPUT_REGISTER]] = this.registers[inputs[INPUT_REGISTER_1]] | inputs[INPUT_REGISTER_2];
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

    public static class Operation {
        private final String function;
        private final int[] inputs;

        public Operation(final String function, final int[] inputs) {
            this.function = function;
            this.inputs = inputs;
        }
    }
}
