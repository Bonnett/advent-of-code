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
public class Day16Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    @Test
    public void testPart1() {
        assertEquals("baedc", Day16.reorderPrograms(5, "s1,x3/4,pe/b", 1));
    }

    @Test
    public void getAnswers() throws IOException {
        final String input = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day16"), Charset.defaultCharset()).trim();
        LOGGER.info("Part 1: " + Day16.reorderPrograms(16, input, 1));
        LOGGER.info("Part 2: " + Day16.reorderPrograms(16, input, 1000000000));
    }
}
