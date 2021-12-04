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
public class Day03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);

    private static final String EXAMPLE_DIAGNOSTIC_REPORT = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(198, Day03.calculatePowerConsumption(Arrays.stream(EXAMPLE_DIAGNOSTIC_REPORT.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(230, Day03.calculateLifeSupportRating(Arrays.stream(EXAMPLE_DIAGNOSTIC_REPORT.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> moves = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day03")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day03.calculatePowerConsumption(moves));
        LOGGER.info("Part 2: {}", Day03.calculateLifeSupportRating(moves));
    }
}
