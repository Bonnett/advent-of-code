package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    private static final String SAMPLE_INPUT = "..#\n#..\n...";

    @Test
    public void testExamplesPart1() {
        assertEquals(5587, Day22.getVirusInfections(SAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2511944, Day22.getVirusInfectionsWithWeakening(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day22"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day22.getVirusInfections(puzzleInput));
        LOGGER.info("Part 2: " + Day22.getVirusInfectionsWithWeakening(puzzleInput));
    }
}
