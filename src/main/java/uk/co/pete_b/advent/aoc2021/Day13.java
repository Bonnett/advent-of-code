package uk.co.pete_b.advent.aoc2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.LongCoordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);

    public static int calculateNumberOfDots(final List<String> rules, final boolean stopAtOne) {
        final Set<LongCoordinate> coordinates = new HashSet<>();
        final List<String> foldRules = new ArrayList<>();
        for (String rule : rules) {
            if (rule.isEmpty()) {
                continue;
            }

            if (rule.startsWith("fold")) {
                foldRules.add(rule);
            } else {
                final String[] coords = rule.split(",");
                final long x = Long.parseLong(coords[0]);
                final long y = Long.parseLong(coords[1]);
                coordinates.add(new LongCoordinate(x, y));
            }
        }

        for (String foldRule : foldRules) {
            final Set<LongCoordinate> newCoordinates = new HashSet<>();
            String axis = foldRule.substring(11, 12);
            long value = Long.parseLong(foldRule.substring(13));

            if (axis.equals("y")) {
                for (LongCoordinate coordinate : coordinates) {
                    if (coordinate.getY() < value) {
                        newCoordinates.add(coordinate);
                    } else {
                        newCoordinates.add(new LongCoordinate(coordinate.getX(), (value - (coordinate.getY() - value))));
                    }
                }
            } else {
                for (LongCoordinate coordinate : coordinates) {
                    if (coordinate.getX() < value) {
                        newCoordinates.add(coordinate);
                    } else {
                        newCoordinates.add(new LongCoordinate((value - (coordinate.getX() - value)), coordinate.getY()));
                    }
                }
            }

            coordinates.clear();
            coordinates.addAll(newCoordinates);
            if (stopAtOne) {
                break;
            }
        }

        if (!stopAtOne) {
            debugGrid(coordinates);
        }

        return coordinates.size();
    }

    private static void debugGrid(Set<LongCoordinate> coordinates) {
        long width = 0;
        long height = 0;
        for (LongCoordinate coord : coordinates) {
            width = Math.max(coord.getX(), width);
            height = Math.max(coord.getY(), height);
        }
        width++;
        height++;

        final StringBuilder sb = new StringBuilder();
        for (long y = 0; y < height; y++) {
            for (long x = 0; x < width; x++) {
                sb.append(coordinates.contains(new LongCoordinate(x, y)) ? "#" : " ");
            }
            sb.append("\r\n");
        }
        sb.append("\r\n");
        LOGGER.info("\r\n{}", sb);
    }
}
