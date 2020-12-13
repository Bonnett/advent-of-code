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

    public static long findContiguousBus(final String timetable, final Long startValue) {
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
}
