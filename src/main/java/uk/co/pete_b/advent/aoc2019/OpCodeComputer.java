package uk.co.pete_b.advent.aoc2019;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class OpCodeComputer implements Runnable {
    private final List<Long> state;
    private int currentPos = 0;
    private long relativeBase = 0;
    private final Supplier<Long> input;
    private final Consumer<Long> output;

    public OpCodeComputer(final List<Long> operations, final Supplier<Long> input, final Consumer<Long> output) {
        this.state = new ArrayList<>(operations);
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            final DecimalFormat format = new DecimalFormat("00000");
            boolean keepRunning = true;
            while (keepRunning && this.currentPos < this.state.size() - 1) {
                final String instruction = format.format(safeGet(this.currentPos));
                final String opCode = instruction.substring(3);
                switch (opCode) {
                    case "01":
                    case "02": {
                        final long valueA = getValue(instruction, 2, 1);
                        final long valueB = getValue(instruction, 1, 2);
                        final long target = getAddress(instruction, 0, 3);
                        if (opCode.equalsIgnoreCase("01")) {
                            safeSet(target, valueA + valueB);
                        } else {
                            safeSet(target, valueA * valueB);
                        }
                        this.currentPos += 4;
                        break;
                    }
                    case "03": {
                        safeSet(getAddress(instruction, 2, 1), this.input.get());
                        this.currentPos += 2;
                        break;
                    }
                    case "04": {
                        this.output.accept(getValue(instruction, 2, 1));
                        this.currentPos += 2;
                        break;
                    }
                    case "05":
                    case "06": {
                        final long valueA = getValue(instruction, 2, 1);
                        final long valueB = getValue(instruction, 1, 2);
                        if ((opCode.equalsIgnoreCase("05")) == (valueA != 0)) {
                            this.currentPos = (int) valueB;
                        } else {
                            this.currentPos += 3;
                        }
                        break;
                    }
                    case "07":
                    case "08": {
                        final long valueA = getValue(instruction, 2, 1);
                        final long valueB = getValue(instruction, 1, 2);
                        final long target = getAddress(instruction, 0, 3);
                        final boolean shouldOperate = (opCode.equalsIgnoreCase("07")) ? valueA < valueB : valueA == valueB;
                        if (shouldOperate) {
                            safeSet(target, 1L);
                        } else {
                            safeSet(target, 0L);
                        }
                        this.currentPos += 4;
                        break;
                    }
                    case "09": {
                        this.relativeBase += getValue(instruction, 2, 1);
                        this.currentPos += 2;
                        break;
                    }
                    case "99":
                        keepRunning = false;
                        break;
                    default:
                        throw new IllegalStateException("Something's wrong");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private long getValue(final String instruction, final int charPos, final int stateOffset) {
        switch (instruction.charAt(charPos)) {
            case '0':
                return safeGet((int) safeGet(this.currentPos + stateOffset));
            case '1':
                return safeGet(this.currentPos + stateOffset);
            case '2':
                return safeGet((int) this.relativeBase + (int) safeGet(this.currentPos + stateOffset));
            default:
                throw new IllegalStateException("Something's wrong");
        }
    }

    private int getAddress(final String instruction, final int charPos, final int stateOffset) {
        switch (instruction.charAt(charPos)) {
            case '0':
                return (int) safeGet(this.currentPos + stateOffset);
            case '2':
                return (int) (this.relativeBase + safeGet(this.currentPos + stateOffset));
            default:
                throw new IllegalStateException("Something's wrong");
        }
    }

    private long safeGet(final long index) {
        prepopulate(index);
        return this.state.get((int) index);
    }

    private void safeSet(final long index, final long value) {
        prepopulate(index);
        this.state.set((int) index, value);
    }

    private void prepopulate(final long index) {
        if (index >= this.state.size()) {
            while (index >= this.state.size()) {
                this.state.add(0L);
            }
        }
    }
}
