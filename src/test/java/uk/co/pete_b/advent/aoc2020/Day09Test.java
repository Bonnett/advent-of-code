package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);
    private static final String EXAMPLE_INPUT = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(127, Day09.getFirstInvalidSum(EXAMPLE_INPUT, 5));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(62, Day09.findEncryptionWeakness(EXAMPLE_INPUT, 5));
    }

    @Test
    public void getAnswers() throws IOException {
        final String sequence = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day09"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day09.getFirstInvalidSum(sequence, 25));
        LOGGER.info("Part 2: {}", Day09.findEncryptionWeakness(sequence, 25));
    }

}
