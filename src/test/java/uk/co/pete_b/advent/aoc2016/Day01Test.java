package uk.co.pete_b.advent.aoc2016;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Day01Test {

    private Day01 puzzle;

    @Before
    public void setUp() {
        puzzle = new Day01();
    }

    @Test
    public void solveDay01() throws IOException {
        InputStream routeStream = getClass().getResourceAsStream("/puzzle-data/2016/day01");
        if (routeStream == null) {
            throw new IllegalArgumentException("Unable to open route file");
        }
        int[] coords = puzzle.calculateRoute(IOUtils.toString(routeStream, "UTF-8"));
        Assert.assertEquals(-149, coords[0]);
        Assert.assertEquals(90, coords[1]);

        // Answer to part 1:
        Assert.assertEquals(239, Math.abs(coords[0]) + Math.abs(coords[1]));

        // Answer to part 2:
        coords = puzzle.firstDuplicate();
        Assert.assertEquals(141, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample1() {
        int[] coords = puzzle.calculateRoute("R2, L3");
        Assert.assertEquals(2, coords[0]);
        Assert.assertEquals(3, coords[1]);
        Assert.assertEquals(5, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample2() {
        int[] coords = puzzle.calculateRoute("R2, R2, R2");
        Assert.assertEquals(0, coords[0]);
        Assert.assertEquals(-2, coords[1]);
        Assert.assertEquals(2, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample3() {
        int[] coords = puzzle.calculateRoute("R5, L5, R5, R3");
        Assert.assertEquals(10, coords[0]);
        Assert.assertEquals(2, coords[1]);
        Assert.assertEquals(12, Math.abs(coords[0]) + Math.abs(coords[1]));
    }

    @Test
    public void solveExample4() {
        puzzle.calculateRoute("R8, R4, R4, R8");
        int[] coords = puzzle.firstDuplicate();
        Assert.assertEquals(4, coords[0]);
        Assert.assertEquals(0, coords[1]);
    }
}
