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
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    private static final String SAMPLE_INPUT = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
                        
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#""";

    private static final String ADDITIONAL_INPUT = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
                        
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
                        
            .#.##.#.#
            .##..##..
            .#.##.#..
            #......##
            #......##
            .#.##.#..
            .##..##.#
                        
            #..#....#
            ###..##..
            .##.#####
            .##.#####
            ###..##..
            #..#....#
            #..##...#""";

    @Test
    public void testExamplesPart1() {
        assertEquals(405, Day13.sumOfReflections(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
        assertEquals(709, Day13.sumOfReflections(IOUtils.readLines(new StringReader(ADDITIONAL_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(400, Day13.sumOfReflectionsWithSmudge(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
        assertEquals(1400, Day13.sumOfReflectionsWithSmudge(IOUtils.readLines(new StringReader(ADDITIONAL_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> lavaMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day13")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day13.sumOfReflections(lavaMap));
        LOGGER.info("Part 2: {}", Day13.sumOfReflectionsWithSmudge(lavaMap));
    }
}
