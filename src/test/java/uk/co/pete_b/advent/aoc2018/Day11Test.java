package uk.co.pete_b.advent.aoc2018;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2018")
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    @Test
    public void testExamplesPart1() {
        // Check power calculation is correct
        assertEquals(4, Day11.calculatePower(8, 3, 5));
        assertEquals(-5, Day11.calculatePower(57, 122, 79));
        assertEquals(0, Day11.calculatePower(39, 217, 196));
        assertEquals(4, Day11.calculatePower(71, 101, 153));

        assertEquals(new Coordinate(33, 45), Day11.findLargestCell(18, 3).getCoordinate());
        assertEquals(new Coordinate(21, 61), Day11.findLargestCell(42, 3).getCoordinate());
    }

    @Test
    public void testExamplesPart2() {
        final Day11.Answer grid18 = Day11.findLargestTotalPower(18);
        assertEquals(new Coordinate(90, 269), grid18.getCoordinate());
        assertEquals(16, grid18.getSubGridSize());
        assertEquals(113, grid18.getSubGridPower());

        final Day11.Answer grid42 = Day11.findLargestTotalPower(42);
        assertEquals(new Coordinate(232, 251), grid42.getCoordinate());
        assertEquals(12, grid42.getSubGridSize());
        assertEquals(119, grid42.getSubGridPower());
    }

    @Test
    public void getAnswers() {
        LOGGER.info("Part 1: " + Day11.findLargestCell(8868, 3));
        LOGGER.info("Part 2: " + Day11.findLargestTotalPower(8868));
    }
}
