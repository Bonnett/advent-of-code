package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Day19 {
    public static long getTractorBeamReach(final List<Long> operations) throws InterruptedException {
        final Set<Coordinate> beamCoords = createGrid(operations, 0, 0, 50, 50);
        return beamCoords.size();
    }

    public static long findSanta(final List<Long> operations) throws Exception {
        // Trial and error found these values
        final Set<Coordinate> beamCoords = createGrid(operations, 1800, 1800, 2100, 2100);

        // 99 because we need to include this coord to make 100
        final Set<Coordinate> coordsWhichMatch = beamCoords.stream()
                .filter(coord -> beamCoords.contains(new Coordinate(coord.getX() + 99, coord.getY())) && beamCoords.contains(new Coordinate(coord.getX(), coord.getY() + 99)))
                .collect(Collectors.toSet());

        final int lowestX = coordsWhichMatch.stream().mapToInt(Coordinate::getX).min().orElseThrow();
        final int lowestY = coordsWhichMatch.stream().mapToInt(Coordinate::getY).min().orElseThrow();

        return (lowestX * 10000) + lowestY;
    }

    private static Set<Coordinate> createGrid(final List<Long> operations, final int startX, final int startY, final int width, final int height) throws InterruptedException {
        final Set<Coordinate> targetMap = new ConcurrentSkipListSet<>();

        final ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int y = startY; y < width; y++) {
            for (int x = startX; x < height; x++) {
                final Drone drone = new Drone(targetMap, new Coordinate(x, y));
                final OpCodeComputer computer = new OpCodeComputer(operations, drone::move, drone::getFeedback);
                executor.submit(computer);
            }
        }
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        return targetMap;
    }

    private static class Drone {
        private final Queue<Long> values;
        private final Set<Coordinate> targetMap;
        private final Coordinate currentCoord;

        public Drone(final Set<Coordinate> targetMap, final Coordinate coordinate) {
            this.targetMap = targetMap;
            this.currentCoord = coordinate;
            this.values = new ArrayDeque<>(Arrays.asList((long) coordinate.getX(), (long) coordinate.getY()));
        }

        public Long move() {
            return this.values.poll();
        }

        public void getFeedback(final Long output) {
            if (output == 1L) {
                this.targetMap.add(this.currentCoord);
            }
        }
    }
}
