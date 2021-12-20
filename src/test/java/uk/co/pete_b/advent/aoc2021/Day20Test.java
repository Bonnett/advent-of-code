package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    @Test
    public void testExamples() throws IOException {
        final List<String> inputImage = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day20_example")), Charset.defaultCharset());
        assertEquals(35, Day20.enhanceImage(inputImage, 2));
        assertEquals(3351, Day20.enhanceImage(inputImage, 50));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> inputImage = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day20")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day20.enhanceImage(inputImage, 2));
        LOGGER.info("Part 2: {}", Day20.enhanceImage(inputImage, 50));
    }
}
