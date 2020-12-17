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
public class Day17Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    private static final String EXAMPLE_INPUT = """
            .#.
            ..#
            ###
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(112, Day17.countActiveCubesInThreeDimensions(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(848, Day17.countActiveCubesInFourDimensions(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String inputMap = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day17"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day17.countActiveCubesInThreeDimensions(inputMap));
        LOGGER.info("Part 2: {}", Day17.countActiveCubesInFourDimensions(inputMap));
    }
}
