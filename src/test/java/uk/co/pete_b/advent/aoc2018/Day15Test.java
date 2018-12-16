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

public class Day15Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    private static final List<String> TEST_MAP_1 = Arrays.asList("#######",
            "#.G...#",
            "#...EG#",
            "#.#.#G#",
            "#..G#E#",
            "#.....#",
            "#######");

    private static final List<String> TEST_MAP_2 = Arrays.asList("#######",
            "#G..#E#",
            "#E#E.E#",
            "#G.##.#",
            "#...#E#",
            "#...E.#",
            "#######");

    private static final List<String> TEST_MAP_3 = Arrays.asList("#######",
            "#E..EG#",
            "#.#G.E#",
            "#E.##E#",
            "#G..#.#",
            "#..E#.#",
            "#######");

    private static final List<String> TEST_MAP_4 = Arrays.asList("#######",
            "#E.G#.#",
            "#.#G..#",
            "#G.#.G#",
            "#G..#.#",
            "#...E.#",
            "#######");

    private static final List<String> TEST_MAP_5 = Arrays.asList("#######",
            "#.E...#",
            "#.#..G#",
            "#.###.#",
            "#E#G#G#",
            "#...#G#",
            "#######");

    private static final List<String> TEST_MAP_6 = Arrays.asList("#########",
            "#G......#",
            "#.E.#...#",
            "#..##..G#",
            "#...##..#",
            "#...#...#",
            "#.G...G.#",
            "#.....G.#",
            "#########");

    @Test
    public void testExamplesPart1() {
        assertEquals(27730, Day15.getOutcomeWithDeaths(TEST_MAP_1));
        assertEquals(36334, Day15.getOutcomeWithDeaths(TEST_MAP_2));
        assertEquals(39514, Day15.getOutcomeWithDeaths(TEST_MAP_3));
        assertEquals(27755, Day15.getOutcomeWithDeaths(TEST_MAP_4));
        assertEquals(28944, Day15.getOutcomeWithDeaths(TEST_MAP_5));
        assertEquals(18740, Day15.getOutcomeWithDeaths(TEST_MAP_6));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(new Day15.Answer(15, 4988), Day15.getAttackPowerNoDeaths(TEST_MAP_1));
        assertEquals(new Day15.Answer(4, 29064), Day15.getAttackPowerNoDeaths(TEST_MAP_2));
        assertEquals(new Day15.Answer(4, 31284), Day15.getAttackPowerNoDeaths(TEST_MAP_3));
        assertEquals(new Day15.Answer(15, 3478), Day15.getAttackPowerNoDeaths(TEST_MAP_4));
        assertEquals(new Day15.Answer(12, 6474), Day15.getAttackPowerNoDeaths(TEST_MAP_5));
        assertEquals(new Day15.Answer(34, 1140), Day15.getAttackPowerNoDeaths(TEST_MAP_6));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day15"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day15.getOutcomeWithDeaths(input));
        LOGGER.info("Part 2: " + Day15.getAttackPowerNoDeaths(input));
    }
}
