package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

    @Test
    public void testExamplesPart1() {
        final List<String> testInput = Arrays.asList("pos=<0,0,0>, r=4",
                "pos=<1,0,0>, r=1",
                "pos=<4,0,0>, r=3",
                "pos=<0,2,0>, r=1",
                "pos=<0,5,0>, r=3",
                "pos=<0,0,3>, r=1",
                "pos=<1,1,1>, r=1",
                "pos=<1,1,2>, r=1",
                "pos=<1,3,1>, r=1");

        assertEquals(7, Day23.findStrongestNanobotRange(testInput));
    }

    @Test
    public void testExamplesPart2() {
        final List<String> testInput = Arrays.asList("pos=<10,12,12>, r=2",
                "pos=<12,14,12>, r=2",
                "pos=<16,12,12>, r=4",
                "pos=<14,14,14>, r=6",
                "pos=<50,50,50>, r=200",
                "pos=<10,10,10>, r=5");

        assertEquals(36, Day23.findDistanceToBestSpot(testInput));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day23"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day23.findStrongestNanobotRange(input));
        LOGGER.info("Part 2: " + Day23.findDistanceToBestSpot(input));
    }

}
