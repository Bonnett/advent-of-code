package uk.co.pete_b.advent.aoc2022;

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

@Tag("2022")
public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);
    public static final List<String> SAMPLE_GAME = Arrays.asList("A Y", "B X", "C Z");

    @Test
    public void testExamplesPart1() {
        assertEquals(15, Day02.getTotalScoreWithMoves(SAMPLE_GAME));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(12, Day02.getScoreWithResult(SAMPLE_GAME));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> moves = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day02")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day02.getTotalScoreWithMoves(moves));
        LOGGER.info("Part 2: {}", Day02.getScoreWithResult(moves));
    }
}
