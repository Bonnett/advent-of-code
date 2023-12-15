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
public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    private static final String SAMPLE_INPUT = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....""";

    @Test
    public void testExamplesPart1() {
        assertEquals(136, Day14.calculateTotalLoad(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(64, Day14.calculateLoadInCycles(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> rockLayout = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day14")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day14.calculateTotalLoad(rockLayout));
        LOGGER.info("Part 2: {}", Day14.calculateLoadInCycles(rockLayout));
    }
}
