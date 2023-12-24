package uk.co.pete_b.advent.aoc2023;

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

@Tag("2023")
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

    private static final String SAMPLE_INPUT = """
            #.#####################
            #.......#########...###
            #######.#########.#.###
            ###.....#.>.>.###.#.###
            ###v#####.#v#.###.#.###
            ###.>...#.#.#.....#...#
            ###v###.#.#.#########.#
            ###...#.#.#.......#...#
            #####.#.#.#######.#.###
            #.....#.#.#.......#...#
            #.#####.#.#.#########v#
            #.#...#...#...###...>.#
            #.#.#v#######v###.###v#
            #...#.>.#...>.>.#.###.#
            #####v#.#.###v#.#.###.#
            #.....#...#...#.#.#...#
            #.#########.###.#.#.###
            #...###...#...#...#.###
            ###.###.#.###v#####v###
            #...#...#.#.>.>.#.>.###
            #.###.###.#.###.#.#v###
            #.....###...###...#...#
            #####################.#""";

    @Test
    public void testExamplesPart1() {
        assertEquals(94, Day23.findLongestPath(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), true));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(154, Day23.findLongestPath(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), false));
        assertEquals(154, Day23.findLongestPathIgnoringSlopes(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    // This test takes too long
    //@Test
    public void getAnswers() throws IOException {
        final List<String> gardenMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day23")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day23.findLongestPath(gardenMap, true));
        LOGGER.info("Part 2: {}", Day23.findLongestPathIgnoringSlopes(gardenMap));
    }
}
