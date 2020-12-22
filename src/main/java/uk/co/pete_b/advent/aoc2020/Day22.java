package uk.co.pete_b.advent.aoc2020;

import java.util.*;

public class Day22 {
    public static int playCombat(final String deck) {
        final Deque<Integer> playerOneDeck = new ArrayDeque<>();
        final Deque<Integer> playerTwoDeck = new ArrayDeque<>();
        dealCards(deck, playerOneDeck, playerTwoDeck);

        while (playerOneDeck.size() != 0 && playerTwoDeck.size() != 0) {
            int playerOneCard = playerOneDeck.pop();
            int playerTwoCard = playerTwoDeck.pop();
            if (playerOneCard > playerTwoCard) {
                playerOneDeck.add(playerOneCard);
                playerOneDeck.add(playerTwoCard);
            } else if (playerOneCard < playerTwoCard) {
                playerTwoDeck.add(playerTwoCard);
                playerTwoDeck.add(playerOneCard);
            }
        }

        return calculateWinningScore(playerOneDeck, playerTwoDeck);
    }

    public static int playRecursiveCombat(final String deck) {
        final Deque<Integer> playerOneDeck = new ArrayDeque<>();
        final Deque<Integer> playerTwoDeck = new ArrayDeque<>();
        dealCards(deck, playerOneDeck, playerTwoDeck);

        playRecursiveCombat(playerOneDeck, playerTwoDeck);

        return calculateWinningScore(playerOneDeck, playerTwoDeck);
    }

    private static int calculateWinningScore(Deque<Integer> playerOneDeck, Deque<Integer> playerTwoDeck) {
        final Deque<Integer> winningDeck = (playerOneDeck.size() != 0) ? playerOneDeck : playerTwoDeck;
        final int totalCards = winningDeck.size();

        int score = 0;
        for (int i = 0; i < totalCards; i++) {
            score += (winningDeck.size() * winningDeck.pop());
        }

        return score;
    }

    private static boolean playRecursiveCombat(final Deque<Integer> playerOneDeck, final Deque<Integer> playerTwoDeck) {
        final Set<String> previousDecks = new HashSet<>();
        while (playerOneDeck.size() != 0 && playerTwoDeck.size() != 0) {
            final String deckState = playerOneDeck.toString() + playerTwoDeck.toString();
            if (previousDecks.contains(deckState)) {
                return true;
            }
            previousDecks.add(deckState);

            final int playerOneCard = playerOneDeck.pop();
            final int playerTwoCard = playerTwoDeck.pop();

            if (playerOneCard <= playerOneDeck.size() && playerTwoCard <= playerTwoDeck.size()) {
                final Deque<Integer> newPlayerOneDeck = cloneDeck(playerOneDeck, playerOneCard);
                final Deque<Integer> newPlayerTwoDeck = cloneDeck(playerTwoDeck, playerTwoCard);

                if (playRecursiveCombat(newPlayerOneDeck, newPlayerTwoDeck)) {
                    playerOneDeck.add(playerOneCard);
                    playerOneDeck.add(playerTwoCard);
                } else {
                    playerTwoDeck.add(playerTwoCard);
                    playerTwoDeck.add(playerOneCard);
                }
            } else {
                if (playerOneCard > playerTwoCard) {
                    playerOneDeck.add(playerOneCard);
                    playerOneDeck.add(playerTwoCard);
                } else if (playerOneCard < playerTwoCard) {
                    playerTwoDeck.add(playerTwoCard);
                    playerTwoDeck.add(playerOneCard);
                }
            }
        }

        return playerOneDeck.size() != 0;
    }

    private static Deque<Integer> cloneDeck(final Deque<Integer> deck, final int maxDeckSize) {
        final Deque<Integer> newDeck = new ArrayDeque<>();
        if (maxDeckSize >= deck.size()) {
            newDeck.addAll(deck);
        } else {
            final List<Integer> currentDeck = new ArrayList<>(deck);
            newDeck.addAll(currentDeck.subList(0, maxDeckSize));
        }
        return newDeck;
    }

    private static void dealCards(String deck, Deque<Integer> playerOneDeck, Deque<Integer> playerTwoDeck) {
        final String[] cards = deck.split("\n");
        boolean playerOnesCards = true;
        for (String card : cards) {
            if (card.equals("Player 1:")) {
                playerOnesCards = true;
            } else if (card.equals("Player 2:")) {
                playerOnesCards = false;
            } else if (card.length() != 0) {
                int cardValue = Integer.parseInt(card);
                if (playerOnesCards) {
                    playerOneDeck.add(cardValue);
                } else {
                    playerTwoDeck.add(cardValue);

                }
            }
        }
    }
}
