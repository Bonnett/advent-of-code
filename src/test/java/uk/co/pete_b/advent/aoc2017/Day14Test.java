package uk.co.pete_b.advent.aoc2017;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Day14Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    @Test
    public void testExamples() {
        final Day14.Answers exampleAnswer = Day14.calculateSquaresUsed("flqrgnkx");
        Assert.assertEquals(8108, exampleAnswer.getNumberOfSquaresUsed());
        Assert.assertEquals(1242, exampleAnswer.getNumberOfGroups());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day14.Answers answer = Day14.calculateSquaresUsed("hfdlxzhv");
        LOGGER.info("Part 1: " + answer.getNumberOfSquaresUsed());
        LOGGER.info("Part 2: " + answer.getNumberOfGroups());
    }

}