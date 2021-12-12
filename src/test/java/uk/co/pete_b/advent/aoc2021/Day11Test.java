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
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    private static final String EXAMPLE_OCTOPUSES = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """;

    @Test
    public void testExamples() {
        assertEquals(new Day11.Answer(1656, 195), Day11.flashOctopuses(Arrays.stream(EXAMPLE_OCTOPUSES.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> octopuses = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day11")), Charset.defaultCharset());

        LOGGER.info("{}", Day11.flashOctopuses(octopuses));
    }
}
