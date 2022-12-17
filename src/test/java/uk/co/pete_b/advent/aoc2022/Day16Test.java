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
public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    private static final String VALVE_SETUP = """
            Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
            Valve BB has flow rate=13; tunnels lead to valves CC, AA
            Valve CC has flow rate=2; tunnels lead to valves DD, BB
            Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
            Valve EE has flow rate=3; tunnels lead to valves FF, DD
            Valve FF has flow rate=0; tunnels lead to valves EE, GG
            Valve GG has flow rate=0; tunnels lead to valves FF, HH
            Valve HH has flow rate=22; tunnel leads to valve GG
            Valve II has flow rate=0; tunnels lead to valves AA, JJ
            Valve JJ has flow rate=21; tunnel leads to valve II
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(1651, Day16.maxPressureReleasedAlone(IOUtils.readLines(new StringReader(VALVE_SETUP))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(1707, Day16.maxPressureReleasedWithElephant(IOUtils.readLines(new StringReader(VALVE_SETUP))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day16")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day16.maxPressureReleasedAlone(pairs));
        LOGGER.info("Part 2: {}", Day16.maxPressureReleasedWithElephant(pairs));
    }
}
