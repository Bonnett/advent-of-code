package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag("2019")
public class Day19Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19Test.class);

    @Test
    public void getAnswers() throws Exception {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day19"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day19.getTractorBeamReach(instructions));
        LOGGER.info("Part 2: {}", Day19.findSanta(instructions));
    }
}

