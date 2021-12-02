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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(7, Day01.calculateNumberOfIncreases(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(5, Day01.calculateNumberOfWindowIncreases(Arrays.asList(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> stringValues = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day01")), Charset.defaultCharset());
        final List<Integer> depthChanges = stringValues.stream().map(Integer::parseInt).collect(Collectors.toList());

        LOGGER.info("Part 1: {}", Day01.calculateNumberOfIncreases(depthChanges));
        LOGGER.info("Part 2: {}", Day01.calculateNumberOfWindowIncreases(depthChanges));
    }
}
