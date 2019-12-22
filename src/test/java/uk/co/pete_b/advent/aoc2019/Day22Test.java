package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@Tag("2019")
public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    @Test
    public void testExamplesPart1() {
        assertArrayEquals(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, Day22.shuffleDeck(10, Collections.singletonList("deal into new stack")));
        assertArrayEquals(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, Day22.shuffleDeck(10, Collections.singletonList("cut 3")));
        assertArrayEquals(new int[]{6, 7, 8, 9, 0, 1, 2, 3, 4, 5}, Day22.shuffleDeck(10, Collections.singletonList("cut -4")));
        assertArrayEquals(new int[]{0, 7, 4, 1, 8, 5, 2, 9, 6, 3}, Day22.shuffleDeck(10, Collections.singletonList("deal with increment 3")));
        assertArrayEquals(new int[]{0, 3, 6, 9, 2, 5, 8, 1, 4, 7}, Day22.shuffleDeck(10, Arrays.asList("deal with increment 7", "deal into new stack",
                "deal into new stack")));
        assertArrayEquals(new int[]{3, 0, 7, 4, 1, 8, 5, 2, 9, 6}, Day22.shuffleDeck(10, Arrays.asList("cut 6", "deal with increment 7", "deal into new stack")));
        assertArrayEquals(new int[]{6, 3, 0, 7, 4, 1, 8, 5, 2, 9}, Day22.shuffleDeck(10, Arrays.asList("deal with increment 7", "deal with increment 9", "cut -2")));
        assertArrayEquals(new int[]{9, 2, 5, 8, 1, 4, 7, 0, 3, 6}, Day22.shuffleDeck(10, Arrays.asList("deal into new stack", "cut -2", "deal with increment 7",
                "cut 8", "cut -4", "deal with increment 7", "cut 3", "deal with increment 9", "deal with increment 3", "cut -1")));
    }

    @Test
    public void getAnswers() throws Exception {
        final List<String> operations = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2019/day22"), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", ArrayUtils.indexOf(Day22.shuffleDeck(10007, operations), 2019));
    }
}
