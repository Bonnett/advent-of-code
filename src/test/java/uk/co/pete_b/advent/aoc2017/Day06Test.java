package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    @Test
    public void testPart1() {
        assertEquals(5, Day06.numberTillInfiniteLoop("0\t2\t7\t0"));
    }

    @Test
    public void testPart2() {
        assertEquals(4, Day06.cyclesFromInfiniteLoop("0\t2\t7\t0"));
    }

    @Test
    public void getAnswer() throws IOException {
        final InputStream blockStream = getClass().getResourceAsStream("/puzzle-data/2017/day06");
        final String blocks = IOUtils.toString(blockStream, "UTF-8");

        LOGGER.info("Part 1: " + Day06.numberTillInfiniteLoop(blocks));
        LOGGER.info("Part 2: " + Day06.cyclesFromInfiniteLoop(blocks));
    }
}
