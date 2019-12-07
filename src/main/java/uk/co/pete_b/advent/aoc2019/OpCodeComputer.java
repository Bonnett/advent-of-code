package uk.co.pete_b.advent.aoc2019;

import java.text.DecimalFormat;
import java.util.concurrent.BlockingQueue;

public class OpCodeComputer implements Runnable {

    private final int[] state;
    private int currentPos = 0;
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;

    public OpCodeComputer(final int[] operations, final BlockingQueue<Integer> inputQueue, final BlockingQueue<Integer> outputQueue)
    {
        this.state = operations.clone();
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public void run() {
        try {
            final DecimalFormat FORMAT = new DecimalFormat("0000");
            boolean shouldQuit = false;
            while (!shouldQuit && this.currentPos < this.state.length - 1) {
                final String instruction = FORMAT.format(this.state[this.currentPos]);
                final String opCode = instruction.substring(2);
                switch (opCode) {
                    case "01":
                    case "02": {
                        final int valueA = instruction.charAt(1) == '0' ? this.state[this.state[this.currentPos + 1]] : this.state[this.currentPos + 1];
                        final int valueB = instruction.charAt(0) == '0' ? this.state[this.state[this.currentPos + 2]] : this.state[this.currentPos + 2];
                        final int target = this.state[this.currentPos + 3];
                        if (opCode.equalsIgnoreCase("01")) {
                            this.state[target] = valueA + valueB;
                        } else {
                            this.state[target] = valueA * valueB;
                        }
                        this.currentPos += 4;
                        break;
                    }
                    case "03": {
                        final int valueA = this.state[this.currentPos + 1];
                        this.state[valueA] = this.inputQueue.take();
                        this.currentPos += 2;
                        break;
                    }
                    case "04": {
                        this.outputQueue.put(instruction.charAt(1) == '0' ? this.state[this.state[this.currentPos + 1]] : this.state[this.currentPos + 1]);
                        this.currentPos += 2;
                        break;
                    }
                    case "05":
                    case "06": {
                        final int valueA = instruction.charAt(1) == '0' ? this.state[this.state[this.currentPos + 1]] : this.state[this.currentPos + 1];
                        final int valueB = instruction.charAt(0) == '0' ? this.state[this.state[this.currentPos + 2]] : this.state[this.currentPos + 2];
                        if ((opCode.equalsIgnoreCase("05")) == (valueA != 0)) {
                            this.currentPos = valueB;
                        } else {
                            this.currentPos += 3;
                        }
                        break;
                    }
                    case "07":
                    case "08": {
                        final int valueA = instruction.charAt(1) == '0' ? this.state[this.state[this.currentPos + 1]] : this.state[this.currentPos + 1];
                        final int valueB = instruction.charAt(0) == '0' ? this.state[this.state[this.currentPos + 2]] : this.state[this.currentPos + 2];
                        final int target = this.state[this.currentPos + 3];
                        final boolean shouldOperate = (opCode.equalsIgnoreCase("07")) ? valueA < valueB : valueA == valueB;
                        if (shouldOperate) {
                            this.state[target] = 1;
                        } else {
                            this.state[target] = 0;
                        }
                        this.currentPos += 4;
                        break;
                    }
                    case "99":
                        shouldQuit = true;
                        break;
                    default:
                        throw new IllegalStateException("Something's wrong");
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
