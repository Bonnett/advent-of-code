package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day04 {

    private static final int BINGO_CARD_SIZE = 5;

    public static int calculateFinalScore(final List<String> bingoInput, final boolean winFirst) {
        final int[] numbers = Arrays.stream(bingoInput.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        final List<BingoCard> bingoCards = new ArrayList<>();
        List<String> currentCard = new ArrayList<>();
        for (int i = 2; i < bingoInput.size(); i++) {
            final String currentLine = bingoInput.get(i).trim();

            if (currentLine.isEmpty()) {
                bingoCards.add(new BingoCard(currentCard));
                currentCard = new ArrayList<>();
            } else {
                currentCard.add(currentLine);
            }
        }
        bingoCards.add(new BingoCard(currentCard));

        for (int number : numbers) {
            final Iterator<BingoCard> bingoIterator = bingoCards.iterator();
            while (bingoIterator.hasNext()) {
                BingoCard bingoCard = bingoIterator.next();

                if (bingoCard.numberCalled(number)) {
                    if (winFirst || bingoCards.size() == 1) {
                        return bingoCard.getCardScore() * number;
                    } else {
                        bingoIterator.remove();
                    }
                }
            }
        }

        // If something goes wrong
        return -1;
    }

    private static class BingoCard {
        final Set<Integer> remainingNumbers = new HashSet<>();
        final Map<Integer, Coordinate> cardLayout = new HashMap<>();
        final boolean[][] cards = new boolean[BINGO_CARD_SIZE][BINGO_CARD_SIZE];

        public BingoCard(List<String> bingoCard) {
            for (int y = 0; y < bingoCard.size(); y++) {
                final String[] card = bingoCard.get(y).split("[ ]+");
                for (int x = 0; x < card.length; x++) {
                    final int number = Integer.parseInt(card[x].trim());
                    this.remainingNumbers.add(number);
                    this.cardLayout.put(number, new Coordinate(x, y));
                }
            }
        }

        public boolean numberCalled(final int number) {
            if (this.remainingNumbers.contains(number)) {
                this.remainingNumbers.remove(number);
                final Coordinate card = this.cardLayout.get(number);
                this.cards[card.getY()][card.getX()] = true;

                boolean matches = true;
                for (int y = 0; y < BINGO_CARD_SIZE && matches; y++) {
                    matches = this.cards[y][card.getX()];
                }

                if (matches) {
                    return true;
                }

                matches = true;
                for (int x = 0; x < BINGO_CARD_SIZE && matches; x++) {
                    matches = this.cards[card.getY()][x];
                }

                return matches;
            }

            return false;
        }

        public int getCardScore() {
            return this.remainingNumbers.stream().mapToInt(Integer::intValue).sum();
        }
    }
}
