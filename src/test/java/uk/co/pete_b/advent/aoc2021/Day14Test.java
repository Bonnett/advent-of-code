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
public class Day14Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14Test.class);

    private static final String EXAMPLE_POLYMER_RULES = """
            NNCB
                        
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(1588, Day14.calculateMostMinusLeastCommon(Arrays.stream(EXAMPLE_POLYMER_RULES.split("\r?\n")).toList(), 10));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2188189693529L, Day14.calculateMostMinusLeastCommon(Arrays.stream(EXAMPLE_POLYMER_RULES.split("\r?\n")).toList(), 40));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> polymerRules = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day14")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day14.calculateMostMinusLeastCommon(polymerRules, 10));
        LOGGER.info("Part 2: {}", Day14.calculateMostMinusLeastCommon(polymerRules, 40));
    }
}
