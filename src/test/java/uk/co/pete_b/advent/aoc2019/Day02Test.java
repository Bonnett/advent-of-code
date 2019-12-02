package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day02Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day02Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day02.calculateOpCodes(new int[]{1, 0, 0, 0, 99})[0]);
        assertEquals(2, Day02.calculateOpCodes(new int[]{2, 3, 0, 3, 99})[0]);
        assertEquals(2, Day02.calculateOpCodes(new int[]{2, 4, 4, 5, 99, 0})[0]);
        assertEquals(30, Day02.calculateOpCodes(new int[]{1, 1, 1, 4, 99, 5, 6, 0, 99})[0]);
        assertEquals(3500, Day02.calculateOpCodes(new int[]{1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50})[0]);
    }

    @Test
    public void getAnswers() throws IOException {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day02"), Charset.defaultCharset());
        final int[] opCodes = Arrays.stream(opCodeList.trim().split(",")).mapToInt(Integer::parseInt).toArray();
        opCodes[1] = 12;
        opCodes[2] = 2;
        LOGGER.info("Part 1: {}", Day02.calculateOpCodes(opCodes)[0]);

        final int partTwoTarget = 19690720;
        boolean found = false;
        for (int noun = 0; !found && noun <= 99; noun++) {
            for (int verb = 0; !found && verb <= 99; verb++) {
                opCodes[1] = noun;
                opCodes[2] = verb;

                final int[] newArray = Day02.calculateOpCodes(opCodes);
                if (newArray[0] == partTwoTarget) {
                    LOGGER.info("Part 2: Noun: {}, Verb: {}, Total: {}", noun, verb, (100 * noun) + verb);
                    found = true;
                }
            }
        }
    }
}
