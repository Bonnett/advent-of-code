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
public class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);
    public static final String SAMPLE_PAIRS = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(2, Day04.calculateFullyOverlappingPairs(IOUtils.readLines(new StringReader(SAMPLE_PAIRS))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(4, Day04.calculateAnyOverlappingPairs(IOUtils.readLines(new StringReader(SAMPLE_PAIRS))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day04")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day04.calculateFullyOverlappingPairs(pairs));
        LOGGER.info("Part 2: {}", Day04.calculateAnyOverlappingPairs(pairs));
    }
}
