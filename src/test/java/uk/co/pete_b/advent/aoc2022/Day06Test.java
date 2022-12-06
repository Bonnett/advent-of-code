package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(7, Day06.findFirstMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4));
        assertEquals(5, Day06.findFirstMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 4));
        assertEquals(6, Day06.findFirstMarker("nppdvjthqldpwncqszvftbrmjlhg", 4));
        assertEquals(10, Day06.findFirstMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4));
        assertEquals(11, Day06.findFirstMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(19, Day06.findFirstMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14));
        assertEquals(23, Day06.findFirstMarker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14));
        assertEquals(23, Day06.findFirstMarker("nppdvjthqldpwncqszvftbrmjlhg", 14));
        assertEquals(29, Day06.findFirstMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14));
        assertEquals(26, Day06.findFirstMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14));
    }

    @Test
    public void getAnswers() throws IOException {
        final String stream = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day06")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day06.findFirstMarker(stream, 4));
        LOGGER.info("Part 2: {}", Day06.findFirstMarker(stream, 14));
    }
}
