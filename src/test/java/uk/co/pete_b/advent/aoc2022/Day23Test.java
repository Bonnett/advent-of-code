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
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

    private static final String EXAMPLE_ELF_LOCATIONS = """
            ....#..
            ..###.#
            #...#.#
            .#...##
            #.###..
            ##.#.##
            .#..#..
            """;

    @Test
    public void testExamples() throws IOException {
        assertEquals(new Day23.Answer(110, 20), Day23.calculateMinimumEmptyGroundTiles(IOUtils.readLines(new StringReader(EXAMPLE_ELF_LOCATIONS))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> elfLocations = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day23")), Charset.defaultCharset());

        Day23.Answer answer = Day23.calculateMinimumEmptyGroundTiles(elfLocations);
        LOGGER.info("Part 1: {}", answer.gridSizeRound10());
        LOGGER.info("Part 2: {}", answer.roundWithNoMoves());
    }
}
