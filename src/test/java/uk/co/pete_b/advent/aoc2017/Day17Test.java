package uk.co.pete_b.advent.aoc2017;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Day17Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    @Test
    public void testPart1() {
        assertEquals(638, Day17.solveSpinlock(3));
    }

    @Test
    public void getAnswers() throws IOException {
        LOGGER.info("Part 1: " + Day17.solveSpinlock(369));
        LOGGER.info("Part 2: " + Day17.getNextToZero(369));
    }
}
