package uk.co.pete_b.advent.aoc2022;

import java.util.List;

public class Day02 {

    private enum Result {
        WIN, LOSE, DRAW
    }

    private enum Move {
        ROCK(1), PAPER(2), SCISSORS(3);

        private Move moveThisBeats;
        private Move moveThisLosesTo;
        private final int score;

        Move(final int score) {
            this.score = score;
        }

        public void setMoveThisBeats(final Move moveThisBeats) {
            this.moveThisBeats = moveThisBeats;
            moveThisBeats.moveThisLosesTo = this;
        }

        public int play(Move yourMove) {
            int result = 0;
            if (yourMove == this) {
                result = 3;
            } else if (yourMove != this.moveThisBeats) {
                result = 6;
            }

            return yourMove.score + result;
        }
    }

    static {
        Move.ROCK.setMoveThisBeats(Move.SCISSORS);
        Move.SCISSORS.setMoveThisBeats(Move.PAPER);
        Move.PAPER.setMoveThisBeats(Move.ROCK);
    }

    public static int getTotalScoreWithMoves(final List<String> moves) {
        int score = 0;

        for (String move : moves) {
            final String[] goes = move.split(" ");
            final Move theirMove = getMove(goes[0]);
            final Move yourMove = getMove(goes[1]);
            score += theirMove.play(yourMove);
        }

        return score;
    }

    public static int getScoreWithResult(final List<String> moves) {
        int score = 0;

        for (String move : moves) {
            final String[] goes = move.split(" ");
            final Move theirMove = getMove(goes[0]);
            final Result matchResult = getResult(goes[1]);
            switch (matchResult) {
                case WIN -> score += theirMove.play(theirMove.moveThisLosesTo);
                case LOSE -> score += theirMove.play(theirMove.moveThisBeats);
                case DRAW -> score += theirMove.play(theirMove);
            }
        }

        return score;
    }

    private static Move getMove(final String move) {
        return switch (move) {
            case "A", "X" -> Move.ROCK;
            case "B", "Y" -> Move.PAPER;
            case "C", "Z" -> Move.SCISSORS;
            default -> throw new IllegalArgumentException("Unknown move: " + move);
        };
    }

    private static Result getResult(final String move) {
        return switch (move) {
            case "X" -> Result.LOSE;
            case "Y" -> Result.DRAW;
            case "Z" -> Result.WIN;
            default -> throw new IllegalArgumentException("Unknown move: " + move);
        };
    }
}
