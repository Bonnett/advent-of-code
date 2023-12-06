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
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    private static final String SAMPLE_INPUT = """
            Time:      7  15   30
            Distance:  9  40  200""";

    @Test
    public void testExamplesPart1() {
        assertEquals(288, Day06.findProductOfWinningRaces(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(71503, Day06.findProductOfWinningAsOneRace(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> raceDetails = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day06")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day06.findProductOfWinningRaces(raceDetails));
        LOGGER.info("Part 2: {}", Day06.findProductOfWinningAsOneRace(raceDetails));
    }
}
