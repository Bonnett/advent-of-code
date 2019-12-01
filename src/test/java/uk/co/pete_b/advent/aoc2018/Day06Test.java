package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    @Test
    public void testExamples() {
        Day06.Answers answers = Day06.getAnswers(Arrays.asList("1, 1", "1, 6", "8, 3", "3, 4", "5, 5", "8, 9"), 32);
        assertEquals(17, answers.getLargestNonInfiniteArea());
        assertEquals(16, answers.getNumberOfSafeCells());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day06.Answers answers = Day06.getAnswers(IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day06"), Charset.defaultCharset()), 10000);
        LOGGER.info("Part 1: " + answers.getLargestNonInfiniteArea());
        LOGGER.info("Part 1: " + answers.getNumberOfSafeCells());
    }
}
