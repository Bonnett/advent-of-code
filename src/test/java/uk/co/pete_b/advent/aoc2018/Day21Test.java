package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Tag("2018")
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    @Test
    public void getAnswers() throws IOException {
        final List<String> input = IOUtils.readLines(getClass().getResourceAsStream("/puzzle-data/2018/day21"), Charset.defaultCharset());
        LOGGER.info("Part 1: " + Day21.runProgram(input, true));
        LOGGER.info("Part 1: " + Day21.runProgram(input, false));
    }
}
