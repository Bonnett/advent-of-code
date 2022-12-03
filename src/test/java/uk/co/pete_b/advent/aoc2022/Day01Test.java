package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    private static final String SAMPLE_INPUT = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000""";

    @Test
    public void testExamplesPart1() {
        assertEquals(24000, Day01.calculateMaxElfSnacks(SAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(45000, Day01.calculateTopThreeElves(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String elfSnacks = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day01")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day01.calculateMaxElfSnacks(elfSnacks));
        LOGGER.info("Part 2: {}", Day01.calculateTopThreeElves(elfSnacks));
    }
}
