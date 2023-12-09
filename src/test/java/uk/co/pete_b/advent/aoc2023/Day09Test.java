package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2023")
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    private static final String SAMPLE_INPUT = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45""";

    @Test
    public void testExamples() {
        assertEquals(new Day09.Answer(2, 114), Day09.sumOfExtrapolatedValues(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> series = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day09")), Charset.defaultCharset());

        final Day09.Answer answer = Day09.sumOfExtrapolatedValues(series);
        LOGGER.info("Part 1: {}", answer.rightmostSum());
        LOGGER.info("Part 2: {}", answer.leftmostSum());
    }
}
