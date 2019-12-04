package uk.co.pete_b.advent.aoc2019;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("2019")
public class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);

    @Test
    public void testExamplesPart1() {
        assertTrue(Day04.isNumberValid(111111));
        assertFalse(Day04.isNumberValid(223450));
        assertFalse(Day04.isNumberValid(123789));
    }

    @Test
    public void testExamplesPart2() {
        assertFalse(Day04.isNumberValidWithAdditionalCheck(111111));
        assertFalse(Day04.isNumberValidWithAdditionalCheck(223450));
        assertFalse(Day04.isNumberValidWithAdditionalCheck(123789));
        assertTrue(Day04.isNumberValidWithAdditionalCheck(112233));
        assertFalse(Day04.isNumberValidWithAdditionalCheck(123444));
        assertTrue(Day04.isNumberValidWithAdditionalCheck(111122));
    }

    @Test
    public void getAnswers() {
        final int start = 128392;
        final int end = 643281;
        int numberValidPartOne = 0;
        int numberValidPartTwo = 0;
        for (int i = start; i <= end; i++) {
            numberValidPartOne += Day04.isNumberValid(i) ? 1 : 0;
            numberValidPartTwo += Day04.isNumberValidWithAdditionalCheck(i) ? 1 : 0;
        }
        LOGGER.info("Part 1: {}", numberValidPartOne);
        LOGGER.info("Part 2: {}", numberValidPartTwo);
    }

}
