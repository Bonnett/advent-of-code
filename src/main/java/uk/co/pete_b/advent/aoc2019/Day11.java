package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Day11 {
    public static Map<Coordinate, Long> startPainting(final Long startingColour, final List<Long> operations) throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(2);
        final BlockingQueue<Long> inputQueue = new ArrayBlockingQueue<>(20);
        final BlockingQueue<Long> outputQueue = new ArrayBlockingQueue<>(20);

        final OpCodeComputer computer = new OpCodeComputer(operations, inputQueue, outputQueue);
        final PaintingRobot robot = new PaintingRobot(computer);
        executor.execute(computer);
        executor.execute(robot);
        inputQueue.put(startingColour);
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        return robot.getPaintedSquares();
    }

    private static class PaintingRobot implements Runnable {
        private final OpCodeComputer computer;
        private final BlockingQueue<Long> inputQueue;
        private final BlockingQueue<Long> outputQueue;

        private Coordinate currentPosition = new Coordinate(0, 0);
        private Direction currentDirection = Direction.UP;
        private Map<Coordinate, Long> paintedSquares = new HashMap<>();

        PaintingRobot(final OpCodeComputer computer) {
            this.computer = computer;
            this.inputQueue = computer.getOutputQueue();
            this.outputQueue = computer.getInputQueue();
        }

        public void run() {
            try {
                while (!this.computer.hasFinishedExecution()) {
                    final Long colour = this.inputQueue.take();
                    final Long direction = this.inputQueue.take();
                    this.paintedSquares.put(this.currentPosition, colour);
                    this.currentDirection = (direction == 0L) ? this.currentDirection.getLeft() : this.currentDirection.getRight();
                    switch (this.currentDirection) {
                        case LEFT:
                            this.currentPosition = this.currentPosition.left();
                            break;
                        case UP:
                            this.currentPosition = this.currentPosition.up();
                            break;
                        case RIGHT:
                            this.currentPosition = this.currentPosition.right();
                            break;
                        case DOWN:
                            this.currentPosition = this.currentPosition.down();
                            break;
                    }

                    this.outputQueue.put(this.paintedSquares.getOrDefault(this.currentPosition, 0L));
                }
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public Map<Coordinate, Long> getPaintedSquares() {
            return this.paintedSquares;
        }
    }
}
