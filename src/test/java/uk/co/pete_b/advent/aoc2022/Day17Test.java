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
public class Day17Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    private static final String WIND_DIRECTION = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(3068, Day17.calculateTowerHeight(WIND_DIRECTION));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(1514285714288L, Day17.calculateTowerHeightViaCycles(WIND_DIRECTION, 1000000000000L));
    }

    @Test
    public void getAnswers() throws IOException {
        final String windDirection = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day17")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day17.calculateTowerHeight(windDirection));
        LOGGER.info("Part 2: {}", Day17.calculateTowerHeightViaCycles(windDirection, 1000000000000L));
    }
}
