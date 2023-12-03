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
public class Day03Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03Test.class);

    private static final String SAMPLE_INPUT = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..""";

    @Test
    public void testExamples() {
        assertEquals(new Day03.Answer(4361, 467835), Day03.sumValidParts(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> partsSchematic = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day03")), Charset.defaultCharset());

        final Day03.Answer answer = Day03.sumValidParts(partsSchematic);
        LOGGER.info("Part 1: {}", answer.validPartsSum());
        LOGGER.info("Part 2: {}", answer.gearRatioSum());
    }
}
