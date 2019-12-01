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
public class Day19Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19Test.class);

    private static final String SAMPLE_INPUT = "     |          \n" +
            "     |  +--+    \n" +
            "     A  |  C    \n" +
            " F---|----E|--+ \n" +
            "     |  |  |  D \n" +
            "     +B-+  +--+ \n";

    @Test
    public void testExamplesPart1() {
        assertEquals("ABCDEF", Day19.navigatePath(SAMPLE_INPUT).getPathNavigated());
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(38, Day19.navigatePath(SAMPLE_INPUT).getNumberOfSteps());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day19.Answers answer = Day19.navigatePath(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day19"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answer.getPathNavigated());
        LOGGER.info("Part 2: " + answer.getNumberOfSteps());
    }
}
