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
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    private static final String EXAMPLE_INPUT = """
            939
            7,13,x,x,59,x,31,19
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(295, Day13.findEarliestBus(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1068781, Day13.findContiguousBusInefficiently(EXAMPLE_INPUT, 1000000L));
        assertEquals(3417, Day13.findContiguousBusInefficiently("17,x,13,19", 3000L));
        assertEquals(754018, Day13.findContiguousBusInefficiently("67,7,59,61", 700000L));
        assertEquals(779210, Day13.findContiguousBusInefficiently("67,x,7,59,61", 700000L));
        assertEquals(1261476, Day13.findContiguousBusInefficiently("67,7,x,59,61", 1000000L));
        assertEquals(1202161486, Day13.findContiguousBusInefficiently("1789,37,47,1889", 1000000000L));

        assertEquals(1068781, Day13.findContiguousBusEfficiently(EXAMPLE_INPUT));
        assertEquals(3417, Day13.findContiguousBusEfficiently("17,x,13,19"));
        assertEquals(754018, Day13.findContiguousBusEfficiently("67,7,59,61"));
        assertEquals(779210, Day13.findContiguousBusEfficiently("67,x,7,59,61"));
        assertEquals(1261476, Day13.findContiguousBusEfficiently("67,7,x,59,61"));
        assertEquals(1202161486, Day13.findContiguousBusEfficiently("1789,37,47,1889"));
    }

    @Test
    public void getAnswers() throws IOException {
        final String instructions = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day13"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day13.findEarliestBus(instructions));
        LOGGER.info("Part 2: {}", Day13.findContiguousBusEfficiently(instructions));
    }
}
