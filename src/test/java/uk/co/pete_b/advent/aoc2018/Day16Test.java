package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList("Before: [3, 2, 1, 1]",
            "9 2 1 2",
            "After:  [3, 2, 2, 1]");

    @Test
    public void testExamplesPart1() {
        assertEquals(1, Day16.getSolution(TEST_INPUT, true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day16"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day16.getSolution(input, true));
        LOGGER.info("Part 2: " + Day16.getSolution(input, false));
    }
}
