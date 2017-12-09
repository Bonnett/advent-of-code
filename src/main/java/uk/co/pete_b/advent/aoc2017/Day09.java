package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day09 {
    public static class Answers {
        private final int score;
        private final int nonCancelled;

        Answers(int score, int nonCancelled) {
            this.score = score;
            this.nonCancelled = nonCancelled;
        }

        public int getScore() {
            return score;
        }

        public int getNonCancelled() {
            return nonCancelled;
        }
    }

    public static Answers solvePuzzle(final String input) {
        int score = 0;
        int currentLevel = 0;
        int nonCancelledInGarbage = 0;

        final List<Character> chars = new ArrayList<>(Arrays.asList(ArrayUtils.toObject(input.toCharArray())));

        final Iterator<Character> it = chars.iterator();
        while (it.hasNext()) {
            Character next = it.next();

            if (next == '{') {
                currentLevel++;
                score += currentLevel;
                continue;
            }

            if (next == '}') {
                currentLevel--;
                continue;
            }

            boolean inGarbage = false;
            if (next == '<') {
                inGarbage = true;
            }

            if (inGarbage) {
                nonCancelledInGarbage--;

                while (true) {
                    if (next == '!') {
                        it.remove();
                        it.next();
                        it.remove();
                        next = it.next();
                    } else if (next == '>') {
                        it.remove();
                        break;
                    } else {
                        it.remove();
                        next = it.next();
                        nonCancelledInGarbage++;
                    }
                }
            }
        }

        return new Answers(score, nonCancelledInGarbage);
    }
}
