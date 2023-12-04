package uk.co.pete_b.advent.aoc2023;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 {
    public static int sumWinningPoints(final List<String> cards) {
        int score = 0;
        for (String card : cards) {
            final int colonIndex = card.indexOf(":");
            final String[] split = card.substring(colonIndex + 1).split("\\|");
            final Set<Integer> winningNumbers = parseNumbers(split[0]);
            final Set<Integer> cardNumbers = parseNumbers(split[1]);
            long winningCards = cardNumbers.stream().filter(winningNumbers::contains).count();
            if (winningCards > 0) {
                score += (int) Math.pow(2, winningCards - 1);
            }
        }

        return score;
    }

    public static int countWinningCards(final List<String> cards) {
        final Map<Integer, List<Integer>> cardWinnings = new LinkedHashMap<>();
        final Map<Integer, Integer> totalCardsWon = new LinkedHashMap<>();

        for (String card : cards) {
            final int colonIndex = card.indexOf(":");
            final int cardNumber = Integer.parseInt(card.substring(5, colonIndex).trim());
            final String[] split = card.substring(colonIndex + 1).split("\\|");
            final Set<Integer> winningNumbers = parseNumbers(split[0]);
            final Set<Integer> cardNumbers = parseNumbers(split[1]);
            final long winningCards = cardNumbers.stream().filter(winningNumbers::contains).count();
            final List<Integer> winningCardsList = new ArrayList<>();
            for (int i = 1; i <= winningCards; i++) {
                winningCardsList.add(cardNumber + i);
            }
            cardWinnings.put(cardNumber, winningCardsList);
        }

        for (Map.Entry<Integer, List<Integer>> entry : cardWinnings.entrySet()) {
            // Stick the card in our hand into our total winnings
            totalCardsWon.compute(entry.getKey(), (card, total) -> {
                if (total == null) {
                    total = 0;
                }
                total += 1;
                return total;
            });

            // For each copy of this card and an entry for the cards it would win
            for (int i=0; i<totalCardsWon.get(entry.getKey()); i++) {
                for (Integer card : entry.getValue()) {
                    totalCardsWon.compute(card, (cardNumber, total) -> {
                        if (total == null) {
                            total = 0;
                        }
                        total += 1;
                        return total;
                    });
                }
            }
        }

        return totalCardsWon.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static Set<Integer> parseNumbers(final String numbers) {
        return Arrays.stream(numbers.trim().split(" +")).map(Integer::parseInt).collect(Collectors.toSet());
    }
}
