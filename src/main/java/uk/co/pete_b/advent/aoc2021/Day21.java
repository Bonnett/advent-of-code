package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class Day21 {

    private static final Map<Integer, Integer> DICE_ROLL_MOVEMENTS = Map.of(3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1);

    public static int calculateScoreDeterministicDie(final List<String> playerInput) {
        int playerOnePosition = Integer.parseInt(playerInput.get(0).substring(28));
        int playerTwoPosition = Integer.parseInt(playerInput.get(1).substring(28));

        int playerOneScore = 0;
        int playerTwoScore = 0;

        int diceRolls = 0;

        int dice = 1;

        while (true) {
            int playerOneRolls = (dice * 3) + 3;
            playerOnePosition = playerOnePosition + playerOneRolls;

            int newPosPlayerOne = playerOnePosition % 10;
            if (newPosPlayerOne == 0) {
                newPosPlayerOne = 10;
            }
            playerOnePosition = newPosPlayerOne;
            playerOneScore += playerOnePosition;

            diceRolls += 3;
            if (playerOneScore >= 1000) {
                break;
            }

            dice += 3;
            if (dice > 100) {
                dice -= 100;
            }

            int playerTwoRolls = (dice * 3) + 3;
            dice += 3;
            if (dice > 100) {
                dice -= 100;
            }
            playerTwoPosition = playerTwoPosition + playerTwoRolls;

            int newPosPlayerTwo = playerTwoPosition % 10;
            if (newPosPlayerTwo == 0) {
                newPosPlayerTwo = 10;
            }
            playerTwoPosition = newPosPlayerTwo;

            playerTwoScore += playerTwoPosition;

            diceRolls += 3;
            if (playerTwoScore >= 1000) {
                break;
            }
        }

        return Math.min(playerOneScore, playerTwoScore) * diceRolls;
    }

    public static long calculateScoreQuantumDie(List<String> playerInput) {
        final int playerOnePosition = Integer.parseInt(playerInput.get(0).substring(28));
        final int playerTwoPosition = Integer.parseInt(playerInput.get(1).substring(28));

        final Pair<Long, Long> wins = playQuantumDiceGame(Pair.of(playerOnePosition, playerTwoPosition),
                Pair.of(0, 0), 0, 1);

        return Math.max(wins.getLeft(), wins.getRight());
    }

    private static Pair<Long, Long> playQuantumDiceGame(final Pair<Integer, Integer> playerPositions,
                                                        final Pair<Integer, Integer> playerScores, final int currentPlayer,
                                                        final long numberOfUniverses) {
        long winsPlayerOne = 0L;
        long winsPlayerTwo = 0L;

        for (Map.Entry<Integer, Integer> entry : DICE_ROLL_MOVEMENTS.entrySet()) {
            final int diceRoll = entry.getKey();

            final long nextNumberOfUniverses = numberOfUniverses * entry.getValue();

            int playerOnePosition = playerPositions.getLeft();
            int playerOneScore = playerScores.getLeft();

            int playerTwoPosition = playerPositions.getRight();
            int playerTwoScore = playerScores.getRight();

            if (currentPlayer == 0) {
                playerOnePosition = getPlayerPosition(diceRoll, playerOnePosition);
                playerOneScore += playerOnePosition;
            } else {
                playerTwoPosition = getPlayerPosition(diceRoll, playerTwoPosition);
                playerTwoScore += playerTwoPosition;
            }

            if (playerOneScore >= 21) {
                winsPlayerOne += nextNumberOfUniverses;
            } else if (playerTwoScore >= 21) {
                winsPlayerTwo += nextNumberOfUniverses;
            } else {
                final Pair<Long, Long> wins = playQuantumDiceGame(Pair.of(playerOnePosition, playerTwoPosition),
                        Pair.of(playerOneScore, playerTwoScore), currentPlayer == 0 ? 1 : 0, nextNumberOfUniverses);

                winsPlayerOne += wins.getLeft();
                winsPlayerTwo += wins.getRight();
            }
        }

        return Pair.of(winsPlayerOne, winsPlayerTwo);
    }

    private static int getPlayerPosition(final int diceRoll, final int currentPosition) {
        int nextPosition = currentPosition + diceRoll;
        if (nextPosition > 10) {
            nextPosition = nextPosition - 10;
        }
        return nextPosition;
    }
}
