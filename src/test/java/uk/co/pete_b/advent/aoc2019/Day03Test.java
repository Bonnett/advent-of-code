package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(new Day03.Answer(6, 30), Day03.calculateClosestCrossingPoint("R8,U5,L5,D3", "U7,R6,D4,L4"));
        assertEquals(new Day03.Answer(159, 610), Day03.calculateClosestCrossingPoint("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83"));
        assertEquals(new Day03.Answer(135, 410), Day03.calculateClosestCrossingPoint("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> routes = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day03"), Charset.defaultCharset());
        LOGGER.info("{}", Day03.calculateClosestCrossingPoint(routes.get(0), routes.get(1)));
    }
}
