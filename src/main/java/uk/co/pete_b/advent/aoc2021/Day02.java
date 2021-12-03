package uk.co.pete_b.advent.aoc2021;

import java.util.List;

public class Day02 {

    public static int calculatePosition(final List<String> moves) {
        int x = 0;
        int y = 0;

        for (String move : moves) {
            int spaceIndex = move.indexOf(" ");
            String action = move.substring(0, spaceIndex);
            int value = Integer.parseInt(move.substring(spaceIndex + 1));
            switch (action) {
                case "forward" -> x += value;
                case "up" -> y += value;
                case "down" -> y -= value;
                default -> throw new IllegalArgumentException("Unexpected Action: " + action);
            }

        }

        return Math.abs(x) * Math.abs(y);
    }

    public static int calculatePositionAndAim(final List<String> moves) {
        int x = 0;
        int y = 0;
        int aim = 0;

        for (String move : moves) {
            int spaceIndex = move.indexOf(" ");
            String action = move.substring(0, spaceIndex);
            int value = Integer.parseInt(move.substring(spaceIndex + 1));
            switch (action) {
                case "forward" -> {
                    x += value;
                    y += value * aim;
                }
                case "up" -> aim -= value;
                case "down" -> aim += value;
                default -> throw new IllegalArgumentException("Unexpected Action: " + action);
            }

        }

        return Math.abs(x) * Math.abs(y);
    }
}
