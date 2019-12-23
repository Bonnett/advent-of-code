package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.concurrent.*;

public class Day23 {
    public static Answer getPacketSentTo255(final List<Long> operations) throws Exception {
        final List<BlockingOpCodeComputer> computers = new ArrayList<>();
        final NatController natController = new NatController(computers);
        final Switch networkSwitch = new Switch(computers, natController);
        natController.setSwitch(networkSwitch);

        final ExecutorService executor = Executors.newFixedThreadPool(52);
        for (int i = 0; i < 50; i++) {
            computers.add(new BlockingOpCodeComputer(operations, new ArrayBlockingQueue<>(10000), new ArrayBlockingQueue<>(10000)));
            computers.get(i).getInput().add((long) i);
        }

        executor.submit(networkSwitch);
        for (int i = 0; i < 50; i++) {
            executor.submit(computers.get(i));
        }
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        return natController.getAnswer();
    }

    private static class NatController {
        private final List<BlockingOpCodeComputer> computers;
        private Long lastSentY;
        private Long yValue;
        private boolean donePartOne = false;
        private Switch networkSwitch;

        public NatController(final List<BlockingOpCodeComputer> computers) {
            this.computers = computers;
        }

        public void setSwitch(final Switch networkSwitch) {
            this.networkSwitch = networkSwitch;
        }

        public void receivePacket(final Long xValue, final Long yValue) {
            synchronized (this) {
                if (!this.donePartOne) {
                    this.yValue = yValue;
                    this.donePartOne = true;
                }

                boolean allIdle = true;
                for (int i=0; i<this.computers.size() && allIdle; i++) {
                    allIdle = this.computers.get(i).getInput().isEmpty();
                }

                if (allIdle) {

                    if (yValue.equals(this.lastSentY)) {
                        System.out.println("DUPLICATE: " + this.lastSentY);
                        for (final BlockingOpCodeComputer computer : this.computers) {
                            computer.forceQuit();
                        }
                        this.networkSwitch.forceQuit();
                    }
                    else {
                        this.lastSentY = yValue;
                        this.computers.get(0).getInput().addAll(Arrays.asList(xValue, yValue));
                    }
                }
            }
        }

        public Answer getAnswer() {
            return new Answer(this.yValue.intValue(), this.lastSentY.intValue());
        }
    }

    public static class Switch implements Runnable {
        private final List<BlockingOpCodeComputer> computers;
        private final NatController natController;
        private boolean keepRunning = true;

        public Switch(final List<BlockingOpCodeComputer> computers, final NatController natController) {
            this.computers = computers;
            this.natController = natController;
        }

        @Override
        public void run() {
            while (this.keepRunning) {
                for (final BlockingOpCodeComputer computer : this.computers) {
                    Long destination = computer.getOutput().poll();
                    if (destination != null) {
                        try {
                            Long x = computer.getOutput().poll(500, TimeUnit.MILLISECONDS);
                            Long y = computer.getOutput().poll(500, TimeUnit.MILLISECONDS);
                            if (destination == 255) {
                                this.natController.receivePacket(Objects.requireNonNull(x), Objects.requireNonNull(y));
                            } else {
                                this.computers.get(destination.intValue()).getInput().put(Objects.requireNonNull(x));
                                this.computers.get(destination.intValue()).getInput().put(Objects.requireNonNull(y));
                            }
                        } catch (Exception e) {
                            System.err.println("Error while processing data. Caused by " + e.getMessage());
                            return;
                        }
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void forceQuit() {
            this.keepRunning = false;
        }
    }

    public static class Answer {
        private final int yValueForAddress255;
        private final int firstDuplicateYValue;

        public Answer(final int yValueForAddress255, final int firstDuplicateYValue) {
            this.yValueForAddress255 = yValueForAddress255;
            this.firstDuplicateYValue = firstDuplicateYValue;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).yValueForAddress255 == this.yValueForAddress255 && ((Answer) otherAnswer).firstDuplicateYValue == this.firstDuplicateYValue;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Y Value for Address 255: %d, First Duplicate Y value sent to 0: %d", yValueForAddress255, firstDuplicateYValue);
        }
    }
}
