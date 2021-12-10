package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day09 {
    public static int sumLowPoints(final List<String> rows) {
        final int height = rows.size();
        final int width = rows.get(0).length();
        final Map<Coordinate, Integer> heightMap = generateHeightMap(rows);

        final List<Coordinate> basinStarts = getBasinStarts(height, width, heightMap);
        return basinStarts.stream().map(heightMap::get).mapToInt(Integer::intValue).sum() + basinStarts.size();
    }

    public static int multiplyThreeLargestBasins(final List<String> rows) {
        final int height = rows.size();
        final int width = rows.get(0).length();
        final Map<Coordinate, Integer> heightMap = generateHeightMap(rows);

        final List<Coordinate> basinStarts = getBasinStarts(height, width, heightMap);
        final List<Set<Coordinate>> basins = new ArrayList<>();
        for (Coordinate start : basinStarts) {
            final Set<Coordinate> basin = new HashSet<>();
            basin.add(start);
            while (true) {
                final Set<Coordinate> pointsToAdd = new HashSet<>();
                for (Coordinate point : basin) {
                    addPointIfAbsent(basin, point.up(), heightMap, pointsToAdd);
                    addPointIfAbsent(basin, point.down(), heightMap, pointsToAdd);
                    addPointIfAbsent(basin, point.left(), heightMap, pointsToAdd);
                    addPointIfAbsent(basin, point.right(), heightMap, pointsToAdd);
                }
                basin.addAll(pointsToAdd);

                if (pointsToAdd.isEmpty()) {
                    break;
                }
            }
            basins.add(basin);
        }

        basins.sort(Comparator.comparingInt(Set::size));
        Collections.reverse(basins);

        return basins.get(0).size() * basins.get(1).size() * basins.get(2).size();
    }

    private static Map<Coordinate, Integer> generateHeightMap(final List<String> rows) {
        final Map<Coordinate, Integer> heightMap = new HashMap<>();
        for (int y = 0; y < rows.size(); y++) {
            final String row = rows.get(y);
            for (int x = 0; x < row.length(); x++) {
                heightMap.put(new Coordinate(x, y), (int) row.charAt(x) - 48);
            }
        }
        return heightMap;
    }

    private static List<Coordinate> getBasinStarts(final int height, final int width,
                                                   final Map<Coordinate, Integer> heightMap) {
        final List<Coordinate> basinStarts = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Coordinate point = new Coordinate(x, y);
                final int startVal = heightMap.get(point);
                if (heightMap.getOrDefault(point.left(), Integer.MAX_VALUE) > startVal
                        && heightMap.getOrDefault(point.up(), Integer.MAX_VALUE) > startVal
                        && heightMap.getOrDefault(point.right(), Integer.MAX_VALUE) > startVal
                        && heightMap.getOrDefault(point.down(), Integer.MAX_VALUE) > startVal) {
                    basinStarts.add(point);
                }
            }
        }

        return basinStarts;
    }

    private static void addPointIfAbsent(final Set<Coordinate> basin, final Coordinate point,
                                         final Map<Coordinate, Integer> heightMap, final Set<Coordinate> pointsToAdd) {
        if (!basin.contains(point) && heightMap.getOrDefault(point, Integer.MAX_VALUE) < 9) {
            pointsToAdd.add(point);
        }
    }
}
