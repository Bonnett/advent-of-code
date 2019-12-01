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
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    private static final String SAMPLE_INPUT = "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10";

    @Test
    public void testExamplesPart1() {
        assertEquals(31, Day24.getBridgeFacts(SAMPLE_INPUT).getStrongestBridge());
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(19, Day24.getBridgeFacts(SAMPLE_INPUT).getLongestBridgeStrength());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day24.Answers answers = Day24.getBridgeFacts(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day24"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answers.getStrongestBridge());
        LOGGER.info("Part 1: " + answers.getLongestBridgeStrength());
    }
}
