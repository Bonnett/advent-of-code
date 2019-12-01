package uk.co.pete_b.advent.aoc2018;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(32, Day09.calculateWinningScore(9, 25));
        assertEquals(8317, Day09.calculateWinningScore(10, 1618));
        assertEquals(146373, Day09.calculateWinningScore(13, 7999));
        assertEquals(2764, Day09.calculateWinningScore(17, 1104));
        assertEquals(54718, Day09.calculateWinningScore(21, 6111));
        assertEquals(37305, Day09.calculateWinningScore(30, 5807));
    }

    @Test
    public void getAnswers() {
        LOGGER.info("Part 1: " + Day09.calculateWinningScore(473, 70904));
        LOGGER.info("Part 2: " + Day09.calculateWinningScore(473, 7090400));
    }
}
