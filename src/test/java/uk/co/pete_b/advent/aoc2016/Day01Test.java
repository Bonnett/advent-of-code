package uk.co.pete_b.advent.aoc2016;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2016")
public class Day01Test {

    private Day01 puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new Day01();
    }

    @Test
    public void solveDay01() throws IOException {
        InputStream routeStream = getClass().getResourceAsStream("/puzzle-data/2016/day01");
        if (routeStream == null) {
            throw new IllegalArgumentException("Unable to open route file");
        }
        int[] coords = puzzle.calculateRoute(IOUtils.toString(routeStream, StandardCharsets.UTF_8));
        assertEquals(-149, coords[0]);
        assertEquals(90, coords[1]);

        // Answer to part 1:
        assertEquals(239, Math.abs(coords[0]) + Math.abs(coords[1]));

        // Answer to part 2:
        coords = puzzle.firstDuplicate();
        assertEquals(141, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample1() {
        int[] coords = puzzle.calculateRoute("R2, L3");
        assertEquals(2, coords[0]);
        assertEquals(3, coords[1]);
        assertEquals(5, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample2() {
        int[] coords = puzzle.calculateRoute("R2, R2, R2");
        assertEquals(0, coords[0]);
        assertEquals(-2, coords[1]);
        assertEquals(2, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample3() {
        int[] coords = puzzle.calculateRoute("R5, L5, R5, R3");
        assertEquals(10, coords[0]);
        assertEquals(2, coords[1]);
        assertEquals(12, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample4() {
        puzzle.calculateRoute("R8, R4, R4, R8");
        int[] coords = puzzle.firstDuplicate();
        assertEquals(4, coords[0]);
        assertEquals(0, coords[1]);
    }
}
