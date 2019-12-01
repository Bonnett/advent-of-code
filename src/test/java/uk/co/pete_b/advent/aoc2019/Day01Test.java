package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day01.calculateFuelRequired(12));
        assertEquals(2, Day01.calculateFuelRequired(14));
        assertEquals(654, Day01.calculateFuelRequired(1969));
        assertEquals(33583, Day01.calculateFuelRequired(100756));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2, Day01.calculateFuelRequiredWithFuelMass(14));
        assertEquals(966, Day01.calculateFuelRequiredWithFuelMass(1969));
        assertEquals(50346, Day01.calculateFuelRequiredWithFuelMass(100756));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> moduleMasses = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day01"), Charset.defaultCharset());
        int totalFuel = moduleMasses.stream().map(mass -> Day01.calculateFuelRequired(Integer.parseInt(mass))).mapToInt(Integer::intValue).sum();
        int totalWithMass = moduleMasses.stream().map(mass -> Day01.calculateFuelRequiredWithFuelMass(Integer.parseInt(mass))).mapToInt(Integer::intValue).sum();

        LOGGER.info("Part 1: " + totalFuel);
        LOGGER.info("Part 2: " + totalWithMass);
    }
}
