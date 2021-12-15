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
public class Day15Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    private static final String EXAMPLE_CAVERN = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(40, Day15.calculateLowestTotalRisk(Arrays.stream(EXAMPLE_CAVERN.split("\r?\n")).toList(), false));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(315, Day15.calculateLowestTotalRisk(Arrays.stream(EXAMPLE_CAVERN.split("\r?\n")).toList(), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> cave = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day15")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day15.calculateLowestTotalRisk(cave, false));
        LOGGER.info("Part 2: {}", Day15.calculateLowestTotalRisk(cave, true));
    }
}
