package uk.co.pete_b.advent.aoc2022;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {

    private static final Pattern MOVE_PATTERN = Pattern.compile("move ([0-9]+) from ([0-9]+) to ([0-9]+)");

    public static String getStackTops(final List<String> instructions, final boolean moveTogether) {
        int line = 0;
        final Deque<String> gameLayout = new ArrayDeque<>();
        for (; line < instructions.size(); line++) {
            final String instruction = instructions.get(line);
            if (instruction.length() == 0) {
                continue;
            } else if (instruction.startsWith("move")) {
                break;
            }

            gameLayout.push(instruction);
        }

        final Map<String, Deque<Character>> stacks = parseStackMap(gameLayout);

        // Move the stacks
        for (; line < instructions.size(); line++) {
            final String instruction = instructions.get(line);
            final Matcher matcher = MOVE_PATTERN.matcher(instruction);
            if (matcher.matches()) {
                final int total = Integer.parseInt(matcher.group(1));
                final String source = matcher.group(2);
                final String target = matcher.group(3);

                if (moveTogether) {
                    final Deque<Character> buffer = new ArrayDeque<>();
                    for (int i = 0; i < total; i++) {
                        buffer.push(stacks.get(source).pop());
                    }
                    for (int i = 0; i < total; i++) {
                        stacks.get(target).push(buffer.pop());
                    }
                } else {
                    for (int i = 0; i < total; i++) {
                        stacks.get(target).push(stacks.get(source).pop());
                    }
                }

            }
        }

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Deque<Character>> entry : stacks.entrySet()) {
            sb.append(entry.getValue().peek());
        }

        return sb.toString();
    }

    private static Map<String, Deque<Character>> parseStackMap(final Deque<String> gameLayout) {
        final Map<String, Deque<Character>> gameMap = new TreeMap<>();

        final List<Deque<Character>> stacks = new ArrayList<>();
        final String setupLine = gameLayout.pop();
        for (int i = 1; i < setupLine.length(); i++) {
            stacks.add(new ArrayDeque<>());
            i += 3;
        }

        while (!gameLayout.isEmpty()) {
            final String line = gameLayout.pop();
            for (int i = 1; i < line.length(); i++) {
                if (line.charAt(i) != ' ') {
                    stacks.get((i - 1) / 4).push(line.charAt(i));
                }
                i += 3;
            }
        }

        for (int i = 0; i < stacks.size(); i++) {
            gameMap.put(String.valueOf(i + 1), stacks.get(i));
        }

        return gameMap;
    }
}
