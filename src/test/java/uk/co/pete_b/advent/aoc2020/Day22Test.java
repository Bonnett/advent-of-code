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
public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    private static final String EXAMPLE_INPUT = """
            Player 1:
            9
            2
            6
            3
            1
                        
            Player 2:
            5
            8
            4
            7
            10  
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(306, Day22.playCombat(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(291, Day22.playRecursiveCombat(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String deck = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day22"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day22.playCombat(deck));
        LOGGER.info("Part 2: {}", Day22.playRecursiveCombat(deck));
    }
}
