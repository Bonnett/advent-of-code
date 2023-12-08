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
public class Day08Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);

    private static final String SAMPLE_INPUT_ONE = """
            RL
                        
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)""";

    private static final String SAMPLE_INPUT_TWO = """
            LLR
                        
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)""";

    private static final String SAMPLE_INPUT_THREE = """
            LR
                        
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)""";

    @Test
    public void testExamplesPart1() {
        assertEquals(2, Day08.countStepsToZZZ(IOUtils.readLines(new StringReader(SAMPLE_INPUT_ONE))));
        assertEquals(6, Day08.countStepsToZZZ(IOUtils.readLines(new StringReader(SAMPLE_INPUT_TWO))));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(6L, Day08.countStepsAsGhosts(IOUtils.readLines(new StringReader(SAMPLE_INPUT_THREE))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> network = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day08")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day08.countStepsToZZZ(network));
        LOGGER.info("Part 2: {}", Day08.countStepsAsGhosts(network));
    }
}
