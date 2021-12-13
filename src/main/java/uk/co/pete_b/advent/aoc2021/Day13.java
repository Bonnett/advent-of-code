package uk.co.pete_b.advent.aoc2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);

    public static int calculateNumberOfDots(final List<String> rules, final boolean stopAtOne) {
        final Set<Coordinate> coordinates = new HashSet<>();
        final List<String> foldRules = new ArrayList<>();
        int width = 0;
        int height = 0;
        for (String rule : rules) {
            if (rule.isEmpty()) {
                continue;
            }

            if (rule.startsWith("fold")) {
                foldRules.add(rule);
            } else {
                final String[] coords = rule.split(",");
                final int x = Integer.parseInt(coords[0]);
                final int y = Integer.parseInt(coords[1]);
                width = Math.max(x, width);
                height = Math.max(y, height);
                coordinates.add(new Coordinate(x, y));
            }
        }
        // Factor in starting at 0
        width++;
        height++;


        for (String foldRule : foldRules) {
            final Set<Coordinate> newCoordinates = new HashSet<>();
            String axis = foldRule.substring(11, 12);
            int value = Integer.parseInt(foldRule.substring(13));

            if (axis.equals("y")) {
                int newHeight = height - value;
                if (height % 2 == 1) {
                    newHeight--;
                }

                for (Coordinate coordinate : coordinates) {
                    if (coordinate.getY() < newHeight) {
                        newCoordinates.add(coordinate);
                    } else {
                        newCoordinates.add(new Coordinate(coordinate.getX(), (value - (coordinate.getY() - value))));
                    }
                }

                height = newHeight;
            } else {
                int newWidth = width - value;
                if (width % 2 == 1) {
                    newWidth--;
                }

                for (Coordinate coordinate : coordinates) {
                    if (coordinate.getX() < newWidth) {
                        newCoordinates.add(coordinate);
                    } else {
                        newCoordinates.add(new Coordinate((value - (coordinate.getX() - value)), coordinate.getY()));
                    }
                }

                width = newWidth;
            }

            coordinates.clear();
            coordinates.addAll(newCoordinates);
            if (stopAtOne) {
                break;
            }
        }

        if (!stopAtOne) {
            debugGrid(height, width, coordinates);
        }

        return coordinates.size();
    }

    private static void debugGrid(int height, int width, Set<Coordinate> coordinates) {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                sb.append(coordinates.contains(new Coordinate(x, y)) ? "#" : " ");
            }
            sb.append("\r\n");
        }
        sb.append("\r\n");
        LOGGER.info("\r\n{}", sb);
    }
}
