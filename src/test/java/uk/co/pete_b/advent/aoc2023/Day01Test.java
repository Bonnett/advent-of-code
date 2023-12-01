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
public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet""";

    private static final String SAMPLE_INPUT_TWO = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen""";

    @Test
    public void testExamplesPart1() {
        assertEquals(142, Day01.getCalibrationSum(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(281, Day01.getCalibrationSumFromWords(IOUtils.readLines(new StringReader(SAMPLE_INPUT_TWO))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> calibrationData = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day01")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day01.getCalibrationSum(calibrationData));
        LOGGER.info("Part 2: {}", Day01.getCalibrationSumFromWords(calibrationData));
    }
}
