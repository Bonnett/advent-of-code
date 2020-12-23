package uk.co.pete_b.advent.aoc2020;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);
    private static final String EXAMPLE_INPUT = "389125467";

    @Test
    public void testExamplesPart1() {
        assertEquals("67384529", Day23.playCupGame(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(149245887792L, Day23.playExtendedCupGame(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = "789465123";
        LOGGER.info("Part 1: {}", Day23.playCupGame(puzzleInput));
        LOGGER.info("Part 2: {}", Day23.playExtendedCupGame(puzzleInput));
    }
}
