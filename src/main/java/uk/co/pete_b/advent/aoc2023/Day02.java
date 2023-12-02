package uk.co.pete_b.advent.aoc2023;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day02 {
    private static final Map<String, Integer> COLOUR_LIMITS = new HashMap<>();

    static {
        COLOUR_LIMITS.put("red", 12);
        COLOUR_LIMITS.put("green", 13);
        COLOUR_LIMITS.put("blue", 14);
    }

    public static int sumValidGames(final List<String> listOfGames) {
        int sum = 0;
        for (String fullGame : listOfGames) {
            final int colonIndex = fullGame.indexOf(":");
            final int gameId = Integer.parseInt(fullGame.substring(5, colonIndex));
            final String[] games = fullGame.substring(colonIndex + 2).split("; ");
            boolean validGame = true;
            for (String game : games) {
                final String[] picks = game.split(", ");
                for (String pick : picks) {
                    final String[] pickDetails = pick.split(" ");
                    final int total = Integer.parseInt(pickDetails[0]);
                    final String colour = pickDetails[1];
                    if (COLOUR_LIMITS.get(colour) < total) {
                        validGame = false;
                        break;
                    }
                }

                if (!validGame) {
                    break;
                }
            }

            if (validGame) {
                sum += gameId;
            }
        }

        return sum;
    }

    public static int calculatePowerSum(final List<String> listOfGames) {
        int power = 0;
        for (String fullGame : listOfGames) {
            final int colonIndex = fullGame.indexOf(":");
            final String[] games = fullGame.substring(colonIndex + 2).split("; ");
            final Map<String, Integer> maxValues = new LinkedHashMap<>();

            for (String game : games) {
                final String[] picks = game.split(", ");
                for (String pick : picks) {
                    final String[] pickDetails = pick.split(" ");
                    final int total = Integer.parseInt(pickDetails[0]);
                    final String colour = pickDetails[1];
                    maxValues.compute(colour, (col, count) -> {
                        if (count == null) {
                            count = 0;
                        }
                        if (count < total) {
                            count = total;
                        }

                        return count;
                    });
                }
            }

            power += maxValues.values().stream().reduce(1, (a, b) -> a * b);
        }

        return power;
    }
}
