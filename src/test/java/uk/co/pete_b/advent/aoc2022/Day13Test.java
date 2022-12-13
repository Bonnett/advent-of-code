package uk.co.pete_b.advent.aoc2022;

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

@Tag("2022")
public class Day13Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13Test.class);

    private static final String EXAMPLE_PAIRS = """
            [1,1,3,1,1]
            [1,1,5,1,1]
                        
            [[1],[2,3,4]]
            [[1],4]
                        
            [9]
            [[8,7,6]]
                        
            [[4,4],4,4]
            [[4,4],4,4,4]
                        
            [7,7,7,7]
            [7,7,7]
                        
            []
            [3]
                        
            [[[]]]
            [[]]
                        
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            """;

    @Test
    public void testExamplesPart1() throws IOException {
        assertEquals(13, Day13.sumCorrectOrderIndices(IOUtils.readLines(new StringReader(EXAMPLE_PAIRS))).sumOfValidIndices());
    }

    @Test
    public void testExamplesPart2() throws IOException {
        assertEquals(140, Day13.sumCorrectOrderIndices(IOUtils.readLines(new StringReader(EXAMPLE_PAIRS))).decoderKey());
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> pairs = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day13")), Charset.defaultCharset());

        final Day13.Answer answer = Day13.sumCorrectOrderIndices(pairs);
        LOGGER.info("Part 1: {}", answer.sumOfValidIndices());
        LOGGER.info("Part 2: {}", answer.decoderKey());
    }
}
