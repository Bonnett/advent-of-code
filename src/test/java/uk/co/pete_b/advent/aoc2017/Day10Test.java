package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day10Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    @Test
    public void testPart1() {
        Assert.assertEquals(12, Day10.calculateHashNonAscii(5, "3,4,1,5"));
    }

    @Test
    public void testPart2() {
        Assert.assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", Day10.calculateKnotHash("1,2,3"));
        Assert.assertEquals("a2582a3a0e66e6e86e3812dcb672a272", Day10.calculateKnotHash(""));
        Assert.assertEquals("33efeb34ea91902bb2f59c9920caa6cd", Day10.calculateKnotHash("AoC 2017"));
        Assert.assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", Day10.calculateKnotHash("1,2,4"));

    }

    @Test
    public void getAnswers() throws IOException {
        final String inputString = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day10"), Charset.defaultCharset()).trim();
        LOGGER.info("Part 1: " + Day10.calculateHashNonAscii(256, inputString));
        LOGGER.info("Part 2: " + Day10.calculateKnotHash(inputString));
    }
}
