package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private static final String SAMPLE_INPUT = "../.# => ##./#../...\n" +
            ".#./..#/### => #..#/..../..../#..#";

    @Test
    public void testExamplesPart1() {
        assertEquals(12, Day21.getPixelsOn(2, SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day21"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day21.getPixelsOn(5, puzzleInput));
        LOGGER.info("Part 2: " + Day21.getPixelsOn(18, puzzleInput));
    }
}
