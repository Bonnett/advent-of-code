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
public class Day17Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day17Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            2413432311323
            3215453535623
            3255245654254
            3446585845452
            4546657867536
            1438598798454
            4457876987766
            3637877979653
            4654967986887
            4564679986453
            1224686865563
            2546548887735
            4322674655533""";

    private static final String SAMPLE_INPUT_TWO = """
            111111111111
            999999999991
            999999999991
            999999999991
            999999999991""";

    @Test
    public void testExamplesPart1() {
        assertEquals(102, Day17.findLowestHeatLoss(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(94, Day17.findLowestHeatLossWithUltraCrucible(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
        assertEquals(71, Day17.findLowestHeatLossWithUltraCrucible(IOUtils.readLines(new StringReader(SAMPLE_INPUT_TWO))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> lavaMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day17")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day17.findLowestHeatLoss(lavaMap));
        LOGGER.info("Part 2: {}", Day17.findLowestHeatLossWithUltraCrucible(lavaMap));
    }
}
