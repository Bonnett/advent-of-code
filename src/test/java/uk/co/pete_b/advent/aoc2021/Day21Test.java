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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private static final String EXAMPLE_INPUT = """
            Player 1 starting position: 4
            Player 2 starting position: 8
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(739785, Day21.calculateScoreDeterministicDie(Arrays.stream(EXAMPLE_INPUT.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(444356092776315L, Day21.calculateScoreQuantumDie(Arrays.stream(EXAMPLE_INPUT.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> playerInput = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day21")), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day21.calculateScoreDeterministicDie(playerInput));
        LOGGER.info("Part 2: {}", Day21.calculateScoreQuantumDie(playerInput));
    }
}
