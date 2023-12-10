package uk.co.pete_b.advent.aoc2023;

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

@Tag("2023")
public class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....""";

    private static final String SAMPLE_INPUT_TWO = """
            ..F7.
            .FJ|.
            SJ.L7
            |F--J
            LJ...""";

    private static final String SAMPLE_INPUT_THREE = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........""";

    private static final String SAMPLE_INPUT_FOUR = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...""";

    private static final String SAMPLE_INPUT_FIVE = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L""";

    @Test
    public void testExamplesPart1() {
        assertEquals(new Day10.Answer(4, 1), Day10.findFarthestPipe(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
        assertEquals(new Day10.Answer(8, 1), Day10.findFarthestPipe(IOUtils.readLines(new StringReader(SAMPLE_INPUT_TWO))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(4, Day10.findFarthestPipe(IOUtils.readLines(new StringReader(SAMPLE_INPUT_THREE))).enclosedPoints());
        assertEquals(8, Day10.findFarthestPipe(IOUtils.readLines(new StringReader(SAMPLE_INPUT_FOUR))).enclosedPoints());
        assertEquals(10, Day10.findFarthestPipe(IOUtils.readLines(new StringReader(SAMPLE_INPUT_FIVE))).enclosedPoints());
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> network = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day10")), Charset.defaultCharset());

        final Day10.Answer answer = Day10.findFarthestPipe(network);
        LOGGER.info("Part 1: {}", answer.steps());
        LOGGER.info("Part 2: {}", answer.enclosedPoints());
    }
}
