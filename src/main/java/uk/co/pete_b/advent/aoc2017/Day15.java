package uk.co.pete_b.advent.aoc2017;

public class Day15 {

    private static final int PART_ONE_TOTAL = 40000000;
    private static final int PART_TWO_TOTAL = 5000000;

    public static int getJudgeCount(final long a, final long b, final boolean doPartTwo) {

        long generatorA = a;
        long generatorB = b;

        int judgeCount = 0;

        final int total = (doPartTwo ? PART_TWO_TOTAL : PART_ONE_TOTAL);
        for (int i = 0; i < total; i++) {
            generatorA = (generatorA * 16807) % Integer.MAX_VALUE;
            generatorB = (generatorB * 48271) % Integer.MAX_VALUE;

            if (doPartTwo) {
                while (generatorA % 4 != 0) {
                    generatorA = (generatorA * 16807) % Integer.MAX_VALUE;
                }

                while (generatorB % 8 != 0) {
                    generatorB = (generatorB * 48271) % Integer.MAX_VALUE;
                }
            }

            if ((generatorA & 0xFFFF) == (generatorB & 0xFFFF)) {
                judgeCount++;
            }
        }

        return judgeCount;
    }
}
