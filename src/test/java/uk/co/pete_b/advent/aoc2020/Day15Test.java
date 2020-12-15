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
public class Day15Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    @Test
    public void testExamplesPart1() {
        final int targetIterations = 2020;
        assertEquals(436, Day15.findIteration2020("0,3,6", targetIterations));
        assertEquals(1, Day15.findIteration2020("1,3,2", targetIterations));
        assertEquals(10, Day15.findIteration2020("2,1,3", targetIterations));
        assertEquals(27, Day15.findIteration2020("1,2,3", targetIterations));
        assertEquals(78, Day15.findIteration2020("2,3,1", targetIterations));
        assertEquals(438, Day15.findIteration2020("3,2,1", targetIterations));
        assertEquals(1836, Day15.findIteration2020("3,1,2", targetIterations));
    }

    @Test
    public void testExamplesPart2() {
        final int targetIterations = 30000000;
        assertEquals(175594, Day15.findIteration2020("0,3,6", targetIterations));
        assertEquals(2578, Day15.findIteration2020("1,3,2", targetIterations));
        assertEquals(3544142, Day15.findIteration2020("2,1,3", targetIterations));
        assertEquals(261214, Day15.findIteration2020("1,2,3", targetIterations));
        assertEquals(6895259, Day15.findIteration2020("2,3,1", targetIterations));
        assertEquals(18, Day15.findIteration2020("3,2,1", targetIterations));
        assertEquals(362, Day15.findIteration2020("3,1,2", targetIterations));
    }

    @Test
    public void getAnswers() throws IOException {
        final String sequence = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day15"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day15.findIteration2020(sequence, 2020));
        LOGGER.info("Part 2: {}", Day15.findIteration2020(sequence, 30000000));
    }
}
