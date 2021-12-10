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
public class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    private static final String EXAMPLE_NAVIGATION_LINES = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
            """;

    @Test
    public void testExamples() {
        assertEquals(new Day10.Answer(26397, 288957), Day10.calculateSyntaxScore(Arrays.stream(EXAMPLE_NAVIGATION_LINES.split("\r?\n")).toList()));
    }
    @Test
    public void getAnswers() throws IOException {
        final List<String> navigation = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day10")), Charset.defaultCharset());

        LOGGER.info("{}", Day10.calculateSyntaxScore(navigation));
    }
}
