package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);

    private static final String EXAMPLE_SUM_ONE = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
            """;
    private static final String EXAMPLE_SUM_TWO = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
            """;

    @Test
    public void testExamplePart1() {

        assertEquals("[[[[0,9],2],3],4]", StringUtils.join(Day18.reduceSums(Collections.singletonList("[[[[[9,8],1],2],3],4]")), ""));
        assertEquals("[7,[6,[5,[7,0]]]]", StringUtils.join(Day18.reduceSums(Collections.singletonList("[7,[6,[5,[4,[3,2]]]]]")), ""));
        assertEquals("[[6,[5,[7,0]]],3]", StringUtils.join(Day18.reduceSums(Collections.singletonList("[[6,[5,[4,[3,2]]]],1]")), ""));
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", StringUtils.join(Day18.reduceSums(Collections.singletonList("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")), ""));
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", StringUtils.join(Day18.reduceSums(Arrays.asList("[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]")), ""));
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", StringUtils.join(Day18.reduceSums(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]")), ""));
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", StringUtils.join(Day18.reduceSums(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]")), ""));
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", StringUtils.join(Day18.reduceSums(Arrays.asList("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]")), ""));
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", StringUtils.join(Day18.reduceSums(Arrays.stream(EXAMPLE_SUM_ONE.split("\r?\n")).toList()), ""));

        assertEquals(29, Day18.calculateMagnitude(Collections.singletonList("[9,1]")));
        assertEquals(129, Day18.calculateMagnitude(Collections.singletonList("[[9,1],[1,9]]")));
        assertEquals(143, Day18.calculateMagnitude(Collections.singletonList("[[1,2],[[3,4],5]]")));
        assertEquals(1384, Day18.calculateMagnitude(Collections.singletonList("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")));
        assertEquals(445, Day18.calculateMagnitude(Collections.singletonList("[[[[1,1],[2,2]],[3,3]],[4,4]]")));
        assertEquals(791, Day18.calculateMagnitude(Collections.singletonList("[[[[3,0],[5,3]],[4,4]],[5,5]]")));
        assertEquals(1137, Day18.calculateMagnitude(Collections.singletonList("[[[[5,0],[7,4]],[5,5]],[6,6]]")));
        assertEquals(3488, Day18.calculateMagnitude(Arrays.stream(EXAMPLE_SUM_ONE.split("\r?\n")).toList()));
        assertEquals(4140, Day18.calculateMagnitude(Arrays.stream(EXAMPLE_SUM_TWO.split("\r?\n")).toList()));
    }

    @Test
    public void testExamplePart2() {
        assertEquals(3993, Day18.calculateMaximumMagnitudeFromAnyTwo(Arrays.stream(EXAMPLE_SUM_TWO.split("\r?\n")).toList()));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> sums = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day18")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day18.calculateMagnitude(sums));
        LOGGER.info("Part 2: {}", Day18.calculateMaximumMagnitudeFromAnyTwo(sums));
    }
}
