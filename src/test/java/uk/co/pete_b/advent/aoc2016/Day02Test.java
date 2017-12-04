package uk.co.pete_b.advent.aoc2016;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Day02Test {

    private Day02 puzzle;

    @Test
    public void solveDay02() throws IOException {
        puzzle = new Day02(Day02.part1StartButtons());
        InputStream buttonStream = getClass().getResourceAsStream("/puzzle-data/2016/day02");
        if (buttonStream == null) {
            throw new IllegalArgumentException("Unable to open route file");
        }
        String combination = IOUtils.toString(buttonStream, "UTF-8");
        String key = puzzle.calculateCombination(combination);
        Assert.assertEquals("48584", key);

        puzzle = new Day02(Day02.part2StartButtons());
        key = puzzle.calculateCombination(combination);
        Assert.assertEquals("563B6", key);
    }

    @Test
    public void testExampleLine1() {
        puzzle = new Day02(Day02.part1StartButtons());
        String example = "ULL";
        String key = puzzle.calculateCombination(example);

        Assert.assertEquals("1", key);

        puzzle = new Day02(Day02.part2StartButtons());
        key = puzzle.calculateCombination(example);
        Assert.assertEquals("5", key);
    }

    @Test
    public void testExampleMultiline() {
        puzzle = new Day02(Day02.part1StartButtons());
        String example = "ULL\nRRDDD\nLURDL\nUUUUD";
        String key = puzzle.calculateCombination(example);

        Assert.assertEquals("1985", key);

        puzzle = new Day02(Day02.part2StartButtons());
        key = puzzle.calculateCombination(example);
        Assert.assertEquals("5DB3", key);
    }


}
