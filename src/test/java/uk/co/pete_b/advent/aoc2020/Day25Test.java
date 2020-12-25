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
public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    private static final String EXAMPLE_INPUT = """
            5764801
            17807724
            """;

    @Test
    public void testExamples() {
        assertEquals(14897079, Day25.findEncryptionKey(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String keys = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day25"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day25.findEncryptionKey(keys));
    }
}
