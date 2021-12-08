package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    private static final String EXAMPLE_SIGNAL_PATTERNS = """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(26, Day08.countUniqueDigitOccurences(Arrays.stream(EXAMPLE_SIGNAL_PATTERNS.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(61229, Day08.sumOutputs(Arrays.stream(EXAMPLE_SIGNAL_PATTERNS.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> patterns = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day08")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day08.countUniqueDigitOccurences(patterns));
        LOGGER.info("Part 2: {}", Day08.sumOutputs(patterns));
    }
}
