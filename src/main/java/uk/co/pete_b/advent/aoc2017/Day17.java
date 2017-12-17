package uk.co.pete_b.advent.aoc2017;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

    public static int solveSpinlock(final int stepSize) {
        int currentPosition = 0;
        final List<Integer> list = new ArrayList<>();
        list.add(currentPosition, 0);

        for (int i = 1; i < 2018; i++) {
            currentPosition = 1 + (currentPosition + stepSize) % list.size();
            list.add(currentPosition, i);
        }

        return list.get(currentPosition + 1);
    }

    public static int getNextToZero(final int stepSize) {
        int currentPosition = 0;
        int elementAtOne = 0;

        for (int i = 1; i < 50000000; i++) {
            currentPosition = 1 + (currentPosition + stepSize) % i;
            if (currentPosition == 1) {
                elementAtOne = i;
            }
        }

        return elementAtOne;
    }
}
