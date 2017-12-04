package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day02Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);

    @Test
    public void checkExamplesPart1() {
        Assert.assertEquals(8, Day02.calculateChecksumPart1("5 1 9 5"));
        Assert.assertEquals(4, Day02.calculateChecksumPart1("7 5 3"));
        Assert.assertEquals(6, Day02.calculateChecksumPart1("2 4 6 8"));
        Assert.assertEquals(18, Day02.calculateChecksumPart1("5 1 9 5\n7 5 3\n2 4 6 8"));
    }

    @Test
    public void checkExamplesPart2() {
        Assert.assertEquals(4, Day02.calculateChecksumPart2("5 9 2 8"));
        Assert.assertEquals(3, Day02.calculateChecksumPart2("9 4 7 3"));
        Assert.assertEquals(2, Day02.calculateChecksumPart2("3 8 6 5"));
        Assert.assertEquals(9, Day02.calculateChecksumPart2("5 9 2 8\n9 4 7 3\n3 8 6 5"));
    }

    @Test
    public void getAnswers() throws IOException {
        LOGGER.info("Part 1: " + Day02.calculateChecksumPart1(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day02"), Charset.defaultCharset())));
        LOGGER.info("Part 2: " + Day02.calculateChecksumPart2(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day02"), Charset.defaultCharset())));
    }
}
