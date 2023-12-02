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
public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);

    private static final String SAMPLE_INPUT = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""";

    @Test
    public void testExamplesPart1() {
        assertEquals(8, Day02.sumValidGames(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2286, Day02.calculatePowerSum(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> gameDetails = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day02")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day02.sumValidGames(gameDetails));
        LOGGER.info("Part 2: {}", Day02.calculatePowerSum(gameDetails));
    }
}
