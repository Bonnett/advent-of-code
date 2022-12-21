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
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private static final String EXAMPLE_MONKEYS = """
            root: pppw + sjmn
            dbpl: 5
            cczh: sllz + lgvd
            zczc: 2
            ptdq: humn - dvpt
            dvpt: 3
            lfqf: 4
            humn: 5
            ljgn: 2
            sjmn: drzm * dbpl
            sllz: 4
            pppw: cczh / lfqf
            lgvd: ljgn * ptdq
            drzm: hmdt - zczc
            hmdt: 32
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(152L, Day21.calculateRootMonkeysSum(IOUtils.readLines(new StringReader(EXAMPLE_MONKEYS))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(301L, Day21.findNumberToYell(IOUtils.readLines(new StringReader(EXAMPLE_MONKEYS))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> monkeys = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day21")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day21.calculateRootMonkeysSum(monkeys));
        LOGGER.info("Part 2: {}", Day21.findNumberToYell(monkeys));
    }
}
