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
public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    private static final String EXAMPLE_ONE = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(58, Day25.findStepWithNoMoves(Arrays.stream(EXAMPLE_ONE.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> cucumbers = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day25")), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day25.findStepWithNoMoves(cucumbers));
    }
}
