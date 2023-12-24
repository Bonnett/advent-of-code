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
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    private static final String SAMPLE_INPUT = """
            19, 13, 30 @ -2,  1, -2
            18, 19, 22 @ -1, -1, -2
            20, 25, 34 @ -2, -2, -4
            12, 31, 28 @ -1, -2, -1
            20, 19, 15 @  1, -5, -3""";

    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day24.findTwoDimensionalIntersections(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), 7, 271));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(47, Day24.findBestInitialPosition(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> hailstones = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day24")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day24.findTwoDimensionalIntersections(hailstones, 200000000000000L, 400000000000000L));
        LOGGER.info("Part 2: {}", Day24.findBestInitialPosition(hailstones));
    }
}
