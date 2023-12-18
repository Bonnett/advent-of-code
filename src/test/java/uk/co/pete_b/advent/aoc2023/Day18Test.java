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
public class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)""";

    @Test
    public void testExamplesPart1() {
        assertEquals(62, Day18.calculateLavaCapacity(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(952408144115L, Day18.calculateLavaCapacityViaHex(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> lavaMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day18")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day18.calculateLavaCapacity(lavaMap));
        LOGGER.info("Part 2: {}", Day18.calculateLavaCapacityViaHex(lavaMap));
    }
}
