package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day17Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    @Test
    public void testExample() {
        assertEquals(new Day17.Answer(45, 112), Day17.calculateHighestElevation("target area: x=20..30, y=-10..-5"));
    }

    @Test
    public void getAnswers() throws IOException {
        final String inputRules = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day17")), Charset.defaultCharset()).trim();

        LOGGER.info("{}", Day17.calculateHighestElevation(inputRules));
    }
}
