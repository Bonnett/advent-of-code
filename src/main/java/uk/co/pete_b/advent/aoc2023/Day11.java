package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    public static long calculateDistances(final List<String> spaceMap, int blankLineDistance) {
        long sumDistances = 0;

        final List<Coordinate> galaxies = new ArrayList<>();
        final Set<Integer> horizontalBlankLines = new HashSet<>();
        final Set<Integer> verticalBlankLines = Arrays.stream(IntStream.range(0, spaceMap.get(0).length()).toArray()).boxed().collect(Collectors.toSet());

        for (int y = 0; y < spaceMap.size(); y++) {
            final String spaceLine = spaceMap.get(y);
            if (spaceLine.matches("\\.+")) {
                horizontalBlankLines.add(y);
            }
            for (int x = 0; x < spaceLine.length(); x++) {
                if (spaceLine.charAt(x) == '#') {
                    galaxies.add(new Coordinate(x, y));
                    verticalBlankLines.remove(x);
                }
            }
        }

        for (int i = 0; i < galaxies.size(); i++) {
            final Coordinate galaxyOne = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                final Coordinate galaxyTwo = galaxies.get(j);
                final int maxX = Math.max(galaxyTwo.getX(), galaxyOne.getX());
                final int minX = Math.min(galaxyTwo.getX(), galaxyOne.getX());
                final int maxY = Math.max(galaxyTwo.getY(), galaxyOne.getY());
                final int minY = Math.min(galaxyTwo.getY(), galaxyOne.getY());
                final int manhattanDistance = (maxX - minX) + (maxY - minY);
                final long extraXDistance = (blankLineDistance - 1) * IntStream.range(minX, maxX).filter(verticalBlankLines::contains).count();
                final long extraYDistance = (blankLineDistance - 1) * IntStream.range(minY, maxY).filter(horizontalBlankLines::contains).count();

                sumDistances += extraXDistance + extraYDistance + manhattanDistance;
            }
        }

        return sumDistances;
    }
}
