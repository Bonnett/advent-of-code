package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    private static final String EXAMPLE_INPUT_ONE = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
            """;

    private static final String EXAMPLE_INPUT_TWO = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(165, Day14.sumMemoryValuesVersionOne(EXAMPLE_INPUT_ONE));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(208, Day14.sumMemoryValuesVersionTwo(EXAMPLE_INPUT_TWO));
    }

    @Test
    public void getAnswers() throws IOException {
        final String instructions = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day14"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day14.sumMemoryValuesVersionOne(instructions));
        LOGGER.info("Part 2: {}", Day14.sumMemoryValuesVersionTwo(instructions));
    }
}
