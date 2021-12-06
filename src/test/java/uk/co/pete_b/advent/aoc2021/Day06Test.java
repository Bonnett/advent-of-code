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
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    private static final String EXAMPLE_LANTERN_FISH = "3,4,3,1,2";

    @Test
    public void testExamplesPart1() {
        assertEquals(5934, Day06.calculateLanternFish(EXAMPLE_LANTERN_FISH, 80));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(26984457539L, Day06.calculateLanternFish(EXAMPLE_LANTERN_FISH, 256));
    }

    @Test
    public void getAnswers() throws IOException {
        final String lanternFish = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day06")), Charset.defaultCharset()).trim();

        LOGGER.info("Part 1: {}", Day06.calculateLanternFish(lanternFish, 80));
        LOGGER.info("Part 2: {}", Day06.calculateLanternFish(lanternFish, 256));
    }
}
