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
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final String SAMPLE_INPUT = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""";

    @Test
    public void testExamplesPart1() {
        assertEquals(6440, Day07.calculateTotalWinnings(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(5905, Day07.calculateTotalWinningsWithJoker(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> camelCards = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day07")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day07.calculateTotalWinnings(camelCards));
        LOGGER.info("Part 2: {}", Day07.calculateTotalWinningsWithJoker(camelCards));
    }
}
