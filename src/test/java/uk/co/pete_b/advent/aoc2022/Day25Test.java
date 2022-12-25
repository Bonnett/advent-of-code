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
public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    private static final String EXAMPLE_SNAFU_NUMBERS = """
            1=-0-2
            12111
            2=0=
            21
            2=01
            111
            20012
            112
            1=-1=
            1-12
            12
            1=
            122
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals("2=-1=0", Day25.getSNAFUNumber(IOUtils.readLines(new StringReader(EXAMPLE_SNAFU_NUMBERS))));
    }
    @Test
    public void getAnswers() throws IOException {
        final List<String> snafuNumbers = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day25")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day25.getSNAFUNumber(snafuNumbers));
    }
}
