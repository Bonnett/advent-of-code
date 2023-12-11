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
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    private static final String SAMPLE_INPUT = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....""";

    @Test
    public void testExamplesPart1() {
        assertEquals(374, Day11.calculateDistances(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 2));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1030, Day11.calculateDistances(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 10));
        assertEquals(8410, Day11.calculateDistances(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 100));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> spaceMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day11")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day11.calculateDistances(spaceMap, 2));
        LOGGER.info("Part 2: {}", Day11.calculateDistances(spaceMap, 1000000));
    }
}
