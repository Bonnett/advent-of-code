package uk.co.pete_b.advent.aoc2017;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day14Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    @Test
    public void testExamples() {
        final Day14.Answers exampleAnswer = Day14.calculateSquaresUsed("flqrgnkx");
        assertEquals(8108, exampleAnswer.getNumberOfSquaresUsed());
        assertEquals(1242, exampleAnswer.getNumberOfGroups());
    }

    @Test
    public void getAnswers() {
        final Day14.Answers answer = Day14.calculateSquaresUsed("hfdlxzhv");
        LOGGER.info("Part 1: " + answer.getNumberOfSquaresUsed());
        LOGGER.info("Part 2: " + answer.getNumberOfGroups());
    }

}