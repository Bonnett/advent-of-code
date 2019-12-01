package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2017")
public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    private static final String SAMPLE_INPUT_1 = "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>\n" +
            "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>\n";

    private static final String SAMPLE_INPUT_2 = "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>\n" +
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>\n" +
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>\n" +
            "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>";

    @Test
    public void testExamplesPart1() {
        assertEquals(0, Day20.getClosestParticle(SAMPLE_INPUT_1));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1, Day20.howManyLeft(SAMPLE_INPUT_2));
    }

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day20"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day20.getClosestParticle(puzzleInput));
        LOGGER.info("Part 2: " + Day20.howManyLeft(puzzleInput));
    }

}
