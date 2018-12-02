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

public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(12, Day02.calculateChecksum(Arrays.asList("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals("fgij", Day02.similarChars(Arrays.asList("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day02"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day02.calculateChecksum(input));
        LOGGER.info("Part 2: " + Day02.similarChars(input));
    }
}
