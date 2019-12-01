package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

@Tag("2017")
public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

    @Test
    public void getAnswers() throws IOException {
        final String puzzleInput = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day23"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day23.getAnswer(puzzleInput));
        LOGGER.info("Part 2: " + Day23.optimisedAlgorithm());
    }
}
