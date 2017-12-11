package uk.co.pete_b.advent.aoc2017;

public class Day11 {

    public static class Answers {
        private final int stepsAway;
        private final int furthestStepsAway;

        Answers(int score, int nonCancelled) {
            this.stepsAway = score;
            this.furthestStepsAway = nonCancelled;
        }

        public int getStepsAway() {
            return stepsAway;
        }

        public int getFurthestStepsAway() {
            return furthestStepsAway;
        }
    }

    public static Answers calculateSteps(final String input) {
        int furthest = 0;
        int numSteps = 0;
        int x = 0;
        int y = 0;
        final String[] steps = input.split(",");

        for (String step : steps) {
            if ("nw".equals(step)) {
                x--;
            } else if ("n".equals(step)) {
                y--;
            } else if ("ne".equals(step)) {
                x++;
                y--;
            } else if ("se".equals(step)) {
                x++;
            } else if ("s".equals(step)) {
                y++;
            } else if ("sw".equals(step)) {
                x--;
                y++;
            }

            numSteps = Math.max(Math.abs(x), Math.abs(y));
            furthest = Math.max(furthest, numSteps);
        }


        return new Answers(numSteps, furthest);
    }
}
