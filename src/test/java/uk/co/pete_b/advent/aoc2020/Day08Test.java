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
public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    private static final String EXAMPLE_INPUT = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(5, Day08.findAccumulatorValue(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(8, Day08.fixAndFindAccumulatorValue(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String instructions = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day08"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day08.findAccumulatorValue(instructions));
        LOGGER.info("Part 2: {}", Day08.fixAndFindAccumulatorValue(instructions));
    }
}
