package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class Day03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(0, Day03.getAnswers(Collections.singletonList("#123 @ 3,2: 5x4")).getNumberOfDuplicates());
        assertEquals(4, Day03.getAnswers(Arrays.asList("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")).getNumberOfDuplicates());
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(123, Day03.getAnswers(Collections.singletonList("#123 @ 3,2: 5x4")).getIdOfNonOverlapping());
        assertEquals(3, Day03.getAnswers(Arrays.asList("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")).getIdOfNonOverlapping());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day03.Answers answers = Day03.getAnswers(IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day03"), Charset.defaultCharset()));
        LOGGER.info("Part 1: " + answers.getNumberOfDuplicates());
        LOGGER.info("Part 2: " + answers.getIdOfNonOverlapping());
    }

}
