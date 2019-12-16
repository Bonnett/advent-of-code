package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2019")
public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals("24176176", Day16.getFirstEightDigits("80871224585914546619083218645595"));
        assertEquals("73745418", Day16.getFirstEightDigits("19617804207202209144916044189917"));
        assertEquals("52432133", Day16.getFirstEightDigits("69317163492948606335995924319873"));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals("84462026", Day16.getDigitsWithTenThousandRepeat("03036732577212944063491565474664"));
        assertEquals("78725270", Day16.getDigitsWithTenThousandRepeat("02935109699940807407585447034323"));
        assertEquals("53553731", Day16.getDigitsWithTenThousandRepeat("03081770884921959731165446850517"));
    }

    @Test
    public void getAnswers() throws Exception {
        final String inputNumber = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2019/day16"), Charset.defaultCharset());
        LOGGER.info("{}", Day16.getFirstEightDigits(inputNumber.trim()));
        LOGGER.info("{}", Day16.getDigitsWithTenThousandRepeat(inputNumber.trim()));
    }
}
