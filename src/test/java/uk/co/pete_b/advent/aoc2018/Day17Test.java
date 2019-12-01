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
public class Day17Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList("x=495, y=2..7",
            "y=7, x=495..501",
            "x=501, y=3..7",
            "x=498, y=2..4",
            "x=506, y=1..2",
            "x=498, y=10..13",
            "x=504, y=10..13",
            "y=13, x=498..504");
    private static final List<String> TEST_INPUT_1 = Arrays.asList("x=494, y=2..7",
            "y=7, x=494..500",
            "x=500, y=3..7",
            "x=497, y=2..4",
            "x=506, y=1..2",
            "x=498, y=10..13",
            "x=504, y=10..13",
            "y=13, x=498..504");

    @Test
    public void testExamples() {
        assertEquals(new Day17.Answer(57, 29), Day17.calculateWaterCoverage(TEST_INPUT));
        assertEquals(new Day17.Answer(57, 29), Day17.calculateWaterCoverage(TEST_INPUT_1));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day17"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day17.calculateWaterCoverage(input));
    }
}
