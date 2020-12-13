package uk.co.pete_b.advent.aoc2020;

import java.util.*;
import java.util.stream.Collectors;

public class Day13 {
    public static int findEarliestBus(final String timetable) {
        final int index = timetable.indexOf("\n");

        final int arrivalTime = Integer.parseInt(timetable.substring(0, index).trim());
        final String[] buses = timetable.substring(index).trim().split(",");

        int closestBus = Integer.MAX_VALUE;
        int closestBusId = -1;

        for (String bus : buses) {
            if (bus.equals("x")) {
                continue;
            }
            final int busId = Integer.parseInt(bus);
            int difference = ((1 + (arrivalTime / busId)) * busId) - arrivalTime;
            if (difference < closestBus) {
                closestBus = difference;
                closestBusId = busId;
            }

        }

        return closestBusId * closestBus;
    }

    public static long findContiguousBusInefficiently(final String timetable, final Long startValue) {
        final String[] buses = timetable.substring(timetable.indexOf("\n") + 1).trim().split(",");

        final Map<Integer, Integer> busOffsets = new HashMap<>();
        int largestBusId = Integer.MIN_VALUE;
        int startOffset = -1;

        for (int i = 0; i < buses.length; i++) {
            String bus = buses[i];
            if (!bus.equals("x")) {
                final int busId = Integer.parseInt(bus);
                busOffsets.put(busId, i);
                if (busId > largestBusId) {
                    largestBusId = busId;
                    startOffset = i;
                }
            }
        }

        busOffsets.remove(largestBusId);

        for (int busId : busOffsets.keySet()) {
            busOffsets.put(busId, busOffsets.get(busId) - startOffset);
        }

        final List<Integer> busesSorted = busOffsets.keySet().stream().sorted().collect(Collectors.toList());
        Collections.reverse(busesSorted);

        for (long i = largestBusId * (startValue / largestBusId); i < Long.MAX_VALUE; i += largestBusId) {
            boolean allMatch = true;
            for (Integer bus : busesSorted) {
                if ((i + busOffsets.get(bus)) % bus != 0)
                {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch)
            {
                return i - startOffset;
            }
        }

        return -1;
    }

    public static long findContiguousBusEfficiently(final String timetable) {
        final String[] buses = timetable.substring(timetable.indexOf("\n") + 1).trim().split(",");

        final Map<Integer, Integer> busOffsets = new LinkedHashMap<>();

        for (int i = 0; i < buses.length; i++) {
            String bus = buses[i];
            if (!bus.equals("x")) {
                final int busId = Integer.parseInt(bus);
                busOffsets.put(busId, i);
            }
        }

        final long[] ids = new long[busOffsets.size()];
        final long[] offsets = new long[busOffsets.size()];

        int index = 0;
        for (Map.Entry<Integer, Integer> bus : busOffsets.entrySet()) {
            ids[index] = bus.getKey();
            offsets[index] = bus.getKey() - bus.getValue();
            index++;
        }

        return chineseRemainder(ids, offsets);
    }

    // Algorithm courtesy of https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
    public static long chineseRemainder(long[] n, long[] a) {

        long prod = Arrays.stream(n).reduce(1, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }

        return sm % prod;
    }

    private static long mulInv(long a, long b) {
        long b0 = b;
        long x0 = 0;
        long x1 = 1;

        if (b == 1) {
            return 1;
        }

        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0) {
            x1 += b0;
        }

        return x1;
    }
}
