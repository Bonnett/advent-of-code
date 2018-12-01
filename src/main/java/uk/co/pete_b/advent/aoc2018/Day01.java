package uk.co.pete_b.advent.aoc2018;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Day01 {
    public static int calculateFrequency(final List<String> steps) {
        int total = 0;
        for (String step : steps) {
            total += Integer.parseInt(step);
        }

        return total;
    }

    public static int firstRepeatFrequency(final List<String> steps) {
        final Set<Integer> frequencies = new TreeSet<>();

        boolean reachedTarget = false;
        int total = 0;
        while (!reachedTarget) {
            for (String step : steps) {
                if (frequencies.contains(total)) {
                    reachedTarget = true;
                    break;
                }

                frequencies.add(total);
                total += Integer.parseInt(step);
            }
        }

        return total;
    }
}
