package uk.co.pete_b.advent.aoc2018;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private static final Pattern LINE_PATTERN = Pattern.compile("position=< *([^,]+), *([^,]+)> velocity=< *([^,]+), *([^,]+)>");
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

    public static void generateMessage(final List<String> input, final int startOffset, final int gridOffset) {
        final List<MovingCoordinate> points = new ArrayList<>();

        for (String line : input) {
            final List<Integer> parts = parseLine(line);
            points.add(new MovingCoordinate(parts.get(0), parts.get(1), parts.get(2), parts.get(3)));
        }

        for (int i = 1; i < startOffset; i++) {
            for (final MovingCoordinate point : points) {
                point.move();
            }

            final Integer maxY = points.stream().map(Coordinate::getY).max(Integer::compareTo).orElse(2000);
            final Integer minY = points.stream().map(Coordinate::getY).min(Integer::compareTo).orElse(2000);

            final Integer maxX = points.stream().map(Coordinate::getX).max(Integer::compareTo).orElse(2000);
            final Integer minX = points.stream().map(Coordinate::getX).min(Integer::compareTo).orElse(2000);

            if ((maxY - minY) < 10) {
                System.out.println("Second " + i);

                final char[][] grid = generateGrid(gridOffset + Math.max(maxX, maxY) + 1);
                for (MovingCoordinate point : points) {
                    grid[gridOffset + point.getY()][gridOffset + point.getX()] = '#';
                }

                printGrid(gridOffset, minX, minY, i, grid);
            }
        }
    }

    private static List<Integer> parseLine(final String line) {
        final Matcher matcher = LINE_PATTERN.matcher(line);
        final List<Integer> values = new ArrayList<>();

        if (matcher.matches()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                values.add(Integer.parseInt(matcher.group(i + 1)));
            }
        }

        return values;
    }

    private static void printGrid(final int offset, final int minX, final int minY, final int second, final char[][] grid) {
        LOGGER.info("Second: " + second);
        int rowNum = 0;
        for (char[] row : grid) {
            if (rowNum++ < offset + minY) {
                continue;
            }
            LOGGER.info(new String(row).substring(offset + minX));
        }
        LOGGER.info("");
    }

    private static char[][] generateGrid(int gridSize) {
        final char[][] grid = new char[gridSize][gridSize];
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = '.';
            }
        }

        return grid;
    }

    private static class MovingCoordinate extends Coordinate {
        private final int velocityX;
        private final int velocityY;

        public MovingCoordinate(int startX, int startY, int velocityX, int velocityY) {
            super(startX, startY);
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }

        public void move() {
            this.x += velocityX;
            this.y += velocityY;
        }
    }
}
