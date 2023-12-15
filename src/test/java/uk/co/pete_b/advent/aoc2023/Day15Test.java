package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2023")
public class Day15Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15Test.class);

    private static final String SAMPLE_INPUT = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

    @Test
    public void testExamplesPart1() {
        assertEquals(52, Day15.sumHASHAlgorithm("HASH"));
        assertEquals(1320, Day15.sumHASHAlgorithm(SAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(145, Day15.runHASHMAPAlgorithm(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String input = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day15")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day15.sumHASHAlgorithm(input.trim()));
        LOGGER.info("Part 2: {}", Day15.runHASHMAPAlgorithm(input));
    }
}
