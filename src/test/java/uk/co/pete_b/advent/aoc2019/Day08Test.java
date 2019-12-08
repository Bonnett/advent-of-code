package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    @Test
    public void testExamplesPart1()
    {
        assertEquals(1, Day08.calculateOneTwoSum("123456789012",3,2));
    }

    @Test
    public void testExamplesPart2()
    {
        assertEquals(" ■\r\n■ ", Day08.renderImage("0222112222120000", 2, 2));
    }

    @Test
    public void getAnswers() throws IOException {
        final String imageSpec = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day08"), Charset.defaultCharset()).trim();
        LOGGER.info("Part 1: {}", Day08.calculateOneTwoSum(imageSpec, 25, 6));
        LOGGER.info("Part 2:\r\n{}", Day08.renderImage(imageSpec, 25, 6));
    }
}
