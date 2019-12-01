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
public class Day08Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    private static final String SAMPLE_INPUT = "b inc 5 if a > 1\n" +
            "a inc 1 if b < 5\n" +
            "c dec -10 if a >= 1\n" +
            "c inc -20 if c == 10";

    @Test
    public void testPart1() {
        assertEquals(1, Day08.getAnswers(SAMPLE_INPUT).getLargestRegister());
    }

    @Test
    public void testPart2() {
        assertEquals(10, Day08.getAnswers(SAMPLE_INPUT).getHighestRegister());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day08.Answers solution = Day08.getAnswers(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day08"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + solution.getLargestRegister());
        LOGGER.info("Part 2: " + solution.getHighestRegister());
    }
}
