package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 {

    // Need to provide a border to check if cells are infinite
    private static final int OFFSET = 1;

    public static Answers getAnswers(final List<String> input, final int maxSafeDistance) {
        final List<Point> points = input.stream().map(Day06::parseCoordinates).collect(Collectors.toList());

        final int maxWidth = points.stream().map(point -> point.getCoordinates().getX()).max(Integer::compareTo).orElse(2000) + OFFSET * 2;
        final int maxHeight = points.stream().map(point -> point.getCoordinates().getY()).max(Integer::compareTo).orElse(2000) + OFFSET * 2;

        final Cell[][] grid = new Cell[maxHeight][maxWidth];

        for (final Point point : points) {
            for (int y = 0; y < maxHeight; y++) {
                for (int x = 0; x < maxWidth; x++) {
                    final Cell cell = grid[y][x];
                    if (cell == null) {
                        grid[y][x] = new Cell(new Coordinate(x, y), point, maxWidth - 1, maxHeight - 1);
                    } else {
                        cell.checkDistance(point);
                    }
                }
            }
        }

        final int largestNonInfiniteArea = points.stream().filter(Point::notInfiniteArea).map(point -> point.pointMembers.size()).max(Integer::compareTo).orElse(Integer.MAX_VALUE);

        int numberOfSafeCells = 0;
        for (final Cell[] cells : grid) {
            for (final Cell cell : cells) {
                if (cell.cumulativeDistance < maxSafeDistance) {
                    numberOfSafeCells++;
                }
            }
        }

        return new Answers(largestNonInfiniteArea, numberOfSafeCells);
    }

    private static Point parseCoordinates(String coordinates) {
        final String[] parts = coordinates.split(", ");
        return new Point(new Coordinate(Integer.parseInt(parts[0]) + OFFSET, Integer.parseInt(parts[1]) + OFFSET));
    }

    private static class Cell {
        private final Coordinate coords;
        private Point nearestPoint;
        private int distanceToNearestPoint = Integer.MAX_VALUE;
        private final boolean isEdgeCell;
        private int cumulativeDistance = 0;

        public Cell(final Coordinate coords, final Point initPoint, int width, int height) {
            this.coords = coords;
            this.isEdgeCell = (coords.getX() == 0 || coords.getX() == width || coords.getY() == 0 || coords.getY() == height);
            checkDistance(initPoint);
        }

        public void checkDistance(final Point point) {
            int distance = Math.abs(coords.getX() - point.coords.getX()) + Math.abs(coords.getY() - point.coords.getY());
            if (distance < distanceToNearestPoint) {
                distanceToNearestPoint = distance;
                if (nearestPoint != null) {
                    nearestPoint.removeCell(this);
                }
                point.addCell(this);
                nearestPoint = point;
            } else if (distance == distanceToNearestPoint) {
                nearestPoint.removeCell(this);
            }

            cumulativeDistance += distance;
        }
    }

    private static class Point {
        private final Coordinate coords;
        private final List<Cell> pointMembers = new ArrayList<>();
        private int edgeCellCount = 0;

        public Point(final Coordinate coords) {
            this.coords = coords;
        }

        public void addCell(final Cell cell) {
            edgeCellCount += cell.isEdgeCell ? 1 : 0;
            pointMembers.add(cell);
        }

        public void removeCell(final Cell cell) {
            edgeCellCount -= cell.isEdgeCell ? 1 : 0;
            pointMembers.remove(cell);
        }

        public Coordinate getCoordinates() {
            return coords;
        }

        public boolean notInfiniteArea() {
            return edgeCellCount == 0;
        }
    }

    public static class Answers {
        private final int largestNonInfiniteArea;
        private final int numberOfSafeCells;

        Answers(final int largestNonInfiniteArea, final int numberOfSafeCells) {
            this.largestNonInfiniteArea = largestNonInfiniteArea;
            this.numberOfSafeCells = numberOfSafeCells;
        }

        int getLargestNonInfiniteArea() {
            return largestNonInfiniteArea;
        }

        int getNumberOfSafeCells() {
            return numberOfSafeCells;
        }
    }
}
