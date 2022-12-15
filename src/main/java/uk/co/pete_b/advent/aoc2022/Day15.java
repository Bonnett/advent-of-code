package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.tuple.Pair;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day15 {

    private static final Pattern ROW_PATTERN = Pattern.compile("Sensor at x=([^,]+), y=([^:]+): closest beacon is at x=([^,]+), y=(.+)");

    public static int calculateBeaconLessSquaresInRow(final List<String> setup, final int row) {

        final List<Pair<Integer, Integer>> ranges = new ArrayList<>();

        for (final String setupRow : setup) {
            Matcher matcher = ROW_PATTERN.matcher(setupRow);
            if (matcher.matches()) {
                final Coordinate beacon = new Coordinate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                final Sensor sensor = new Sensor(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))), beacon);
                sensor.getRange(row).ifPresent(ranges::add);
            }
        }

        final List<Pair<Integer, Integer>> nonOverlappingPairs = new ArrayList<>();
        ranges.sort(Comparator.comparing(Pair::getLeft));
        Pair<Integer, Integer> currentPair = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            final Pair<Integer, Integer> range = ranges.get(i);
            if (currentPair.getRight() >= range.getLeft()) {
                currentPair = Pair.of(currentPair.getLeft(), Math.max(currentPair.getRight(), range.getRight()));
            }
        }

        nonOverlappingPairs.add(currentPair);

        return nonOverlappingPairs.stream().map(x -> x.getRight() - x.getLeft()).mapToInt(Integer::intValue).sum();
    }

    public static long findTuningFrequency(final List<String> setup, final int min, final int max) {
        final List<Sensor> sensors = new ArrayList<>();

        for (final String setupRow : setup) {
            Matcher matcher = ROW_PATTERN.matcher(setupRow);
            if (matcher.matches()) {
                final Coordinate beacon = new Coordinate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                final Sensor sensor = new Sensor(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))), beacon);
                sensors.add(sensor);
            }
        }

        try {
            IntStream.range(min, max).parallel().forEach(row -> {
                final List<Pair<Integer, Integer>> ranges = new ArrayList<>();
                sensors.forEach(sensor -> sensor.getRange(row).ifPresent(ranges::add));
                ranges.sort(Comparator.comparing(Pair::getLeft));
                Pair<Integer, Integer> currentPair = ranges.get(0);

                for (int i = 1; i < ranges.size(); i++) {
                    final Pair<Integer, Integer> range = ranges.get(i);
                    if (currentPair.getRight() >= range.getLeft() || currentPair.getRight() + 1 == range.getLeft()) {
                        currentPair = Pair.of(currentPair.getLeft(), Math.max(currentPair.getRight(), range.getRight()));
                    } else {
                        throw new TuningException((currentPair.getRight() + 1), row);
                    }
                }
            });
        }
        catch (TuningException e)
        {
            return e.getTuningFrequency();
        }

        return -1L;
    }

    private static class TuningException extends RuntimeException
    {
        private final int x;
        private final int y;
        public TuningException(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public long getTuningFrequency() {
            return (((long) x) * 4000000L) + y;
        }
    }

    private static class Sensor {
        private final Coordinate sensor;
        private final int manhattanDistance;

        private Sensor(Coordinate sensor, Coordinate beacon) {
            this.sensor = sensor;
            this.manhattanDistance = Math.abs(sensor.getX() - beacon.getX()) + Math.abs(sensor.getY() - beacon.getY());
        }

        private Optional<Pair<Integer, Integer>> getRange(final int rowNum) {
            if (this.sensor.getY() + this.manhattanDistance >= rowNum && this.sensor.getY() - this.manhattanDistance <= rowNum) {
                final int diff = this.manhattanDistance - Math.abs(rowNum - this.sensor.getY());
                return Optional.of(Pair.of(this.sensor.getX() - diff, this.sensor.getX() + diff));
            }

            return Optional.empty();
        }
    }
}
