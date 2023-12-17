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
public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    private static final String SAMPLE_INPUT = """
            .|...\\....
            |.-.\\.....
            .....|-...
            ........|.
            ..........
            .........\\
            ..../.\\\\..
            .-.-/..|..
            .|....-|.\\
            ..//.|....""";

    @Test
    public void testExamplesPart1() {
        assertEquals(46, Day16.calculateTotalEnergized(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(51, Day16.calculateMaximumEnergized(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> mirrorLayout = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day16")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day16.calculateTotalEnergized(mirrorLayout));
        LOGGER.info("Part 2: {}", Day16.calculateMaximumEnergized(mirrorLayout));
    }
}
