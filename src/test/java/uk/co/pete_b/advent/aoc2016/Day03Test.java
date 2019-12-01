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
public class Day03Test {

    private Day03 puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new Day03();
    }

    @Test
    public void solveDay03() throws IOException {
        InputStream routeStream = getClass().getResourceAsStream("/puzzle-data/2016/day03");
        if (routeStream == null) {
            throw new IllegalArgumentException("Unable to open route file");
        }

        String data = IOUtils.toString(routeStream, StandardCharsets.UTF_8);
        // Part 1
        int numValid = puzzle.numberValid(true, data);
        assertEquals(917, numValid);

        numValid = puzzle.numberValid(false, data);
        assertEquals(1649, numValid);
    }

    @Test
    public void example1() {
        int numValid = puzzle.numberValid(true, "5 10 25");
        assertEquals(0, numValid);
    }

    public void example2() {
        String content = "101 301 501\n102 302 502\n103 303 503\n201 401 601\n202 402 602\n203 403 603";
        int numValid = puzzle.numberValid(true, content);
        assertEquals(0, numValid);
    }
}
