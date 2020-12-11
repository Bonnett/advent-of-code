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
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    private static final String EXAMPLE_INPUT = """
            L.LL.LL.LL
            LLLLLLL.LL
            L.L.L..L..
            LLLL.LL.LL
            L.LL.LL.LL
            L.LLLLL.LL
            ..L.L.....
            LLLLLLLLLL
            L.LLLLLL.L
            L.LLLLL.LL
            """;
    @Test
    public void testExamplesPart1() {
        assertEquals(37, Day11.calculateEmptySeats(EXAMPLE_INPUT, true));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(26, Day11.calculateEmptySeats(EXAMPLE_INPUT, false));
    }

    @Test
    public void getAnswers() throws IOException {
        final String seats = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day11"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day11.calculateEmptySeats(seats, true));
        LOGGER.info("Part 2: {}", Day11.calculateEmptySeats(seats, false));
    }

}
