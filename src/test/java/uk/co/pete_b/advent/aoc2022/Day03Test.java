package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);
    public static final List<String> SAMPLE_RUCKSACKS = Arrays.asList("vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", "PmmdzqPrVvPwwTWBwg", "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT", "CrZsJsPPZsGzwwsLwLmpwMDw");

    @Test
    public void testExamplesPart1() {
        assertEquals(157, Day03.sumPriorities(SAMPLE_RUCKSACKS));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(70, Day03.sumPrioritiesInGroupsOfThree(SAMPLE_RUCKSACKS));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> rucksacks = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day03")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day03.sumPriorities(rucksacks));
        LOGGER.info("Part 2: {}", Day03.sumPrioritiesInGroupsOfThree(rucksacks));
    }
}
