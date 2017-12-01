package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class Day01Test {

    @Test
    public void testExamplesPart1() {
        assertEquals(3, Day01.calculateCaptureNextValue("1122"));
        assertEquals(4, Day01.calculateCaptureNextValue("1111"));
        assertEquals(0, Day01.calculateCaptureNextValue("1234"));
        assertEquals(9, Day01.calculateCaptureNextValue("91212129"));
    }

    @Test
    public void getAnswerPart1() throws IOException {
        System.out.println(Day01.calculateCaptureNextValue(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day01"), Charset.defaultCharset())));
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(6, Day01.calculateCaptureHalfway("1212"));
        assertEquals(0, Day01.calculateCaptureHalfway("1221"));
        assertEquals(4, Day01.calculateCaptureHalfway("123425"));
        assertEquals(12, Day01.calculateCaptureHalfway("123123"));
        assertEquals(4, Day01.calculateCaptureHalfway("12131415"));
    }

    @Test
    public void getAnswerPart2() throws IOException {
        System.out.println(Day01.calculateCaptureHalfway(IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2017/day01"), Charset.defaultCharset())));
    }
}
