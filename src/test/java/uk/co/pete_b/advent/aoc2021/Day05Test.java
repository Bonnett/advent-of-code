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
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    private static final String EXAMPLE_LINES = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(5, Day05.calculatePointsWithOverlaps(Arrays.stream(EXAMPLE_LINES.split("\r?\n")).toList(), false));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(12, Day05.calculatePointsWithOverlaps(Arrays.stream(EXAMPLE_LINES.split("\r?\n")).toList(), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> lines = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day05")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day05.calculatePointsWithOverlaps(lines, false));
        LOGGER.info("Part 2: {}", Day05.calculatePointsWithOverlaps(lines, true));
    }
}
