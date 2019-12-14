package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag("2019")
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    @Test
    public void getAnswers() throws Exception {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day13"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day13.countBlocks(instructions));
        LOGGER.info("Part 2: {}", Day13.playGame(instructions));
    }
}
