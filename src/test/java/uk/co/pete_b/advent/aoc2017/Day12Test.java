package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day12Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static final String SAMPLE_INPUT = "0 <-> 2\n" +
            "1 <-> 1\n" +
            "2 <-> 0, 3, 4\n" +
            "3 <-> 2, 4\n" +
            "4 <-> 2, 3, 6\n" +
            "5 <-> 6\n" +
            "6 <-> 4, 5";

    @Test
    public void testPart1() {
        Assert.assertEquals(6, Day12.getAnswers(SAMPLE_INPUT).getZeroProgramCount());
    }

    @Test
    public void testPart2() {
        Assert.assertEquals(2, Day12.getAnswers(SAMPLE_INPUT).getNumberOfGroups());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day12.Answers answers = Day12.getAnswers(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day12"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answers.getZeroProgramCount());
        LOGGER.info("Part 2: " + answers.getNumberOfGroups());
    }

}
