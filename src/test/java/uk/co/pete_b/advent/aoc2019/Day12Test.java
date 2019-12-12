package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(179, Day12.calculateTotalEnergy(Arrays.asList("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>"), 10));
        assertEquals(1940, Day12.calculateTotalEnergy(Arrays.asList("<x=-8, y=-10, z=0>", "<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>"), 100));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(2772L, Day12.calculateSolarSystemPeriod(Arrays.asList("<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>", "<x=4, y=-8, z=8>", "<x=3, y=5, z=-1>")));
        assertEquals(4686774924L, Day12.calculateSolarSystemPeriod(Arrays.asList("<x=-8, y=-10, z=0>", "<x=5, y=5, z=10>", "<x=2, y=-7, z=3>", "<x=9, y=-8, z=-3>")));
    }

    @Test
    public void getAnswers() throws Exception {
        final List<String> solarSystem = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day12"), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day12.calculateTotalEnergy(solarSystem, 1000));
        LOGGER.info("Part 2: {}", Day12.calculateSolarSystemPeriod(solarSystem));
    }
}
