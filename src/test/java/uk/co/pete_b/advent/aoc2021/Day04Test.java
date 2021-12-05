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
public class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);

    private static final String EXAMPLE_BINGO_CARDS = """
            7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
                        
            22 13 17 11  0
             8  2 23  4 24
            21  9 14 16  7
             6 10  3 18  5
             1 12 20 15 19
                        
             3 15  0  2 22
             9 18 13 17  5
            19  8  7 25 23
            20 11 10 24  4
            14 21 16 12  6
                        
            14 21 17 24  4
            10 16 15  9 19
            18  8 23 26 20
            22 11 13  6  5
             2  0 12  3  7
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(4512, Day04.calculateFinalScore(Arrays.stream(EXAMPLE_BINGO_CARDS.split("\r?\n")).toList(), true));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1924, Day04.calculateFinalScore(Arrays.stream(EXAMPLE_BINGO_CARDS.split("\r?\n")).toList(), false));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> bingo = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day04")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day04.calculateFinalScore(bingo, true));
        LOGGER.info("Part 2: {}", Day04.calculateFinalScore(bingo, false));
    }
}
