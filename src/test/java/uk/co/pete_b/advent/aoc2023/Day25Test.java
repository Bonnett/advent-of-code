package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2023")
public class Day25Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day25Test.class);

    private static final String SAMPLE_INPUT = """
            jqt: rhn xhk nvd
            rsh: frs pzl lsr
            xhk: hfx
            cmg: qnr nvd lhk bvb
            rhn: xhk bvb hfx
            bvb: xhk hfx
            pzl: lsr hfx nvd
            qnr: nvd
            ntq: jqt hfx bvb xhk
            nvd: lhk
            lsr: lhk
            rzs: qnr cmg lsr rsh
            frs: qnr lhk lsr""";

    @Test
    public void testExamplesPart1() {
        assertEquals(54, Day25.findProductOfSeparateGroups(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), true));
        assertEquals(54, Day25.findProductOfSeparateGroups(IOUtils.readLines(new StringReader(SAMPLE_INPUT)), false));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> nodes = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2023/day25")), Charset.defaultCharset());

        LOGGER.info("Part 1: {}", Day25.findProductOfSeparateGroups(nodes, false));
    }
}
