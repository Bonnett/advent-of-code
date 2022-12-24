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
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    private static final String EXAMPLE_BLIZZARD_MAP = """
            #.######
            #>>.<^<#
            #.<..<<#
            #>v.><>#
            #<^v^^>#
            ######.#
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(18, Day24.findQuickestRoute(IOUtils.readLines(new StringReader(EXAMPLE_BLIZZARD_MAP))));
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(54, Day24.findQuickestRouteWhenReturningToCollectSnacks(IOUtils.readLines(new StringReader(EXAMPLE_BLIZZARD_MAP))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> blizzardMap = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day24")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day24.findQuickestRoute(blizzardMap));
        LOGGER.info("Part 2: {}", Day24.findQuickestRouteWhenReturningToCollectSnacks(blizzardMap));
    }
}
