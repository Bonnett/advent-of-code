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
public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    private static final String EXAMPLE_CAVE = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(24, Day14.countTotalSand(IOUtils.readLines(new StringReader(EXAMPLE_CAVE)), true));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(93, Day14.countTotalSand(IOUtils.readLines(new StringReader(EXAMPLE_CAVE)), false));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day14")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day14.countTotalSand(pairs, true));
        LOGGER.info("Part 2: {}", Day14.countTotalSand(pairs, false));
    }
}
