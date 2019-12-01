package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    @Test
    public void testPart1() {
        assertEquals(5, Day05.findNoOfStepsPart1("0\n3\n0\n1\n-3"));
    }

    @Test
    public void testPart2() {
        assertEquals(10, Day05.findNoOfStepsPart2("0\n3\n0\n1\n-3"));
    }

    @Test
    public void getAnswer() throws IOException {
        final InputStream offsetStream = getClass().getResourceAsStream("/puzzle-data/2017/day05");
        final String offsets = IOUtils.toString(offsetStream, "UTF-8");

        LOGGER.info("Part 1: " + Day05.findNoOfStepsPart1(offsets));
        LOGGER.info("Part 2: " + Day05.findNoOfStepsPart2(offsets));
    }
}
