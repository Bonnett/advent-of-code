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
public class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);
    private static final String EXAMPLE_INPUT_ONE = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
            """;

    private static final String EXAMPLE_INPUT_TWO = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(35, Day10.calculateJoltDifference(EXAMPLE_INPUT_ONE));
        assertEquals(220, Day10.calculateJoltDifference(EXAMPLE_INPUT_TWO));
    }


    @Test
    public void testExamplesPart2() {
        assertEquals(8, Day10.calculatePermutations(EXAMPLE_INPUT_ONE));
        assertEquals(19208, Day10.calculatePermutations(EXAMPLE_INPUT_TWO));
    }

    @Test
    public void getAnswers() throws IOException {
        final String adapters = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day10"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day10.calculateJoltDifference(adapters));
        LOGGER.info(String.format("Part 2: %.0f", Day10.calculatePermutations(adapters)));
    }

}
