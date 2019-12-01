package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList("Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin.");

    @Test
    public void testExamplesPart1() {
        assertEquals("CABDFE", Day07.getPackageOrder(TEST_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(15, Day07.getCompletionTime(TEST_INPUT, 0, 2));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day07"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day07.getPackageOrder(input));
        LOGGER.info("Part 2: " + Day07.getCompletionTime(input, 60, 5));
    }
}
