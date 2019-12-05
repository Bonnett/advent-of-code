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
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(1, Day05.getDiagnosticCode(1, new int[]{3, 0, 4, 0, 99}));
        assertEquals(2, Day05.getDiagnosticCode(2, new int[]{3, 0, 4, 0, 99}));
    }

    @Test
    public void testExamplesPart2() {
        // Does it equal 8 - position mode
        int[] operations = new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};
        assertEquals(0, Day05.getDiagnosticCode(7, operations));
        assertEquals(1, Day05.getDiagnosticCode(8, operations));
        assertEquals(0, Day05.getDiagnosticCode(9, operations));

        // Does it equal 8 - immediate mode
        operations = new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};
        assertEquals(0, Day05.getDiagnosticCode(7, operations));
        assertEquals(1, Day05.getDiagnosticCode(8, operations));
        assertEquals(0, Day05.getDiagnosticCode(9, operations));

        // Is it less than 8 - position mode
        operations = new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};
        assertEquals(1, Day05.getDiagnosticCode(6, operations));
        assertEquals(1, Day05.getDiagnosticCode(7, operations));
        assertEquals(0, Day05.getDiagnosticCode(8, operations));
        assertEquals(0, Day05.getDiagnosticCode(9, operations));

        // Is it less than 8 - immediate mode
        operations = new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};
        assertEquals(1, Day05.getDiagnosticCode(6, operations));
        assertEquals(1, Day05.getDiagnosticCode(7, operations));
        assertEquals(0, Day05.getDiagnosticCode(8, operations));
        assertEquals(0, Day05.getDiagnosticCode(9, operations));

        // Is it 0 - position mode
        operations = new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};
        assertEquals(1, Day05.getDiagnosticCode(-1, operations));
        assertEquals(0, Day05.getDiagnosticCode(0, operations));
        assertEquals(1, Day05.getDiagnosticCode(1, operations));

        // Is it 0 - immediate mode
        operations = new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};
        assertEquals(1, Day05.getDiagnosticCode(-1, operations));
        assertEquals(0, Day05.getDiagnosticCode(0, operations));
        assertEquals(1, Day05.getDiagnosticCode(1, operations));

        // 999 if < 8, 1000 if == 8, 1001 if > 8
        operations = new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999,
                1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};
        assertEquals(999, Day05.getDiagnosticCode(7, operations));
        assertEquals(1000, Day05.getDiagnosticCode(8, operations));
        assertEquals(1001, Day05.getDiagnosticCode(9, operations));
    }

    @Test
    public void getAnswers() throws IOException {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day05"), Charset.defaultCharset());
        final int[] instructions = Arrays.stream(opCodeList.trim().split(",")).mapToInt(Integer::parseInt).toArray();
        LOGGER.info("Part 1: {}", Day05.getDiagnosticCode(1, instructions));
        LOGGER.info("Part 2: {}", Day05.getDiagnosticCode(5, instructions));
    }
}
