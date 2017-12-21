package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day13Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    private static final String SAMPLE_INPUT = "0: 3\n" +
            "1: 2\n" +
            "4: 4\n" +
            "6: 4";

    @Test
    public void testPart1() {
        assertEquals(24, Day13.getSeverity(SAMPLE_INPUT).getNoDelaySeverity());
    }

    @Test
    public void testPart2() {
        assertEquals(10, Day13.getSeverity(SAMPLE_INPUT).getDelayToGetThrough());
    }

    @Test
    public void getAnswers() throws IOException {
        Day13.Answers answer = Day13.getSeverity(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day13"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answer.getNoDelaySeverity());
        LOGGER.info("Part 2: " + answer.getDelayToGetThrough());
    }
}
