package uk.co.pete_b.advent.aoc2020;

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

@Tag("2020")
public class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(514579, Day01.calculateTwoItemsSummingTo2020(Arrays.asList(1721, 979, 366, 299, 675, 1456)));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(241861950, Day01.calculateThreeItemsSummingTo2020(Arrays.asList(1721, 979, 366, 299, 675, 1456)));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> stringValues = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2020/day01"), Charset.defaultCharset());
        final List<Integer> totalFuel = stringValues.stream().map(Integer::parseInt).collect(Collectors.toList());

        LOGGER.info("Part 1: {}", Day01.calculateTwoItemsSummingTo2020(totalFuel));
        LOGGER.info("Part 2: {}", Day01.calculateThreeItemsSummingTo2020(totalFuel));
    }
}
