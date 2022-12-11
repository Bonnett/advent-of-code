package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2022")
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    private static final String MONKEY_RULES = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
                        
            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0
                        
            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3
                        
            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(10605L, Day11.calculateMonkeyBusiness(IOUtils.readLines(new StringReader(MONKEY_RULES)), 20, true));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(2713310158L, Day11.calculateMonkeyBusiness(IOUtils.readLines(new StringReader(MONKEY_RULES)), 10000, false));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> monkeyRules = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day11")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day11.calculateMonkeyBusiness(monkeyRules, 20, true));
        LOGGER.info("Part 2: {}", Day11.calculateMonkeyBusiness(monkeyRules, 10000, false));
    }
}
