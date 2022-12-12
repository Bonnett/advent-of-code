package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static final String HEIGHT_MAP = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(31, Day12.findFewestSteps(IOUtils.readLines(new StringReader(HEIGHT_MAP))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(29, Day12.findFewestStepsWithAllStartingPositions(IOUtils.readLines(new StringReader(HEIGHT_MAP))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> heightMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day12")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day12.findFewestSteps(heightMap));
        LOGGER.info("Part 2: {}", Day12.findFewestStepsWithAllStartingPositions(heightMap));
    }
}
