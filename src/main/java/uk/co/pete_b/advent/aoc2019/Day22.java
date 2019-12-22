package uk.co.pete_b.advent.aoc2019;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class Day22 {

    public static int[] shuffleDeck(final int numberOfCards, final List<String> operations) {
        int[] deck = new int[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            deck[i] = i;
        }
        for (final String operation : operations) {
            if (operation.equals("deal into new stack")) {
                ArrayUtils.reverse(deck);
            } else if (operation.startsWith("cut ")) {
                int cutIndex = Integer.parseInt(operation.split(" ")[1]);

                if (cutIndex < 0) {
                    cutIndex = numberOfCards - Math.abs(cutIndex);
                }

                int[] bottomDeck = Arrays.copyOfRange(deck, 0, cutIndex);
                int[] topDeck = Arrays.copyOfRange(deck, cutIndex, numberOfCards);
                deck = ArrayUtils.addAll(topDeck, bottomDeck);
            } else if (operation.startsWith("deal with increment ")) {
                final int increment = Integer.parseInt(operation.substring(operation.lastIndexOf(" ") + 1));
                final int[] newDeck = new int[numberOfCards];
                for (int i = 0; i < numberOfCards; i++) {
                    newDeck[(i * increment) % numberOfCards] = deck[i];
                }
                deck = newDeck;
            }
        }

        return deck;
    }
}
