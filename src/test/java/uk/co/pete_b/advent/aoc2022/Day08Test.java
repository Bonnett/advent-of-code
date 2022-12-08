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
public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    private static final String HEIGHT_MAP = """
            30373
            25512
            65332
            33549
            35390
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(21, Day08.countVisibleTrees(IOUtils.readLines(new StringReader(HEIGHT_MAP))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(8, Day08.findMostScenicTree(IOUtils.readLines(new StringReader(HEIGHT_MAP))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> heightMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day08")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day08.countVisibleTrees(heightMap));
        LOGGER.info("Part 2: {}", Day08.findMostScenicTree(heightMap));
    }
}
