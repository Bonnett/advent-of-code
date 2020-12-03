package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day03Test {
    private static final String EXAMPLE_INPUT = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
            """;
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);


    @Test
    public void testExamplesPart1() {
        assertEquals(7, Day03.calculateTreesHit(EXAMPLE_INPUT, Pair.of(3, 1)));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(336, Day03.calculateTreesHit(EXAMPLE_INPUT, Pair.of(1, 1), Pair.of(3, 1), Pair.of(5, 1), Pair.of(7, 1), Pair.of(1, 2)));
    }

    @Test
    public void getAnswers() throws IOException {
        final String map = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day03"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day03.calculateTreesHit(map, Pair.of(3, 1)));
        LOGGER.info("Part 2: {}", Day03.calculateTreesHit(map, Pair.of(1, 1), Pair.of(3, 1), Pair.of(5, 1), Pair.of(7, 1), Pair.of(1, 2)));
    }
}
