package uk.co.pete_b.advent.aoc2019;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Day17 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    private static final char ROBOT = '^';
    private static final char SPACE = '.';
    private static final char SCAFFOLD = '#';

    private static final char COMMA = ',';
    private static final long NEW_LINE = '\n';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';

    public static int calculateAlignmentParameter(final List<Long> operations) throws InterruptedException {
        final AsciiRobot robot = new AsciiRobot();
        final OpCodeComputer computer = new OpCodeComputer(operations, robot::moveRobot, robot::drawScreen);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(computer);
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        return calculateAlignmentParameter(robot.getView());
    }

    public static String calculateRoute(final String viewString) {
        final ViewDetails viewDetails = parseView(viewString);
        final Map<Coordinate, Character> view = viewDetails.getView();

        Coordinate currentPosition = view.entrySet().stream().filter(square -> square.getValue() == ROBOT).findFirst().orElseThrow().getKey();

        final StringBuilder route = new StringBuilder();

        Direction currentHeading;
        if (view.getOrDefault(currentPosition.left(), SPACE) == SCAFFOLD) {
            route.append(LEFT);
            currentHeading = Direction.LEFT;
        } else {
            route.append(RIGHT);
            currentHeading = Direction.RIGHT;
        }
        route.append(COMMA);

        int currentPace = 0;
        boolean reachedExit = false;
        while (!reachedExit) {
            switch (currentHeading) {
                case UP:
                    if (view.getOrDefault(currentPosition.up(), SPACE) == SCAFFOLD) {
                        currentPace++;
                        currentPosition = currentPosition.up();
                    } else if (view.getOrDefault(currentPosition.left(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(LEFT);
                        route.append(COMMA);
                        currentHeading = Direction.LEFT;
                        currentPace = 0;
                    } else if (view.getOrDefault(currentPosition.right(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(RIGHT);
                        route.append(COMMA);
                        currentHeading = Direction.RIGHT;
                        currentPace = 0;
                    } else {
                        route.append(currentPace);
                        reachedExit = true;
                    }
                    break;
                case LEFT:
                    if (view.getOrDefault(currentPosition.left(), SPACE) == SCAFFOLD) {
                        currentPace++;
                        currentPosition = currentPosition.left();
                    } else if (view.getOrDefault(currentPosition.up(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(RIGHT);
                        route.append(COMMA);
                        currentHeading = Direction.UP;
                        currentPace = 0;
                    } else if (view.getOrDefault(currentPosition.down(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(LEFT);
                        route.append(COMMA);
                        currentHeading = Direction.DOWN;
                        currentPace = 0;
                    } else {
                        route.append(currentPace);
                        reachedExit = true;
                    }
                    break;
                case DOWN:
                    if (view.getOrDefault(currentPosition.down(), SPACE) == SCAFFOLD) {
                        currentPace++;
                        currentPosition = currentPosition.down();
                    } else if (view.getOrDefault(currentPosition.left(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(RIGHT);
                        route.append(COMMA);
                        currentHeading = Direction.LEFT;
                        currentPace = 0;
                    } else if (view.getOrDefault(currentPosition.right(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(LEFT);
                        route.append(COMMA);
                        currentHeading = Direction.RIGHT;
                        currentPace = 0;
                    } else {
                        route.append(currentPace);
                        reachedExit = true;
                    }
                    break;
                case RIGHT:
                    if (view.getOrDefault(currentPosition.right(), SPACE) == SCAFFOLD) {
                        currentPace++;
                        currentPosition = currentPosition.right();
                    } else if (view.getOrDefault(currentPosition.up(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(LEFT);
                        route.append(COMMA);
                        currentHeading = Direction.UP;
                        currentPace = 0;
                    } else if (view.getOrDefault(currentPosition.down(), SPACE) == SCAFFOLD) {
                        route.append(currentPace);
                        route.append(COMMA);
                        route.append(RIGHT);
                        route.append(COMMA);
                        currentHeading = Direction.DOWN;
                        currentPace = 0;
                    } else {
                        route.append(currentPace);
                        reachedExit = true;
                    }
                    break;
            }

        }

        return route.toString();
    }

    public static long howMuchDust(final List<Long> operations) throws InterruptedException {
        final AsciiRobot robot = new AsciiRobot();
        final OpCodeComputer initialRun = new OpCodeComputer(operations, robot::moveRobot, robot::drawScreen);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(initialRun);
        executor.shutdown();
        executor.awaitTermination(2L, TimeUnit.SECONDS);

        final String route = calculateRoute(robot.getView());
        robot.reset();

        LOGGER.info("Route is: " + route);

        final String functionA = "L,10,L,10,R,6";
        final String functionB = "R,12,L,12,L,12";
        final String functionC = "L,6,L,10,R,12,R,12";

        final String mainRoutine = "A,A,B,B,C,B,C,B,C,A";
        LOGGER.info("Manually breaking this down");
        LOGGER.info("Function A: {}, Function B: {}, Function C:{}", functionA, functionB, functionC);
        LOGGER.info("Main Routine: {}", mainRoutine);

        robot.setSolvingOperations(mainRoutine, functionA, functionB, functionC);
        operations.set(0, 2L);

        executor = Executors.newSingleThreadExecutor();
        final OpCodeComputer mazeSolver = new OpCodeComputer(operations, robot::moveRobot, robot::drawScreen);
        executor.execute(mazeSolver);
        executor.shutdown();
        executor.awaitTermination(2L, TimeUnit.SECONDS);

        return robot.getDustCollected();
    }

    public static int calculateAlignmentParameter(final String viewString) {
        final ViewDetails viewDetails = parseView(viewString);
        final Map<Coordinate, Character> view = viewDetails.getView();
        final List<Coordinate> intersections = new ArrayList<>();

        for (int y = 0; y < viewDetails.getHeight(); y++) {
            for (int x = 0; x < viewDetails.getWidth(); x++) {
                final Coordinate thisSpot = new Coordinate(x, y);
                if (view.getOrDefault(thisSpot, SPACE) == SPACE) {
                    continue;
                }
                final Coordinate up = thisSpot.up();
                final Coordinate left = thisSpot.left();
                final Coordinate down = thisSpot.down();
                final Coordinate right = thisSpot.right();
                if (view.getOrDefault(up, SPACE) == SCAFFOLD && view.getOrDefault(left, SPACE) == SCAFFOLD &&
                        view.getOrDefault(down, SPACE) == SCAFFOLD && view.getOrDefault(right, SPACE) == SCAFFOLD) {
                    intersections.add(thisSpot);
                }
            }
        }

        return intersections.stream().mapToInt(x -> x.getX() * x.getY()).sum();
    }

    private static ViewDetails parseView(final String viewString) {
        final Map<Coordinate, Character> view = new HashMap<>();
        final String[] rows = viewString.split("\r?\n");
        final int height = rows.length;
        final int width = rows[0].length();

        for (int y = 0; y < height; y++) {
            final char[] row = rows[y].toCharArray();
            for (int x = 0; x < width; x++) {
                view.put(new Coordinate(x, y), row[x]);
            }
        }

        return new ViewDetails(view, height, width);
    }

    private static class AsciiRobot {
        private StringBuilder view = new StringBuilder();
        private Queue<Long> inputOperations = new ArrayDeque<>();
        private long dustCollected = 0;

        public void drawScreen(final Long output) {
            if (output.intValue() < 128) {
                this.view.append((char) output.intValue());
            } else {
                this.dustCollected = output;
            }
        }

        public Long moveRobot() {
            return this.inputOperations.poll();
        }

        private String getView() {
            return this.view.toString();
        }

        public void reset() {
            this.view = new StringBuilder();
        }

        public long getDustCollected() {
            return dustCollected;
        }

        public void setSolvingOperations(final String mainRoutine, final String functionA, final String functionB, final String functionC) {
            for (int i=0; i<mainRoutine.length(); i++) {
                this.inputOperations.add((long) mainRoutine.codePointAt(i));
            }
            this.inputOperations.add(NEW_LINE);

            for (int i=0; i<functionA.length(); i++) {
                this.inputOperations.add((long) functionA.codePointAt(i));
            }
            this.inputOperations.add(NEW_LINE);

            for (int i=0; i<functionB.length(); i++) {
                this.inputOperations.add((long) functionB.codePointAt(i));
            }
            this.inputOperations.add(NEW_LINE);

            for (int i=0; i<functionC.length(); i++) {
                this.inputOperations.add((long) functionC.codePointAt(i));
            }
            this.inputOperations.add(NEW_LINE);
            this.inputOperations.add((long) 'n');
            this.inputOperations.add(NEW_LINE);
        }
    }

    private static class ViewDetails {
        private final Map<Coordinate, Character> view;
        private final int height;
        private final int width;

        private ViewDetails(final Map<Coordinate, Character> view, final int height, final int width) {
            this.view = view;
            this.height = height;
            this.width = width;
        }

        public Map<Coordinate, Character> getView() {
            return view;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}
