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
public class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(71, Day18.calculateSums("1 + 2 * 3 + 4 * 5 + 6", true));
        assertEquals(26, Day18.calculateSums("2 * 3 + (4 * 5)", true));
        assertEquals(437, Day18.calculateSums("5 + (8 * 3 + 9 + 3 * 4 * 3)", true));
        assertEquals(12240, Day18.calculateSums("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)", true));
        assertEquals(13632, Day18.calculateSums("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", true));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(231, Day18.calculateSums("1 + 2 * 3 + 4 * 5 + 6", false));
        assertEquals(51, Day18.calculateSums("1 + (2 * 3) + (4 * (5 + 6))", false));
        assertEquals(46, Day18.calculateSums("2 * 3 + (4 * 5)", false));
        assertEquals(1445, Day18.calculateSums("5 + (8 * 3 + 9 + 3 * 4 * 3)", false));
        assertEquals(669060, Day18.calculateSums("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)", false));
        assertEquals(23340, Day18.calculateSums("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", false));
    }

    @Test
    public void getAnswers() throws IOException {
        final String sums = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day18"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day18.calculateSums(sums, true));
        LOGGER.info("Part 2: {}", Day18.calculateSums(sums, false));
    }
}
