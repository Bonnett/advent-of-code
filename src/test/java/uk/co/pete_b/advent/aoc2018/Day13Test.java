package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    @Test
    public void testExamplesPart1() {
        final List<String> input = Arrays.asList("/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/   ");
        assertEquals(new Coordinate(7, 3), Day13.getFirstCrashCoordinates(input));
    }

    @Test
    public void testExamplesPart2() {
        final List<String> input = Arrays.asList("/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/");

        assertEquals(new Coordinate(6, 4), Day13.getLastRemainingCartCoordinates(input));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day13"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day13.getFirstCrashCoordinates(input));
        LOGGER.info("Part 2: " + Day13.getLastRemainingCartCoordinates(input));
    }
}
