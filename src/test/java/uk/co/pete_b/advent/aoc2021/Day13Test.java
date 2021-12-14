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
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    private static final String EXAMPLE_INSTRUCTIONS = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
                        
            fold along y=7
            fold along x=5
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(17, Day13.calculateNumberOfDots(Arrays.stream(EXAMPLE_INSTRUCTIONS.split("\r?\n")).toList(), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> instructions = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day13")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day13.calculateNumberOfDots(instructions, true));
        LOGGER.info("Part 2: ");
        Day13.calculateNumberOfDots(instructions, false);
    }

    @Test
    public void runRR() throws IOException {
        final List<String> instructions = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day13_rr")), Charset.defaultCharset());

        Day13.calculateNumberOfDots(instructions, false);
    }
}
