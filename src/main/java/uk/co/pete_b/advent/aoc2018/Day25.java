package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate4D;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 {
    private static final Pattern LINE_PATTERN = Pattern.compile("([^,]+),([^,]+),([^,]+),([^,]+)");

    public static long getConstellationNumbers(final List<String> input) {

        final Map<Coordinate4D, Set<Coordinate4D>> starMap = new HashMap<>();

        final List<Coordinate4D> stars = new ArrayList<>();
        for (final String line : input) {
            final Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches()) {
                final int x = Integer.valueOf(m.group(1).trim());
                final int y = Integer.valueOf(m.group(2).trim());
                final int z = Integer.valueOf(m.group(3).trim());
                final int t = Integer.valueOf(m.group(4).trim());

                final Coordinate4D star = new Coordinate4D(x, y, z, t);
                stars.add(star);
                starMap.put(star, new HashSet<>(Collections.singleton(star)));
            } else {
                throw new IllegalArgumentException("Invalid input");
            }
        }

        for (final Coordinate4D starOne : stars) {
            for (final Coordinate4D starTwo : stars) {
                if (starOne.equals(starTwo)) {
                    continue;
                }

                final int distance = calculateDistance(starOne, starTwo);
                if (distance <= 3) {
                    final Set<Coordinate4D> set = starMap.get(starOne);
                    set.addAll(starMap.get(starTwo));
                    set.forEach(coordinates -> starMap.put(coordinates, set));
                }
            }
        }

        return starMap.values().stream().distinct().count();
    }

    private static int calculateDistance(final Coordinate4D pointOne, final Coordinate4D pointTwo) {
        return Math.abs(pointOne.getX() - pointTwo.getX()) +
                Math.abs(pointOne.getY() - pointTwo.getY()) +
                Math.abs(pointOne.getZ() - pointTwo.getZ()) +
                Math.abs(pointOne.getT() - pointTwo.getT());
    }
}
