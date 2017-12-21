package uk.co.pete_b.advent.aoc2017;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class Day15Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    @Test
    public void testPart1() {
        assertEquals(588, Day15.getJudgeCount(65, 8921, false));
    }

    @Test
    public void testPart2() {
        assertEquals(309, Day15.getJudgeCount(65, 8921, true));
    }

    @Test
    public void getAnswers() {
        LOGGER.info("Part 1: " + Day15.getJudgeCount(634, 301, false));
        LOGGER.info("Part 2: " + Day15.getJudgeCount(634, 301, true));
    }
}
