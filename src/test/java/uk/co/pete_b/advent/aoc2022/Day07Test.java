package uk.co.pete_b.advent.aoc2022;

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

@Tag("2022")
public class Day07Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07Test.class);

    private static final String INPUT_FILESYSTEM = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
            """;

    @Test
    public void testExamples() throws IOException {
        assertEquals(new Day07.Answer(95437, 24933642), Day07.findTotalSizeOfSub100KDirectories(IOUtils.readLines(new StringReader(INPUT_FILESYSTEM))));
    }

    @Test
    public void getAnswers() throws IOException {
        final List<String> fileSystem = IOUtils.readLines(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2022/day07")), Charset.defaultCharset());

        LOGGER.info("{}", Day07.findTotalSizeOfSub100KDirectories(fileSystem));
    }
}
