package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    public void testExamples() {
        assertEquals(2, Day25.getConstellationNumbers(Arrays.asList(" 0,0,0,0",
                " 3,0,0,0",
                " 0,3,0,0",
                " 0,0,3,0",
                " 0,0,0,3",
                " 0,0,0,6",
                " 9,0,0,0",
                "12,0,0,0")));

        assertEquals(4, Day25.getConstellationNumbers(Arrays.asList("-1,2,2,0",
                "0,0,2,-2",
                "0,0,0,-2",
                "-1,2,0,0",
                "-2,-2,-2,2",
                "3,0,2,-1",
                "-1,3,2,2",
                "-1,0,-1,0",
                "0,2,1,-2",
                "3,0,0,0")));

        assertEquals(3, Day25.getConstellationNumbers(Arrays.asList("1,-1,0,1",
                "2,0,-1,0",
                "3,2,-1,0",
                "0,0,3,1",
                "0,0,-1,-1",
                "2,3,-2,0",
                "-2,2,0,0",
                "2,-2,0,-1",
                "1,-1,0,-1",
                "3,2,0,2")));

        assertEquals(8, Day25.getConstellationNumbers(Arrays.asList("1,-1,-1,-2",
                "-2,-2,0,1",
                "0,2,1,3",
                "-2,3,-2,1",
                "0,2,3,-2",
                "-1,-1,1,-2",
                "0,-2,-1,0",
                "-2,2,3,-1",
                "1,2,2,0",
                "-1,-2,0,-2")));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day25"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day25.getConstellationNumbers(input));
    }
}
