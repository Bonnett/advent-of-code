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
public class Day05Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05Test.class);

    private static final String SAMPLE_INPUT = """
            seeds: 79 14 55 13
                        
            seed-to-soil map:
            50 98 2
            52 50 48
                        
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
                        
            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4
                        
            water-to-light map:
            88 18 7
            18 25 70
                        
            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13
                        
            temperature-to-humidity map:
            0 69 1
            1 0 69
                        
            humidity-to-location map:
            60 56 37
            56 93 4""";

    @Test
    public void testExamples() {
        assertEquals(new Day05.Answer(35L, 46L), Day05.findLowestLocationSeedNumber(IOUtils.readLines(new StringReader(SAMPLE_INPUT))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> resourceMaps = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day05")), Charset.defaultCharset());

        final Day05.Answer answer = Day05.findLowestLocationSeedNumber(resourceMaps);
        LOGGER.info("Part 1: {}", answer.lowestIndividualLocation());
        LOGGER.info("Part 2: {}", answer.lowestRangeLocation());
    }
}
