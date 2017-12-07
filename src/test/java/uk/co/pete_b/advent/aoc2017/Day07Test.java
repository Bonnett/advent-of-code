package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class Day07Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final String SAMPLE_INPUT = "pbga (66)\n" +
            "xhth (57)\n" +
            "ebii (61)\n" +
            "havc (66)\n" +
            "ktlj (57)\n" +
            "fwft (72) -> ktlj, cntj, xhth\n" +
            "qoyq (66)\n" +
            "padx (45) -> pbga, havc, qoyq\n" +
            "tknk (41) -> ugml, padx, fwft\n" +
            "jptl (61)\n" +
            "ugml (68) -> gyxo, ebii, jptl\n" +
            "gyxo (61)\n" +
            "cntj (57)";

    @Test
    public void testPart1() {
        Assert.assertEquals("tknk", Day07.findRootElement(SAMPLE_INPUT));
    }

    @Test
    public void testPart2() {
        Assert.assertEquals(60, Day07.balanceTree(SAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        LOGGER.info("Part 1: " + Day07.findRootElement(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day07"), Charset.defaultCharset())));
        LOGGER.info("Part 2: " + Day07.balanceTree(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day07"), Charset.defaultCharset())));
    }
}
