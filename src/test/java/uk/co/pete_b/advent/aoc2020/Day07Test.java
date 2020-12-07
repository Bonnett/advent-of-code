package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final String EXAMPLE_INPUT_ONE = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
            """;

    private static final String EXAMPLE_INPUT_TWO = """
            shiny gold bags contain 2 dark red bags.
            dark red bags contain 2 dark orange bags.
            dark orange bags contain 2 dark yellow bags.
            dark yellow bags contain 2 dark green bags.
            dark green bags contain 2 dark blue bags.
            dark blue bags contain 2 dark violet bags.
            dark violet bags contain no other bags.
            """;


    @Test
    public void testExamples() {
        assertEquals(new Day07.Answer(4, 32), Day07.countGoldBags(EXAMPLE_INPUT_ONE));
        assertEquals(new Day07.Answer(0, 126), Day07.countGoldBags(EXAMPLE_INPUT_TWO));
    }

    @Test
    public void getAnswers() throws IOException {
        final String bagRules = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day07"), Charset.defaultCharset());

        LOGGER.info("{}", Day07.countGoldBags(bagRules));
    }
}
