package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(0, Day05.reduceStringLength("aA"));
        assertEquals(0, Day05.reduceStringLength("abBA"));
        assertEquals(4, Day05.reduceStringLength("abAB"));
        assertEquals(6, Day05.reduceStringLength("aabAAB"));
        assertEquals(10, Day05.reduceStringLength("dabAcCaCBAcCcaDA"));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(4, Day05.removeAndReduce("dabAcCaCBAcCcaDA"));
    }

    @Test
    public void getAnswers() throws IOException {
        final String input = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2018/day05"), Charset.defaultCharset()).trim();
        LOGGER.info("Part 1: " + Day05.reduceStringLength(input));
        LOGGER.info("Part 2: " + Day05.removeAndReduce(input));
    }
}
