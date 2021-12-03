package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);

    private static final String EXAMPLE_MOVES = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(150, Day02.calculatePosition(Arrays.stream(EXAMPLE_MOVES.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(900, Day02.calculatePositionAndAim(Arrays.stream(EXAMPLE_MOVES.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> moves = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day02")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day02.calculatePosition(moves));
        LOGGER.info("Part 2: {}", Day02.calculatePositionAndAim(moves));
    }
}
