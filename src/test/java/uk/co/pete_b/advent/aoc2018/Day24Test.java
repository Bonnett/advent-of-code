package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    private static final List<String> TEST_INPUT = Arrays.asList("Immune System:",
            "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
            "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3",
            "",
            "Infection:",
            "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
            "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4");

    @Test
    public void testExamples() {
        assertEquals(5216, Day24.getRemainingUnits(TEST_INPUT, false));
        assertEquals(51, Day24.getRemainingUnits(TEST_INPUT, true));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day24"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day24.getRemainingUnits(input, false));
        LOGGER.info("Part 2: " + Day24.getRemainingUnits(input, true));
    }
}
