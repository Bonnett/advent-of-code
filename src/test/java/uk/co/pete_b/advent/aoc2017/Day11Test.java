package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day11Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    @Test
    public void testPart1() {
        Assert.assertEquals(3, Day11.calculateSteps("ne,ne,ne").getStepsAway());
        Assert.assertEquals(0, Day11.calculateSteps("ne,ne,sw,sw").getStepsAway());
        Assert.assertEquals(2, Day11.calculateSteps("ne,ne,s,s").getStepsAway());
        Assert.assertEquals(3, Day11.calculateSteps("se,sw,se,sw,sw").getStepsAway());
    }

    @Test
    public void getAnswers() throws IOException {
        final String inputString = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day11"), Charset.defaultCharset()).trim();
        LOGGER.info("Part 1: " + Day11.calculateSteps(inputString).getStepsAway());
        LOGGER.info("Part 2: " + Day11.calculateSteps(inputString).getFurthestStepsAway());
    }
}
