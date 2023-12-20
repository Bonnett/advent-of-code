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
public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            broadcaster -> a, b, c
            %a -> b
            %b -> c
            %c -> inv
            &inv -> a""";
    private static final String SAMPLE_INPUT_TWO = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output""";

    @Test
    public void testExamplesPart1() {
        assertEquals(32000000L, Day20.calculatePlusProduct(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
        assertEquals(11687500L, Day20.calculatePlusProduct(IOUtils.readLines(new StringReader(SAMPLE_INPUT_TWO))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> components = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day20")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day20.calculatePlusProduct(components));
        LOGGER.info("Part 2: {}", Day20.calculatePlusesTillRxTurnsOn(components));
    }
}
