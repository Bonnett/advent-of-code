package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.pete_b.advent.utils.Coordinate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag("2019")
public class Day11Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11Test.class);

    @Test
    public void getAnswers() throws Exception {
        final String opCodeList = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day11"), Charset.defaultCharset());
        final List<Long> instructions = Arrays.stream(opCodeList.trim().split(",")).map(Long::parseLong).collect(Collectors.toList());
        LOGGER.info("Part 1: {}", Day11.startPainting(0L, instructions).size());

        final Map<Coordinate, Long> paintSpots = Day11.startPainting(1L, instructions);
        final int maxY = paintSpots.keySet().stream().mapToInt(Coordinate::getY).max().orElse(0);
        final int maxX = paintSpots.keySet().stream().mapToInt(Coordinate::getX).max().orElse(0);

        final int[][] grid = new int[maxY + 1][maxX + 1];
        paintSpots.forEach((key, value) -> grid[key.getY()][key.getX()] = value.intValue());

        final StringBuilder paintedWall = new StringBuilder();
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) {
                    paintedWall.append(' ');
                } else {
                    paintedWall.append('#');
                }
            }
            paintedWall.append("\r\n");
        }

        LOGGER.info("Part 2:\r\n{}", paintedWall);
    }
}
