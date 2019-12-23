package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Day15 {

    private static final char BLOCK = '#';
    private static final char DROID = 'O';
    private static final char OXYGEN = 'X';
    private static final char SPACE = ' ';

    public static Answer calculateDistanceToOxygen(final List<Long> operations) throws InterruptedException {
        final RepairDroid droid = new RepairDroid(operations);
        droid.run();

        final Map<Coordinate, Character> maze = droid.getMaze();

        final Coordinate startingPosition = maze.entrySet().stream().filter(square -> square.getValue() == OXYGEN).findFirst().orElseThrow().getKey();
        long totalSpaces = maze.entrySet().stream().filter(square -> square.getValue() == SPACE).count();

        List<Coordinate> oxygenatingPoints = new ArrayList<>(Collections.singletonList(startingPosition));

        int iterations = 0;
        while (totalSpaces != 0) {
            iterations++;
            final List<Coordinate> newPoints = new ArrayList<>();
            for (final Coordinate point : oxygenatingPoints) {
                final Coordinate up = point.up();
                final Coordinate down = point.down();
                final Coordinate left = point.left();
                final Coordinate right = point.right();
                if (maze.getOrDefault(up, BLOCK) == SPACE) {
                    newPoints.add(up);
                    maze.put(up, OXYGEN);
                    totalSpaces--;
                }
                if (maze.getOrDefault(down, BLOCK) == SPACE) {
                    newPoints.add(down);
                    maze.put(down, OXYGEN);
                    totalSpaces--;
                }
                if (maze.getOrDefault(left, BLOCK) == SPACE) {
                    newPoints.add(left);
                    maze.put(left, OXYGEN);
                    totalSpaces--;
                }
                if (maze.getOrDefault(right, BLOCK) == SPACE) {
                    newPoints.add(right);
                    maze.put(right, OXYGEN);
                    totalSpaces--;
                }
            }

            oxygenatingPoints = newPoints;
        }

        return new Answer(droid.getDistance(), iterations);
    }

    private static class RepairDroid {
        private static final int NORTH = 1;
        private static final int SOUTH = 2;
        private static final int WEST = 3;
        private static final int EAST = 4;
        private static final Map<Integer, int[]> OPTIONS = new HashMap<>();

        static {
            OPTIONS.put(NORTH, new int[]{NORTH, EAST, WEST});
            OPTIONS.put(SOUTH, new int[]{SOUTH, WEST, EAST});
            OPTIONS.put(EAST, new int[]{EAST, SOUTH, NORTH});
            OPTIONS.put(WEST, new int[]{WEST, NORTH, SOUTH});
        }

        private final Map<Coordinate, Character> maze = new HashMap<>();
        private final List<Long> operations;
        private Stack<List<Long>> possibleRoutes = new Stack<>();
        private List<Long> bestRoute;
        private List<Long> currentRoute;
        private boolean shouldExit = false;
        private int currentIndex = 0;
        private Coordinate currentPosition = new Coordinate(0, 0);

        public RepairDroid(final List<Long> operations) {
            this.operations = operations;
            this.maze.put(this.currentPosition, DROID);
        }

        public void run() throws InterruptedException {
            // Populate the starting routes going in all directions
            for (long i = 1; i < 5; i++) {
                final List<Long> route = new ArrayList<>();
                route.add(i);
                this.possibleRoutes.add(route);
            }

            // Iterate through the list of routes until we've exhausted them all
            while (!this.possibleRoutes.isEmpty()) {
                this.currentPosition = new Coordinate(0, 0);
                this.currentRoute = this.possibleRoutes.pop();
                this.shouldExit = false;
                this.currentIndex = 0;
                final ExecutorService executor = Executors.newSingleThreadExecutor();
                final ConsumerSupplierOpCodeComputer computer = new ConsumerSupplierOpCodeComputer(this.operations, this::moveRobot, this::reportMoveStatus);
                executor.execute(computer);
                executor.shutdown();
                executor.awaitTermination(2L, TimeUnit.SECONDS);
            }
        }

        private Long moveRobot() {
            if (!this.shouldExit) {
                final Long nextMovement = this.currentRoute.get(this.currentIndex);
                switch (nextMovement.intValue()) {
                    case NORTH:
                        this.currentPosition = this.currentPosition.up();
                        break;
                    case EAST:
                        this.currentPosition = this.currentPosition.left();
                        break;
                    case SOUTH:
                        this.currentPosition = this.currentPosition.down();
                        break;
                    case WEST:
                        this.currentPosition = this.currentPosition.right();
                        break;
                }
                return nextMovement;
            } else {
                return -1L;
            }
        }

        private void reportMoveStatus(final Long status) {
            this.currentIndex++;
            if (status == 2L) {
                this.maze.put(this.currentPosition, OXYGEN);
                if (this.bestRoute == null || this.bestRoute.size() > this.currentRoute.size()) {
                    this.bestRoute = this.currentRoute;
                }
                this.shouldExit = true;
            } else if (status == 0L) {
                this.shouldExit = true;
                this.maze.put(this.currentPosition, BLOCK);
            } else if (status == 1L) {
                // If we've got a successful move and this is the last move we need to do then we add all the options from the last move to create 3 new possible routes
                if (this.currentIndex == this.currentRoute.size()) {
                    this.maze.put(this.currentPosition, SPACE);
                    this.shouldExit = true;
                    final int[] options = OPTIONS.get(this.currentRoute.get(this.currentIndex - 1).intValue());
                    for (final long option : options) {
                        final List<Long> newOption = new ArrayList<>(this.currentRoute);
                        newOption.add(option);
                        this.possibleRoutes.add(newOption);
                    }
                }
            }
        }

        public int getDistance() {
            return this.bestRoute.size();
        }

        public Map<Coordinate, Character> getMaze() {
            return this.maze;
        }
    }

    public static class Answer {
        private final int fewestMovements;
        private final int iterationsToFillSystem;

        public Answer(final int fewestMovements, final int iterationsToFillSystem) {
            this.fewestMovements = fewestMovements;
            this.iterationsToFillSystem = iterationsToFillSystem;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).fewestMovements == this.fewestMovements && ((Answer) otherAnswer).iterationsToFillSystem == this.iterationsToFillSystem;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Fewest Movements: %d, Iterations To fill: %d", this.fewestMovements, this.iterationsToFillSystem);
        }
    }
}
