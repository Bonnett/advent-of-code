package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

@Tag("2021")
public class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    public void getAnswers() throws IOException {
        final List<String> computer = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day24")), Charset.defaultCharset());
        LOGGER.info("Part 1: {}", Day24.findMonad(computer, true));
        LOGGER.info("Part 2: {}", Day24.findMonad(computer, false));
    }
}
