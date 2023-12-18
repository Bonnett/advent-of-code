package uk.co.pete_b.advent.utils;

import java.util.Arrays;
import java.util.List;

public class Maths {
    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    // Shoelace Area algorithm courtesy of https://rosettacode.org/wiki/Shoelace_formula_for_polygonal_area#Java
    public static int shoelaceArea(final List<Coordinate> shape) {
        int n = shape.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += shape.get(i).getX() * shape.get(i + 1).getY() - shape.get(i + 1).getX() * shape.get(i).getY();
        }

        return (int) Math.abs(a + shape.get(n - 1).getX() * shape.get(0).getY() - shape.get(0).getX() * shape.get(n - 1).getY()) / 2;
    }

    // Shoelace Area algorithm courtesy of https://rosettacode.org/wiki/Shoelace_formula_for_polygonal_area#Java
    public static long longShoelaceArea(final List<LongCoordinate> shape) {
        int n = shape.size();
        double a = 0.0;
        for (int i = 0; i < n - 1; i++) {
            a += shape.get(i).getX() * shape.get(i + 1).getY() - shape.get(i + 1).getX() * shape.get(i).getY();
        }

        return (long) Math.abs(a + shape.get(n - 1).getX() * shape.get(0).getY() - shape.get(0).getX() * shape.get(n - 1).getY()) / 2;
    }
}
