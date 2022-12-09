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
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    private static final String ROPE_MOVES_EXAMPLE_ONE = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
            """;

    private static final String ROPE_MOVES_EXAMPLE_TWO = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(13, Day09.countTailPositions(IOUtils.readLines(new StringReader(ROPE_MOVES_EXAMPLE_ONE)), 2));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(36, Day09.countTailPositions(IOUtils.readLines(new StringReader(ROPE_MOVES_EXAMPLE_TWO)), 10));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> ropeMoves = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day09")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day09.countTailPositions(ropeMoves, 2));
        LOGGER.info("Part 2: {}", Day09.countTailPositions(ropeMoves, 10));
    }
}
