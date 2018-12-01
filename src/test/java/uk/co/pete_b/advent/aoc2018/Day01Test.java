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

public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(3, Day01.calculateFrequency(Arrays.asList("+1", "-2", "+3", "+1")));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2, Day01.firstRepeatFrequency(Arrays.asList("+1", "-2", "+3", "+1")));
        assertEquals(0, Day01.firstRepeatFrequency(Arrays.asList("+1", "-1")));
        assertEquals(10, Day01.firstRepeatFrequency(Arrays.asList("+3", "+3", "+4", "-2", "-4")));
        assertEquals(5, Day01.firstRepeatFrequency(Arrays.asList("-6", "+3", "+8", "+5", "-6")));
        assertEquals(14, Day01.firstRepeatFrequency(Arrays.asList("+7", "+7", "-2", "-7", "-4")));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day01"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day01.calculateFrequency(input));
        LOGGER.info("Part 2: " + Day01.firstRepeatFrequency(input));
    }
}
