package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Day11 {
    public static Map<Coordinate, Long> startPainting(final Long startingColour, final List<Long> operations) {
        final PaintingRobot robot = new PaintingRobot(startingColour);
        final OpCodeComputer computer = new OpCodeComputer(operations, robot::changeDirection, robot::updatePosition);
        final Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(computer);

        return robot.getPaintedSquares();
    }

    private static class PaintingRobot {
        private final Queue<Long> inputBuffer = new ArrayDeque<>();
        private final Queue<Long> outputBuffer = new ArrayDeque<>();
        private final Map<Coordinate, Long> paintedSquares = new HashMap<>();

        private Coordinate currentPosition = new Coordinate(0, 0);
        private Direction currentDirection = Direction.UP;

        public PaintingRobot(final Long startingColour) {
            this.outputBuffer.add(startingColour);
        }

        public void updatePosition(final Long input) {
            this.inputBuffer.add(input);
            if (this.inputBuffer.size() == 2) {
                final Long colour = Objects.requireNonNull(this.inputBuffer.poll());
                final Long direction = Objects.requireNonNull(this.inputBuffer.poll());
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
            }
        }

        public Map<Coordinate, Long> getPaintedSquares() {
            return this.paintedSquares;
        }

        public Long changeDirection() {
            if (!outputBuffer.isEmpty()) {
                return outputBuffer.poll();
            } else {
                return this.paintedSquares.getOrDefault(this.currentPosition, 0L);
            }
        }
    }
}
