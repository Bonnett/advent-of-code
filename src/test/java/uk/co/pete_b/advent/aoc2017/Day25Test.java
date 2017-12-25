package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    private static final String SAMPLE_INPUT = "Begin in state A.\n" +
            "Perform a diagnostic checksum after 6 steps.\n" +
            "\n" +
            "In state A:\n" +
            "  If the current value is 0:\n" +
            "    - Write the value 1.\n" +
            "    - Move one slot to the right.\n" +
            "    - Continue with state B.\n" +
            "  If the current value is 1:\n" +
            "    - Write the value 0.\n" +
            "    - Move one slot to the left.\n" +
            "    - Continue with state B.\n" +
            "\n" +
            "In state B:\n" +
            "  If the current value is 0:\n" +
            "    - Write the value 1.\n" +
            "    - Move one slot to the left.\n" +
            "    - Continue with state A.\n" +
            "  If the current value is 1:\n" +
            "    - Write the value 1.\n" +
            "    - Move one slot to the right.\n" +
            "    - Continue with state A.";

    @Test
    public void testExamplesPart1() {
        assertEquals(3, Day25.getChecksum(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day25"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day25.getChecksum(puzzleInput));
    }
}
