package uk.co.pete_b.advent.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 {
    public static long findProductOfWinningRaces(final List<String> raceDetails) {
        long product = 1;
        final List<Race> races = new ArrayList<>();
        final long[] raceTimes = Arrays.stream(raceDetails.get(0).substring(5).trim().split(" +")).mapToLong(Long::parseLong).toArray();
        final long[] recordDistances = Arrays.stream(raceDetails.get(1).substring(9).trim().split(" +")).mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < raceTimes.length; i++) {
            races.add(new Race(raceTimes[i], recordDistances[i]));
        }

        for (Race race : races) {
            product *= calculateRaceTimes(race);
        }

        return product;
    }
    public static long findProductOfWinningAsOneRace(final List<String> raceDetails) {
        final long raceTimes = Long.parseLong(raceDetails.get(0).substring(5).replaceAll(" ", ""));
        final long recordDistances = Long.parseLong(raceDetails.get(1).substring(9).replaceAll(" ", ""));

        return calculateRaceTimes(new Race(raceTimes, recordDistances));
    }

    private static long calculateRaceTimes(Race race) {
        final double a = -1D;
        final double b = race.raceTime;
        final double c = -1 * race.recordDistance;
        final QuadraticSolution solution = solveQuadratic(a, b, c);
        final long lower = (solution.lowerBound % 1 == 0) ? (long) solution.lowerBound + 1 : (long) Math.ceil(solution.lowerBound);
        final long upper = (solution.upperBound % 1 == 0) ? (long) solution.upperBound - 1 : (long) Math.floor(solution.upperBound);
        return (upper - lower) + 1;
    }

    private static QuadraticSolution solveQuadratic(double a, double b, double c) {
        double p1 = Math.sqrt(b * b - 4 * a * c);
        double upper = (-b - p1) / 2 * a;
        double lower = (-b + p1) / 2 * a;
        return new QuadraticSolution(lower, upper);
    }

    private record Race(long raceTime, long recordDistance) {
    }

    private record QuadraticSolution(double lowerBound, double upperBound) {
    }
}
