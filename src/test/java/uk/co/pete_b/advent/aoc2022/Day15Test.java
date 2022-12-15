package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day15Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    private static final String BEACON_SETUP = """
            Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            Sensor at x=9, y=16: closest beacon is at x=10, y=16
            Sensor at x=13, y=2: closest beacon is at x=15, y=3
            Sensor at x=12, y=14: closest beacon is at x=10, y=16
            Sensor at x=10, y=20: closest beacon is at x=10, y=16
            Sensor at x=14, y=17: closest beacon is at x=10, y=16
            Sensor at x=8, y=7: closest beacon is at x=2, y=10
            Sensor at x=2, y=0: closest beacon is at x=2, y=10
            Sensor at x=0, y=11: closest beacon is at x=2, y=10
            Sensor at x=20, y=14: closest beacon is at x=25, y=17
            Sensor at x=17, y=20: closest beacon is at x=21, y=22
            Sensor at x=16, y=7: closest beacon is at x=15, y=3
            Sensor at x=14, y=3: closest beacon is at x=15, y=3
            Sensor at x=20, y=1: closest beacon is at x=15, y=3
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(26, Day15.calculateBeaconLessSquaresInRow(IOUtils.readLines(new StringReader(BEACON_SETUP)), 10));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(56000011L, Day15.findTuningFrequency(IOUtils.readLines(new StringReader(BEACON_SETUP)), 0, 20));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day15")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day15.calculateBeaconLessSquaresInRow(pairs, 2000000));
        LOGGER.info("Part 2: {}", Day15.findTuningFrequency(pairs, 0, 4000000));
    }
}
