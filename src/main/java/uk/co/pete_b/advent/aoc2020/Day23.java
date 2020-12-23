package uk.co.pete_b.advent.aoc2020;

import java.util.HashMap;
import java.util.Map;

public class Day23 {
    public static String playCupGame(final String startingCups) {
        final Map<Long, Cup> cups = new HashMap<>();

        final Cup startingCup = prepareCups(startingCups, cups, startingCups.length());

        playCupGame(cups, startingCup, 100);

        Cup cupVal = cups.get(1L).nextCup;

        final StringBuilder answer = new StringBuilder();
        while (cupVal.value != 1L) {
            answer.append(cupVal.value);
            cupVal = cupVal.nextCup;
        }

        return answer.toString();
    }

    public static long playExtendedCupGame(final String startingCups) {
        final Map<Long, Cup> cups = new HashMap<>();

        final Cup startingCup = prepareCups(startingCups, cups, 1000000);

        playCupGame(cups, startingCup, 10000000);

        final Cup cupOne = cups.get(1L);
        return cupOne.nextCup.value * cupOne.nextCup.nextCup.value;
    }

    private static void playCupGame(final Map<Long, Cup> nodes, final Cup startCup, final int iterations) {
        Cup currentCup = startCup;
        for (int i = 0; i < iterations; i++) {
            currentCup = moveCups(nodes, currentCup);
        }
    }

    private static Cup moveCups(final Map<Long, Cup> cups, final Cup currentCup) {
        final Cup firstCup = currentCup.nextCup;
        final Cup lastCup = currentCup.nextCup.nextCup.nextCup;

        // Remove the three cups
        currentCup.nextCup = lastCup.nextCup;

        // Work out where to place the cups
        long destinationCup = currentCup.value - 1;

        while (destinationCup == 0 || destinationCup == firstCup.value || destinationCup == firstCup.nextCup.value || destinationCup == firstCup.nextCup.nextCup.value) {
            if (destinationCup == 0) {
                destinationCup = cups.size();
            } else {
                destinationCup--;
            }
        }

        // Reconnect the cups
        final Cup targetCup = cups.get(destinationCup);
        lastCup.nextCup = targetCup.nextCup;
        targetCup.nextCup = firstCup;

        return currentCup.nextCup;
    }

    private static Cup prepareCups(final String startingCups, final Map<Long, Cup> cups, final int maxCups) {
        Cup headCup = null;
        Cup currentCup = null;

        for (int i = 0; i < startingCups.length(); i++) {
            final long value = Long.parseLong(startingCups.substring(i, i + 1));
            if (currentCup == null) {
                currentCup = new Cup(value);
                headCup = currentCup;
            } else {
                currentCup.nextCup = new Cup(value);
                currentCup = currentCup.nextCup;
            }
            cups.put(value, currentCup);
            currentCup.nextCup = headCup;
        }

        if (currentCup == null) {
            throw new IllegalStateException("currentCup == null");
        }

        for (long i = 10; i <= maxCups; i++) {
            currentCup.nextCup = new Cup(i);
            currentCup = currentCup.nextCup;
            cups.put(i, currentCup);
        }

        // set last cup to first cup, set current cup to be the first cup we created
        currentCup.nextCup = headCup;
        currentCup = headCup;

        return currentCup;
    }

    private static class Cup {
        private final Long value;
        private Cup nextCup;

        private Cup(Long value) {
            this.value = value;
        }
    }
}
