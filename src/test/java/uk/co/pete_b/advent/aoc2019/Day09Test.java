package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    @Test
    public void testExamplesPart1() throws Exception {
        final List<Long> input = Arrays.asList(109L, 1L, 204L, -1L, 1001L, 100L, 1L, 100L, 1008L, 100L, 16L, 101L, 1006L, 101L, 0L, 99L);
        assertEquals(input, Day09.getBoostKeyCode(0L, input));
        assertEquals(Collections.singletonList(1125899906842624L), Day09.getBoostKeyCode(1L, Arrays.asList(104L, 1125899906842624L, 99L)));
        assertEquals(Collections.singletonList(1219070632396864L), Day09.getBoostKeyCode(1L, Arrays.asList(1102L, 34915192L, 34915192L, 7L, 4L, 7L, 99L, 0L)));
    }

    @Test
    public void getAnswers() throws Exception {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day09"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day09.getBoostKeyCode(1L, instructions).get(0));
        LOGGER.info("Part 2: {}", Day09.getBoostKeyCode(2L, instructions).get(0));
    }
}
