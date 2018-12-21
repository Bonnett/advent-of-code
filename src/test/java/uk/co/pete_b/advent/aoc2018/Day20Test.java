package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day20Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day20Test.class);

    @Test
    public void testExamples() {
        assertEquals(3, Day20.findAnswers("^WNE$").getShortestFullPath());
        assertEquals(18, Day20.findAnswers("^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$").getShortestFullPath());
        assertEquals(10, Day20.findAnswers("^ENWWW(NEEE|SSE(EE|N))$").getShortestFullPath());
        assertEquals(23, Day20.findAnswers("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$").getShortestFullPath());
        assertEquals(31, Day20.findAnswers("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$").getShortestFullPath());
    }

    @Test
    public void getAnswers() throws IOException {
        final Day20.Answer answer = Day20.findAnswers(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2018/day20"), Charset.defaultCharset()).trim());
        LOGGER.info("Part 1: " + answer.getShortestFullPath());
        LOGGER.info("Part 2: " + answer.getPathsLargerThanOneThousand());
    }
}
