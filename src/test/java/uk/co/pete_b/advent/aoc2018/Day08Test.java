package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);
    private static final String TEST_INPUT = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

    @Test
    public void testExamplesPart1() {
        assertEquals(138, Day08.sumMetadata(TEST_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(66, Day08.rootNodeValue(TEST_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String input = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2018/day08"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day08.sumMetadata(input));
        LOGGER.info("Part 2: " + Day08.rootNodeValue(input));
    }
}
