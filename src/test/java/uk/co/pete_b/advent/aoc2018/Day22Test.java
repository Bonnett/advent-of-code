package uk.co.pete_b.advent.aoc2018;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day22Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day22Test.class);

    @Test
    public void testExamples() {
        Day22.Answers answers = Day22.getRiskLevel(510, new Coordinate(10, 10));
        assertEquals(114, answers.getRiskLevel());
        assertEquals(45, answers.getTimeToReachTarget());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day22.Answers answers = Day22.getRiskLevel(3558, new Coordinate(15, 740));
        LOGGER.info("Part 1: " + answers.getRiskLevel());
        LOGGER.info("Part 2: " + answers.getTimeToReachTarget());
    }
}
