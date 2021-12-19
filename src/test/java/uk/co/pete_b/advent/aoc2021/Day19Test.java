package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day19Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19Test.class);

    @Test
    public void testExamplePart1() throws IOException {
        final List<String> scanners = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day19_example")), Charset.defaultCharset());
        assertEquals(new Day19.Answer(79, 3621), Day19.arrangeUniverse(scanners));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> scanners = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day19")), Charset.defaultCharset());

        LOGGER.info("{}", Day19.arrangeUniverse(scanners));
    }
}
