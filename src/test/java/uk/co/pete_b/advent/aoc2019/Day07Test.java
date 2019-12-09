package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(43210L, Day07.findMaximumSignal(Arrays.asList(3L, 15L, 3L, 16L, 1002L, 16L, 10L, 16L, 1L, 16L, 15L, 15L, 4L, 15L, 99L, 0L, 0L)));
        assertEquals(54321L, Day07.findMaximumSignal(Arrays.asList(3L, 23L, 3L, 24L, 1002L, 24L, 10L, 24L, 1002L, 23L, -1L, 23L, 101L, 5L, 23L, 23L, 1L, 24L, 23L, 23L,
                4L, 23L, 99L, 0L, 0L)));
        assertEquals(65210L, Day07.findMaximumSignal(Arrays.asList(3L, 31L, 3L, 32L, 1002L, 32L, 10L, 32L, 1001L, 31L, -2L, 31L, 1007L, 31L, 0L, 33L, 1002L, 33L, 7L,
                33L, 1L, 33L, 31L, 31L, 1L, 32L, 31L, 31L, 4L, 31L, 99L, 0L, 0L, 0L)));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(139629729L, Day07.findMaximumSignalWithFeedback(Arrays.asList(3L, 26L, 1001L, 26L, -4L, 26L, 3L, 27L, 1002L, 27L, 2L, 27L, 1L, 27L, 26L, 27L, 4L,
                27L, 1001L, 28L, -1L, 28L, 1005L, 28L, 6L, 99L, 0L, 0L, 5L)));
        assertEquals(18216L, Day07.findMaximumSignalWithFeedback(Arrays.asList(3L, 52L, 1001L, 52L, -5L, 52L, 3L, 53L, 1L, 52L, 56L, 54L, 1007L, 54L, 5L, 55L, 1005L,
                55L, 26L, 1001L, 54L, -5L, 54L, 1105L, 1L, 12L, 1L, 53L, 54L, 53L, 1008L, 54L, 0L, 55L, 1001L, 55L, 1L, 55L, 2L, 53L, 55L, 53L, 4L, 53L, 1001L, 56L, -1L, 56L,
                1005L, 56L, 6L, 99L, 0L, 0L, 0L, 0L, 10L)));
    }

    @Test
    public void getAnswers() throws IOException {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day07"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day07.findMaximumSignal(instructions));
        LOGGER.info("Part 2: {}", Day07.findMaximumSignalWithFeedback(instructions));
    }
}
