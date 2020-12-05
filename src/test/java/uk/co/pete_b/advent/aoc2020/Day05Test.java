package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);


    @Test
    public void testExamplesPart1() {
        assertEquals(357, Day05.getSeatId("FBFBBFFRLR"));
        assertEquals(567, Day05.getSeatId("BFFFBBFRRR"));
        assertEquals(119, Day05.getSeatId("FFFBBBFRRR"));
        assertEquals(820, Day05.getSeatId("BBFFBBFRLL"));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> seats = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2020/day05"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day05.getHighestSeatId(seats));
        LOGGER.info("Part 2: {}", Day05.findYourSeatId(seats));
    }
}
