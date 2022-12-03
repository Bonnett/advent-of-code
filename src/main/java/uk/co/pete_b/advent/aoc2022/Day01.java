package uk.co.pete_b.advent.aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class Day01 {
    public static int calculateMaxElfSnacks(final String elfSnacks) {
        int maxElfSnacks = 0;
        int currentElfSnacks = 0;
        try (BufferedReader read = new BufferedReader(new StringReader(elfSnacks))) {
            String line;
            while ((line = read.readLine()) != null) {
                if (line.length() == 0) {
                    if (maxElfSnacks < currentElfSnacks) {
                        maxElfSnacks = currentElfSnacks;
                    }
                    currentElfSnacks = 0;
                } else {
                    currentElfSnacks += Integer.parseInt(line);
                }
            }
        } catch (IOException e) {
            return -1;
        }
        if (maxElfSnacks < currentElfSnacks) {
            maxElfSnacks = currentElfSnacks;
        }

        return maxElfSnacks;
    }

    public static int calculateTopThreeElves(final String elfSnacks) {
        int currentElfSnacks = 0;
        final List<Integer> elfCountList = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new StringReader(elfSnacks))) {
            String line;
            while ((line = read.readLine()) != null) {
                if (line.length() == 0) {
                    elfCountList.add(currentElfSnacks);
                    currentElfSnacks = 0;
                } else {
                    currentElfSnacks += Integer.parseInt(line);
                }
            }
        } catch (IOException e) {
            return -1;
        }
        elfCountList.add(currentElfSnacks);
        elfCountList.sort(Comparator.reverseOrder());

        return elfCountList.get(0) + elfCountList.get(1) + elfCountList.get(2);
    }
}
