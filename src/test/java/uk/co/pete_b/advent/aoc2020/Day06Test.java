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
public class Day06Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06Test.class);

    private static final String EXAMPLE_INPUT = """
            abc
                        
            a
            b
            c
                        
            ab
            ac
                        
            a
            a
            a
            a
                        
            b
            """;


    @Test
    public void testExamplesPart1() {
        assertEquals(11, Day06.countUniqueDeclarationGroups(EXAMPLE_INPUT));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(6, Day06.countDeclarationGroups(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String declarations = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day06"), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day06.countUniqueDeclarationGroups(declarations));
        LOGGER.info("Part 2: {}", Day06.countDeclarationGroups(declarations));
    }
}
