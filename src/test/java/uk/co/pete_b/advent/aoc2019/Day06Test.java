package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    @Test
    public void testExamplesPart1() {
        final List<String> testData = Arrays.asList("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L");
        assertEquals(42, Day06.calculateOrbits(testData));
    }

    @Test
    public void testExamplesPart2() {
        final List<String> testData = Arrays.asList("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L", "K)YOU", "I)SAN");
        assertEquals(4, Day06.calculateOrbitalTransfers(testData));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> solarSystem = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day06"), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day06.calculateOrbits(solarSystem));
        LOGGER.info("Part 2: {}", Day06.calculateOrbitalTransfers(solarSystem));
    }

}
