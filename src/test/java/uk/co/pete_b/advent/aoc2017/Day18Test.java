package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);

    private static final String SAMPLE_INPUT = "set a 1\n" +
            "add a 2\n" +
            "mul a a\n" +
            "mod a 5\n" +
            "snd a\n" +
            "set a 0\n" +
            "rcv a\n" +
            "jgz a -1\n" +
            "set a 1\n" +
            "jgz a -2";

    @Test
    public void testExamplesPart1() {
        assertEquals(4, Day18.recoverFrequencyPart1(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day18"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day18.recoverFrequencyPart1(puzzleInput));
        LOGGER.info("Part 2: " + Day18.recoverFrequencyPart2(puzzleInput));
    }
}
