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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);


    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day02.countValidPasswordsRule1(Arrays.asList("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(1, Day02.countValidPasswordsRule2(Arrays.asList("1-3 a: abcde", "1-3 b: cdefg", "2-9 c: ccccccccc")));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> passwords = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2020/day02"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day02.countValidPasswordsRule1(passwords));
        LOGGER.info("Part 2: {}", Day02.countValidPasswordsRule2(passwords));
    }
}
