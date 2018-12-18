package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList(".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|.");

    @Test
    public void testExamplesPart1() {
        assertEquals(1147, Day18.getWoodedResourceValue(TEST_INPUT, 10));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day18"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day18.getWoodedResourceValue(input, 10));
        LOGGER.info("Part 2: " + Day18.getWoodedResourceValue(input, 1000000000));
    }
}
