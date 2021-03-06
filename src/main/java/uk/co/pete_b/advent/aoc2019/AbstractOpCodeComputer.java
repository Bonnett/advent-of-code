package uk.co.pete_b.advent.aoc2019;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOpCodeComputer implements Runnable {
    public AbstractOpCodeComputer(final List<Long> operations) {
        this.state = new ArrayList<>(operations);
    }

    private final List<Long> state;
    private int currentPos = 0;
    private long relativeBase = 0;
    private boolean keepRunning = true;

    @Override
    public void run() {
        try {
            final DecimalFormat format = new DecimalFormat("00000");
            while (this.keepRunning && this.currentPos < this.state.size() - 1) {
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
                        safeSet(getAddress(instruction, 2, 1), getInputValue());
                        this.currentPos += 2;
                        break;
                    }
                    case "04": {
                        setOutputValue(getValue(instruction, 2, 1));
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
                        this.keepRunning = false;
                        break;
                    default:
                        throw new IllegalStateException("Something's wrong");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void forceQuit() {
        this.keepRunning = false;
    }

    public abstract Long getInputValue() throws Exception;

    public abstract void setOutputValue(final Long value) throws Exception;
}
