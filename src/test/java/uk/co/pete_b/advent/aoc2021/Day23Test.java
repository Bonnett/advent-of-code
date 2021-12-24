package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

    private static final String EXAMPLE_ONE = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(12521, Day23.calculateMinimumScore(Arrays.stream(EXAMPLE_ONE.split("\r?\n")).toList(), false));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(44169, Day23.calculateMinimumScore(Arrays.stream(EXAMPLE_ONE.split("\r?\n")).toList(), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> inputMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day23")), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day23.calculateMinimumScore(inputMap, false));
        LOGGER.info("Part 2: {}", Day23.calculateMinimumScore(inputMap, true));
    }
}
