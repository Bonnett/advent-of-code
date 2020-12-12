package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static final String EXAMPLE_INPUT = """
            F10
            N3
            F7
            R90
            F11
            """;
    @Test
    public void testExamplesPart1() {
        assertEquals(25, Day12.calculateManhattanDistance(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(286, Day12.calculateManhattanDistanceWithWaypoint(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String instructions = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day12"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day12.calculateManhattanDistance(instructions));
        LOGGER.info("Part 2: {}", Day12.calculateManhattanDistanceWithWaypoint(instructions));
    }

}
