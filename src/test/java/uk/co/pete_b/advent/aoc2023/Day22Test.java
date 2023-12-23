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
public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    private static final String SAMPLE_INPUT = """
            1,0,1~1,2,1
            0,0,2~2,0,2
            0,2,3~2,2,3
            0,0,4~0,2,4
            2,0,5~2,2,5
            0,1,6~2,1,6
            1,1,8~1,1,9""";

    @Test
    public void testExamples() {
        assertEquals(new Day22.Answer(5, 7), Day22.countDisintegratingBricks(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> brickList = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day22")), Charset.defaultCharset());
        final Day22.Answer answer = Day22.countDisintegratingBricks(brickList);

        LOGGER.info("Part 1: {}", answer.soloBricks());
        LOGGER.info("Part 2: {}", answer.totalBricks());
    }
}
