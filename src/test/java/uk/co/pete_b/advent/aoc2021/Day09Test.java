package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    private static final String EXAMPLE_HEIGHTMAP = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(15, Day09.sumLowPoints(Arrays.stream(EXAMPLE_HEIGHTMAP.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1134, Day09.multiplyThreeLargestBasins(Arrays.stream(EXAMPLE_HEIGHTMAP.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> heightMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day09")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day09.sumLowPoints(heightMap));
        LOGGER.info("Part 2: {}", Day09.multiplyThreeLargestBasins(heightMap));
    }
}
