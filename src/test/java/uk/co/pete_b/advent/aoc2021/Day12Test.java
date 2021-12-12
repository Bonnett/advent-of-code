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
public class Day12Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12Test.class);

    private static final String EXAMPLE_ROUTES_ONE = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """;

    private static final String EXAMPLE_ROUTE_TWO = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
            """;

    private static final String EXAMPLE_ROUTE_THREE = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
            """;

    @Test
    public void testExamplesPart1() {
        assertEquals(10, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTES_ONE.split("\r?\n")).toList(), false));
        assertEquals(19, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTE_TWO.split("\r?\n")).toList(), false));
        assertEquals(226, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTE_THREE.split("\r?\n")).toList(), false));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(36, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTES_ONE.split("\r?\n")).toList(), true));
        assertEquals(103, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTE_TWO.split("\r?\n")).toList(), true));
        assertEquals(3509, Day12.countUniqueRoutes(Arrays.stream(EXAMPLE_ROUTE_THREE.split("\r?\n")).toList(), true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> routeList = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day12")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day12.countUniqueRoutes(routeList, false));
        LOGGER.info("Part 2: {}", Day12.countUniqueRoutes(routeList, true));
    }
}
