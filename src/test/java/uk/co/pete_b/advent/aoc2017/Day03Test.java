package uk.co.pete_b.advent.aoc2017;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class Day03Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);

    @Test
    public void testExamplesPart1() {
        Day03 dayThree = new Day03(1024);
        assertEquals(0, dayThree.calculateDistance(1));
        assertEquals(3, dayThree.calculateDistance(12));
        assertEquals(2, dayThree.calculateDistance(23));
        assertEquals(31, dayThree.calculateDistance(1024));
    }

    @Test
    public void testExamplesPart2() {
        Day03 dayThree = new Day03(1024);
        assertEquals(1, dayThree.getTotal(1));
        assertEquals(1, dayThree.getTotal(2));
        assertEquals(4, dayThree.getTotal(4));
        assertEquals(5, dayThree.getTotal(5));
    }

    @Test
    public void getAnswers() {
        Day03 dayThree = new Day03(265149);
        LOGGER.info("Part 1: " + dayThree.calculateDistance(265149));
        LOGGER.info("Part 2: " + dayThree.getAnswerPart2());
    }
}
