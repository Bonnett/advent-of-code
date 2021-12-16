package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2021")
public class Day16Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16Test.class);

    @Test
    public void testExamplesPart1() {
        assertEquals(6, Day16.sumVersionNumbers("D2FE28").versionTotal());
        assertEquals(9, Day16.sumVersionNumbers("38006F45291200").versionTotal());
        assertEquals(14, Day16.sumVersionNumbers("EE00D40C823060").versionTotal());
        assertEquals(16, Day16.sumVersionNumbers("8A004A801A8002F478").versionTotal());
        assertEquals(12, Day16.sumVersionNumbers("620080001611562C8802118E34").versionTotal());
        assertEquals(23, Day16.sumVersionNumbers("C0015000016115A2E0802F182340").versionTotal());
        assertEquals(31, Day16.sumVersionNumbers("A0016C880162017C3686B18A3D4780").versionTotal());
    }

    @Test
    public void testExamplesPart2() {
        assertEquals(3, Day16.sumVersionNumbers("C200B40A82").output());
        assertEquals(54, Day16.sumVersionNumbers("04005AC33890").output());
        assertEquals(7, Day16.sumVersionNumbers("880086C3E88112").output());
        assertEquals(9, Day16.sumVersionNumbers("CE00C43D881120").output());
        assertEquals(1, Day16.sumVersionNumbers("D8005AC2A8F0").output());
        assertEquals(0, Day16.sumVersionNumbers("F600BC2D8F").output());
        assertEquals(0, Day16.sumVersionNumbers("9C005AC2F8F0").output());
        assertEquals(1, Day16.sumVersionNumbers("9C0141080250320F1802104A08").output());
    }

    @Test
    public void getAnswers() throws IOException {
        final String inputPacket = IOUtils.toString(Objects.requireNonNull(getClass().getResourceAsStream("/puzzle-data/2021/day16")), Charset.defaultCharset()).trim();

        LOGGER.info("{}", Day16.sumVersionNumbers(inputPacket));
    }
}
