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
public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    private static final String EXAMPLE_NOTES = """
                    ...#
                    .#..
                    #...
                    ....
            ...#.......#
            ........#...
            ..#....#....
            ..........#.
                    ...#....
                    .....#..
                    .#......
                    ......#.
                        
            10R5L5R10L4R5L5
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(6032, Day22.getFinalPassword(IOUtils.readLines(new StringReader(EXAMPLE_NOTES))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(5031, Day22.getFinalPasswordAsCube(IOUtils.readLines(new StringReader(EXAMPLE_NOTES)), 4));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> puzzleNotes = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day22")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day22.getFinalPassword(puzzleNotes));
        LOGGER.info("Part 2: {}", Day22.getFinalPasswordAsCube(puzzleNotes, 50));
    }
}
