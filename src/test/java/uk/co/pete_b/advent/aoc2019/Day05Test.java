package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    @Test
    public void testExamplesPart1() throws Exception {
        assertEquals(1L, Day05.getDiagnosticCode(1, Arrays.asList(3L, 0L, 4L, 0L, 99L)));
        assertEquals(2L, Day05.getDiagnosticCode(2, Arrays.asList(3L, 0L, 4L, 0L, 99L)));
    }

    @Test
    public void testExamplesPart2() throws Exception {
        // Does it equal 8 - position mode
        List<Long> operations = Arrays.asList(3L, 9L, 8L, 9L, 10L, 9L, 4L, 9L, 99L, -1L, 8L);
        assertEquals(0L, Day05.getDiagnosticCode(7L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(8L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(9L, operations));

        // Does it equal 8 - immediate mode
        operations = Arrays.asList(3L, 3L, 1108L, -1L, 8L, 3L, 4L, 3L, 99L);
        assertEquals(0L, Day05.getDiagnosticCode(7L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(8L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(9L, operations));

        // Is it less than 8 - position mode
        operations = Arrays.asList(3L, 9L, 7L, 9L, 10L, 9L, 4L, 9L, 99L, -1L, 8L);
        assertEquals(1L, Day05.getDiagnosticCode(6L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(7L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(8L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(9L, operations));

        // Is it less than 8 - immediate mode
        operations = Arrays.asList(3L, 3L, 1107L, -1L, 8L, 3L, 4L, 3L, 99L);
        assertEquals(1L, Day05.getDiagnosticCode(6L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(7L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(8L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(9L, operations));

        // Is it 0 - position mode
        operations = Arrays.asList(3L, 12L, 6L, 12L, 15L, 1L, 13L, 14L, 13L, 4L, 13L, 99L, -1L, 0L, 1L, 9L);
        assertEquals(1L, Day05.getDiagnosticCode(-1L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(0L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(1L, operations));

        // Is it 0 - immediate mode
        operations = Arrays.asList(3L, 3L, 1105L, -1L, 9L, 1101L, 0L, 0L, 12L, 4L, 12L, 99L, 1L);
        assertEquals(1L, Day05.getDiagnosticCode(-1L, operations));
        assertEquals(0L, Day05.getDiagnosticCode(0L, operations));
        assertEquals(1L, Day05.getDiagnosticCode(1L, operations));

        // 999 if < 8, 1000 if == 8, 1001 if > 8
        operations = Arrays.asList(3L, 21L, 1008L, 21L, 8L, 20L, 1005L, 20L, 22L, 107L, 8L, 21L, 20L, 1006L, 20L, 31L, 1106L, 0L, 36L, 98L, 0L, 0L, 1002L, 21L, 125L, 20L, 4L, 20L,
                1105L, 1L, 46L, 104L, 999L, 1105L, 1L, 46L, 1101L, 1000L, 1L, 20L, 4L, 20L, 1105L, 1L, 46L, 98L, 99L);
        assertEquals(999L, Day05.getDiagnosticCode(7L, operations));
        assertEquals(1000L, Day05.getDiagnosticCode(8L, operations));
        assertEquals(1001L, Day05.getDiagnosticCode(9L, operations));
    }

    @Test
    public void getAnswers() throws Exception {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day05"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day05.getDiagnosticCode(1L, instructions));
        LOGGER.info("Part 2: {}", Day05.getDiagnosticCode(5L, instructions));
    }
}
