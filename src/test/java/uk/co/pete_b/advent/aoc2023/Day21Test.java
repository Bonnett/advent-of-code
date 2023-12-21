package uk.co.pete_b.advent.aoc2023;

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

@Tag("2023")
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private static final String SAMPLE_INPUT = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........""";

    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 1));
        assertEquals(4, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 2));
        assertEquals(6, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 3));
        assertEquals(16, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 6));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(16, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 6));
        assertEquals(50, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 10));
        assertEquals(1594, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 50));
        assertEquals(6536, Day21.getReachablePlotCount(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 100));
        /*
            These take too long
            assertEquals(167004, Day21.calculateViaMaths(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 500));
            assertEquals(668697, Day21.calculateViaMaths(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 1000));
            assertEquals(16733044, Day21.calculateViaMaths(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 5000));
        */
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> gardenMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day21")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day21.getReachablePlotCount(gardenMap, 64));
        LOGGER.info("Part 2: {}", Day21.calculateViaMaths(gardenMap, 26501365L));
    }
}
