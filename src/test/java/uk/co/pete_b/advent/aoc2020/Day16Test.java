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
public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    private static final String EXAMPLE_INPUT_ONE = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50
                        
            your ticket:
            7,1,14
                        
            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
            """;

    private static final String EXAMPLE_INPUT_TWO = """
            class: 0-1 or 4-19
            row: 0-5 or 8-19
            seat: 0-13 or 16-19
                        
            your ticket:
            11,12,13
                        
            nearby tickets:
            3,9,18
            15,1,5
            5,14,9
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(new Day16.Answer(71, 7*1*14), Day16.calculateTicketErrorRate(EXAMPLE_INPUT_ONE, ".*"));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(new Day16.Answer(0, 11*12*13), Day16.calculateTicketErrorRate(EXAMPLE_INPUT_TWO, ".*"));
    }

    @Test
    public void getAnswers() throws IOException {
        final String instructions = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day16"), Charset.defaultCharset());

        LOGGER.info("{}", Day16.calculateTicketErrorRate(instructions, "departure.*"));
    }
}
