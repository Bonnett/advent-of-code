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

public class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList("[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-01 00:25] wakes up",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:55] wakes up",
            "[1518-11-01 23:58] Guard #99 begins shift",
            "[1518-11-02 00:40] falls asleep",
            "[1518-11-02 00:50] wakes up",
            "[1518-11-03 00:05] Guard #10 begins shift",
            "[1518-11-03 00:24] falls asleep",
            "[1518-11-03 00:29] wakes up",
            "[1518-11-04 00:02] Guard #99 begins shift",
            "[1518-11-04 00:36] falls asleep",
            "[1518-11-04 00:46] wakes up",
            "[1518-11-05 00:03] Guard #99 begins shift",
            "[1518-11-05 00:45] falls asleep",
            "[1518-11-05 00:55] wakes up");

    @Test
    public void testExamplesPart1() {
        assertEquals(240, Day04.getAnswers(TEST_INPUT).getStrategyOneAnswer());
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(4455, Day04.getAnswers(TEST_INPUT).getStrategyTwoAnswer());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day04.Answers answers = Day04.getAnswers(IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day04"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answers.getStrategyOneAnswer());
        LOGGER.info("Part 2: " + answers.getStrategyTwoAnswer());
    }
}
