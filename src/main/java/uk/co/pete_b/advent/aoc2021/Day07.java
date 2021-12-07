package uk.co.pete_b.advent.aoc2021;

import java.util.Arrays;
import java.util.List;

public class Day07 {
    public static int calculateLeastFuel(final String submarineData) {
        final List<Integer> data = Arrays.stream(submarineData.split(",")).map(Integer::valueOf).sorted().toList();
        final int median = data.get(data.size() / 2);
        final int[] submarines = new int[data.get(data.size() - 1) + 1];

        for (Integer sub : data) {
            submarines[sub]++;
        }

        int fuelNeeded = 0;
        for (int i = 0; i < submarines.length; i++) {
            fuelNeeded += Math.abs(median - i) * submarines[i];
        }

        return fuelNeeded;
    }

    public static int calculateLeastFuelWithFuelRate(final String submarineData) {
        final List<Integer> data = Arrays.stream(submarineData.split(",")).map(Integer::valueOf).sorted().toList();
        final int[] submarines = new int[data.get(data.size() - 1) + 1];

        for (Integer sub : data) {
            submarines[sub]++;
        }

        int lowestFuelNeeded = Integer.MAX_VALUE;

        for (int target = 0; target < submarines.length; target++) {
            int fuelNeeded = 0;
            for (int i = 0; i < submarines.length; i++) {
                int diff = Math.abs(target - i);
                fuelNeeded += (diff * (diff + 1) / 2) * submarines[i];
            }

            lowestFuelNeeded = Math.min(fuelNeeded, lowestFuelNeeded);
        }

        return lowestFuelNeeded;
    }
}
