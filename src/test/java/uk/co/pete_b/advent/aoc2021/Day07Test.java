package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final String EXAMPLE_CRAB_SUBMARINES = "16,1,2,0,4,2,7,1,2,14";

    @Test
    public void testExamplesPart1() {
        assertEquals(37, Day07.calculateLeastFuel(EXAMPLE_CRAB_SUBMARINES));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(168, Day07.calculateLeastFuelWithFuelRate(EXAMPLE_CRAB_SUBMARINES));
    }

    @Test
    public void getAnswers() throws IOException {
        final String crabSubmarines = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day07")), Charset.defaultCharset()).trim();

        LOGGER.info("Part 1: {}", Day07.calculateLeastFuel(crabSubmarines));
        LOGGER.info("Part 2: {}", Day07.calculateLeastFuelWithFuelRate(crabSubmarines));
    }
}
