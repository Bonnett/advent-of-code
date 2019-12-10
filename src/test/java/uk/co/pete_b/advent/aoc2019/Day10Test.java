package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    @Test
    public void testExamples() {
        assertEquals(new Day10.Answer(8, -1),  Day10.calculateBestPositionTotal(".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##"));

        assertEquals(new Day10.Answer(33, -1),  Day10.calculateBestPositionTotal("......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####"));

        assertEquals(new Day10.Answer(35, -1),  Day10.calculateBestPositionTotal("#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###."));

        assertEquals(new Day10.Answer(41, -1),  Day10.calculateBestPositionTotal(".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#.."));

        assertEquals(new Day10.Answer(210, 802),  Day10.calculateBestPositionTotal(".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##"));
    }

    @Test
    public void getAnswers() throws Exception {
        final String map = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day10"), Charset.defaultCharset());
        LOGGER.info("{}", Day10.calculateBestPositionTotal(map));
    }
}
