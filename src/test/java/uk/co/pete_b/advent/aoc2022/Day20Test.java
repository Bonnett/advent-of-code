package uk.co.pete_b.advent.aoc2022;

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

@Tag("2022")
public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    private static final String EXAMPLE_ENCRYPTED_FILE = """
            1
            2
            -3
            3
            -2
            0
            4
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(3L, Day20.sumGroveCoordinates(IOUtils.readLines(new StringReader(EXAMPLE_ENCRYPTED_FILE)), 1, 1));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(1623178306L, Day20.sumGroveCoordinates(IOUtils.readLines(new StringReader(EXAMPLE_ENCRYPTED_FILE)), 811589153L, 10));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> numbers = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day20")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day20.sumGroveCoordinates(numbers, 1, 1));
        LOGGER.info("Part 2: {}", Day20.sumGroveCoordinates(numbers, 811589153L, 10));
    }
}
