package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("2017")
public class Day04Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day04Test.class);

    @Test
    public void testExamplesPart1() {
        assertTrue(Day04.isPassphraseValidPart1("aa bb cc dd ee"));
        assertFalse(Day04.isPassphraseValidPart1("aa bb cc dd aa"));
        assertTrue(Day04.isPassphraseValidPart1("aa bb cc dd aaa"));
    }

    @Test
    public void testExamplesPart2() {
        assertTrue(Day04.isPassphraseValidPart2("abcde fghij"));
        assertFalse(Day04.isPassphraseValidPart2("abcde xyz ecdab"));
        assertTrue(Day04.isPassphraseValidPart2("a ab abc abd abf abj"));
        assertTrue(Day04.isPassphraseValidPart2("iiii oiii ooii oooi oooo"));
        assertFalse(Day04.isPassphraseValidPart2("oiii ioii iioi iiio"));
    }

    @Test
    public void getAnswers() throws IOException {
        final InputStream passphraseStream = getClass().getResourceAsStream("/puzzle-data/2017/day04");
        final String passphrases = IOUtils.toString(passphraseStream, "UTF-8");
        final String[] lines = passphrases.split("\r?\n");
        int validPart1 = 0;
        int validPart2 = 0;
        for (String passphrase : lines) {
            if (Day04.isPassphraseValidPart1(passphrase)) {
                validPart1++;
            }

            if (Day04.isPassphraseValidPart2(passphrase)) {
                validPart2++;
            }
        }

        LOGGER.info("Part 1: " + validPart1);
        LOGGER.info("Part 2: " + validPart2);
    }
}
