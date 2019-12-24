package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(2129920L, Day24.calculateBioDiversity(Arrays.asList("....#", "#..#.", "#..##", "..#..", "#....")));
    }

    @Test
    public void getAnswers() throws Exception {
        final List<String> layout = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day24"), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day24.calculateBioDiversity(layout));
    }
}
