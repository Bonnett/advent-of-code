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
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);
    public static final String GAME_INSTRUCTIONS = """
                [D]   \s
            [N] [C]   \s
            [Z] [M] [P]
             1   2   3\s
                        
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals("CMZ", Day05.getStackTops(IOUtils.readLines(new StringReader(GAME_INSTRUCTIONS)), false));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals("MCD", Day05.getStackTops(IOUtils.readLines(new StringReader(GAME_INSTRUCTIONS)), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day05")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day05.getStackTops(pairs, false));
        LOGGER.info("Part 2: {}", Day05.getStackTops(pairs, true));
    }
}
