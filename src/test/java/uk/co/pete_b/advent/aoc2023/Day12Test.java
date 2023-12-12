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
public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static final String SAMPLE_INPUT = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1""";

    @Test
    public void testExamplesPart1() {
        assertEquals(21, Day12.sumOfPossibleCombinations(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(525152, Day12.sumOfPossibleCombinationsUnfolded(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> springLayout = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day12")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day12.sumOfPossibleCombinations(springLayout));
        LOGGER.info("Part 2: {}", Day12.sumOfPossibleCombinationsUnfolded(springLayout));
    }
}
