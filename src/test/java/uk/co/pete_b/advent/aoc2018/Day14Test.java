package uk.co.pete_b.advent.aoc2018;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals("0124515891", Day14.getNextTenRecipeScores(5));
        assertEquals("5158916779", Day14.getNextTenRecipeScores(9));
        assertEquals("9251071085", Day14.getNextTenRecipeScores(18));
        assertEquals("5941429882", Day14.getNextTenRecipeScores(2018));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(5, Day14.findRecipesBeforeMatchingString("01245"));
        assertEquals(9, Day14.findRecipesBeforeMatchingString("51589"));
        assertEquals(18, Day14.findRecipesBeforeMatchingString("92510"));
        assertEquals(2018, Day14.findRecipesBeforeMatchingString("59414"));
    }

    @Test
    public void getAnswers() {
        LOGGER.info("Part 1: " + Day14.getNextTenRecipeScores(704321));
        LOGGER.info("Part 2: " + Day14.findRecipesBeforeMatchingString("704321"));
    }
}
