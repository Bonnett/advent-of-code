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
public class Day19Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19Test.class);

    private static final String EXAMPLE_BLUEPRINTS = """
            Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
            Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(33, Day19.calculateQualityTotal(IOUtils.readLines(new StringReader(EXAMPLE_BLUEPRINTS))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        //assertEquals(58, Day19.calculateQualityTotal(IOUtils.readLines(new StringReader(EXAMPLE_BLUEPRINTS))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> cubes = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day19")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day19.calculateQualityTotal(cubes));
        // LOGGER.info("Part 2: {}",  Day19.calculateQualityTotalWithReducedList(cubes));
    }
}
