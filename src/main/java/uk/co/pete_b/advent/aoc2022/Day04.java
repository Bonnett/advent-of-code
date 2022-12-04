package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class Day04 {
    public static int calculateFullyOverlappingPairs(final List<String> pairSets) {
        int overlappingPairs = 0;
        for (String pairSet : pairSets) {
            final List<Pair<Integer, Integer>> pairs = parsePairSet(pairSet);

            if (pairs.get(0).getLeft() <= pairs.get(1).getLeft() && pairs.get(0).getRight() >= pairs.get(1).getRight()) {
                overlappingPairs++;
            } else if (pairs.get(1).getLeft() <= pairs.get(0).getLeft() && pairs.get(1).getRight() >= pairs.get(0).getRight()) {
                overlappingPairs++;
            }
        }

        overlappingPairs = 0;
        for (String pairSet : pairSets) {

            String[] pairs = pairSet.split(",");
            String[] pairOne = pairs[0].split("-");
            String[] pairTwo = pairs[1].split("-");
            int startPairOne = Integer.parseInt(pairOne[0]);
            int endPairOne = Integer.parseInt(pairOne[1]);
            int startPairTwo = Integer.parseInt(pairTwo[0]);
            int endPairTwo = Integer.parseInt(pairTwo[1]);
            if (startPairOne <= startPairTwo && endPairOne >= endPairTwo) {
                overlappingPairs++;
            } else if (startPairTwo <= startPairOne && endPairTwo >= endPairOne) {
                overlappingPairs++;
            }

        }
        return overlappingPairs;
    }

    public static int calculateAnyOverlappingPairs(final List<String> pairSets) {
        int overlappingPairs = 0;

        for (String pairSet : pairSets) {
            final List<Pair<Integer, Integer>> pairs = parsePairSet(pairSet);

            if (!(pairs.get(0).getRight() < pairs.get(1).getLeft() || pairs.get(1).getRight() < pairs.get(0).getLeft())) {
                overlappingPairs++;
            }
        }

        return overlappingPairs;
    }

    private static List<Pair<Integer, Integer>> parsePairSet(String pairSet) {
        return Arrays.stream(pairSet.split(","))
                .map(x -> x.split("-"))
                .map(x -> Pair.of(Integer.parseInt(x[0]), Integer.parseInt(x[1])))
                .toList();
    }
}
