package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day03 {
    public static Answer sumValidParts(final List<String> schematic) {
        int sum = 0;
        int product = 0;
        final Map<Coordinate, String> partsMap = new HashMap<>();
        final Set<Coordinate> symbols = new HashSet<>();
        final Set<Coordinate> gears = new HashSet<>();
        final Map<Coordinate, Set<Coordinate>> boundaryPoints = new HashMap<>();

        for (int y = 0; y < schematic.size(); y++) {
            char[] row = schematic.get(y).toCharArray();
            StringBuilder tempPartNumber = new StringBuilder();
            for (int x = 0; x < row.length; x++) {
                if (row[x] >= '0' && row[x] <= '9') {
                    tempPartNumber.append(row[x]);
                } else {
                    if (!tempPartNumber.isEmpty()) {
                        final Coordinate start = new Coordinate(x - tempPartNumber.length(), y);
                        partsMap.put(start, tempPartNumber.toString());
                        boundaryPoints.put(start, getBoundaryPoints(start, tempPartNumber.length()));
                        tempPartNumber.setLength(0);
                    }
                    if (row[x] != '.') {
                        symbols.add(new Coordinate(x, y));
                        if (row[x] == '*') {
                            gears.add(new Coordinate(x, y));
                        }
                    }
                }
            }

            if (!tempPartNumber.isEmpty()) {
                final Coordinate start = new Coordinate(row.length - tempPartNumber.length(), y);
                partsMap.put(start, tempPartNumber.toString());
                boundaryPoints.put(start, getBoundaryPoints(start, tempPartNumber.length()));
            }
        }

        for (Map.Entry<Coordinate, String> entry : partsMap.entrySet()) {
            final Coordinate start = entry.getKey();
            final Set<Coordinate> boundary = boundaryPoints.get(start);
            for (Coordinate boundaryPoint : boundary) {
                if (symbols.contains(boundaryPoint)) {
                    sum += Integer.parseInt(entry.getValue());
                    break;
                }
            }
        }

        for (Coordinate gear : gears) {
            final List<Integer> matchingPoints = boundaryPoints.entrySet()
                    .stream()
                    .filter(coordinateSetEntry -> coordinateSetEntry.getValue().contains(gear))
                    .map(Map.Entry::getKey)
                    .map(partsMap::get)
                    .map(Integer::parseInt)
                    .toList();

            if (matchingPoints.size() == 2) {
                product += matchingPoints.get(0) * matchingPoints.get(1);
            }
        }

        return new Answer(sum, product);
    }

    private static Set<Coordinate> getBoundaryPoints(final Coordinate start, final int length) {
        final Set<Coordinate> boundaries = new HashSet<>();
        for (int i = start.getX() - 1; i <= start.getX() + length; i++) {
            boundaries.add(new Coordinate(i, start.getY() - 1));
            boundaries.add(new Coordinate(i, start.getY() + 1));
        }
        boundaries.add(new Coordinate(start.getX() - 1, start.getY()));
        boundaries.add(new Coordinate(start.getX() + length, start.getY()));

        return boundaries;
    }

    public record Answer(int validPartsSum, int gearRatioSum) {

    }
}
