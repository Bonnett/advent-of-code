package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day09Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    @Test
    public void testPart1() {
        Assert.assertEquals(1, Day09.solvePuzzle("{<>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<1245124124124>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<<<<>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<{!>}>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<!!>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<!!!>>}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<{o\"i!a,<{i<a>}").getScore());

        Assert.assertEquals(1, Day09.solvePuzzle("{}").getScore());
        Assert.assertEquals(6, Day09.solvePuzzle("{{{}}}").getScore());
        Assert.assertEquals(5, Day09.solvePuzzle("{{},{}}").getScore());
        Assert.assertEquals(16, Day09.solvePuzzle("{{{},{},{{}}}}").getScore());
        Assert.assertEquals(1, Day09.solvePuzzle("{<a>,<a>,<a>,<a>}").getScore());
        Assert.assertEquals(9, Day09.solvePuzzle("{{<ab>},{<ab>},{<ab>},{<ab>}}").getScore());
        Assert.assertEquals(9, Day09.solvePuzzle("{{<!!>},{<!!>},{<!!>},{<!!>}}").getScore());
        Assert.assertEquals(3, Day09.solvePuzzle("{{<a!>},{<a!>},{<a!>},{<ab>}}").getScore());
    }

    @Test
    public void testPart2() {
        Assert.assertEquals(0, Day09.solvePuzzle("{<>}").getNonCancelled());
        Assert.assertEquals(17, Day09.solvePuzzle("{<random characters>}").getNonCancelled());
        Assert.assertEquals(3, Day09.solvePuzzle("{<<<<>}").getNonCancelled());
        Assert.assertEquals(2, Day09.solvePuzzle("{<{!>}>}").getNonCancelled());
        Assert.assertEquals(0, Day09.solvePuzzle("{<!!>}").getNonCancelled());
        Assert.assertEquals(10, Day09.solvePuzzle("{<{o\"i!a,<{i<a>}").getNonCancelled());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day09.Answers solution = Day09.solvePuzzle(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day09"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + solution.getScore());
        LOGGER.info("Part 2: " + solution.getNonCancelled());
    }

}
