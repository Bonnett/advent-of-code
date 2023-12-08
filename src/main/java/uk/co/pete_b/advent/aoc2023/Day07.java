package uk.co.pete_b.advent.aoc2023;

import java.util.*;

public class Day07 {
    public static int calculateTotalWinnings(final List<String> camelCards) {
        int winnings = 0;
        final List<Hand> hands = new ArrayList<>();
        for (String cards : camelCards) {
            String[] handDetails = cards.split(" ");
            final int bid = Integer.parseInt(handDetails[1]);
            hands.add(new Hand(handDetails[0], bid));
        }

        hands.sort(Hand::compareTo);

        for (int i = 0; i < hands.size(); i++) {
            winnings += (i + 1) * hands.get(i).bid;
        }

        return winnings;
    }

    public static int calculateTotalWinningsWithJoker(final List<String> camelCards) {
        int winnings = 0;
        final List<Hand> hands = new ArrayList<>();
        for (String cards : camelCards) {
            String[] handDetails = cards.split(" ");
            final int bid = Integer.parseInt(handDetails[1]);
            hands.add(new Hand(handDetails[0], bid, true));
        }

        hands.sort(Hand::compareTo);

        for (int i = 0; i < hands.size(); i++) {
            winnings += (i + 1) * hands.get(i).bid;
        }

        return winnings;
    }

    enum HandType {
        FIVE_OF_A_KIND(1), FOUR_OF_A_KIND(2), FULL_HOUSE(3), THREE_OF_A_KIND(4), TWO_PAIR(5), ONE_PAIR(6), HIGH_CARD(7);
        final int rank;

        HandType(final int rank) {
            this.rank = rank;
        }
    }

    private static class Hand implements Comparable<Hand> {
        private static final Map<Character, Integer> CARD_RANKS = new HashMap<>();

        static {
            CARD_RANKS.put('A', 13);
            CARD_RANKS.put('K', 12);
            CARD_RANKS.put('Q', 11);
            CARD_RANKS.put('J', 10);
            CARD_RANKS.put('T', 9);
            CARD_RANKS.put('9', 8);
            CARD_RANKS.put('8', 7);
            CARD_RANKS.put('7', 6);
            CARD_RANKS.put('6', 5);
            CARD_RANKS.put('5', 4);
            CARD_RANKS.put('4', 3);
            CARD_RANKS.put('3', 2);
            CARD_RANKS.put('2', 1);
        }

        private static final Map<Character, Integer> CARD_RANKS_JOKER = new HashMap<>(CARD_RANKS);

        static {
            CARD_RANKS_JOKER.put('J', 0);
        }

        final int bid;
        final String cards;
        final HandType handType;
        final List<Integer> cardRanks = new ArrayList<>();

        public Hand(final String cards, final int bid) {
            this(cards, bid, false);
        }

        public Hand(final String cards, final int bid, final boolean jokerRuleApplied) {
            this.cards = cards;
            this.bid = bid;
            this.handType = calculateHandType(cards, jokerRuleApplied);
        }

        private HandType calculateHandType(final String cards, final boolean jokerRuleApplied) {
            char[] cardArray = cards.toCharArray();
            final Map<Character, Integer> hand = new HashMap<>();
            for (char card : cardArray) {
                if (jokerRuleApplied) {
                    cardRanks.add(CARD_RANKS_JOKER.get(card));
                } else {
                    cardRanks.add(CARD_RANKS.get(card));
                }
                hand.compute(card, (character, count) -> {
                    if (count == null) {
                        count = 0;
                    }
                    count++;

                    return count;
                });
            }

            if (jokerRuleApplied && hand.containsKey('J')) {
                final int jokers = hand.remove('J');
                if (jokers == 4 || jokers == 5) {
                    return HandType.FIVE_OF_A_KIND;
                } else if (jokers == 3) {
                    return (hand.size() == 1) ? HandType.FIVE_OF_A_KIND : HandType.FOUR_OF_A_KIND;
                } else if (jokers == 2) {
                    if (hand.size() == 3) {
                        return HandType.THREE_OF_A_KIND;
                    } else if (hand.size() == 2) {
                        return HandType.FOUR_OF_A_KIND;
                    } else {
                        return HandType.FIVE_OF_A_KIND;
                    }
                } else if (jokers == 1) {
                    if (hand.size() == 4) {
                        return HandType.ONE_PAIR;
                    } else if (hand.size() == 3) {
                        return HandType.THREE_OF_A_KIND;
                    } else if (hand.size() == 2) {
                        final Optional<Integer> threeOfAKind = hand.values().stream().filter(count -> count == 3).findAny();
                        return (threeOfAKind.isPresent()) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
                    } else {
                        return HandType.FIVE_OF_A_KIND;
                    }
                }
            } else {
                if (hand.size() == 1) {
                    return HandType.FIVE_OF_A_KIND;
                } else if (hand.size() == 2) {
                    final int count = hand.values().iterator().next();
                    return (count == 1 || count == 4) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
                } else if (hand.size() == 3) {
                    final Optional<Integer> threeOfAKind = hand.values().stream().filter(count -> count == 3).findAny();
                    return (threeOfAKind.isPresent()) ? HandType.THREE_OF_A_KIND : HandType.TWO_PAIR;
                } else if (hand.size() == 4) {
                    return HandType.ONE_PAIR;
                }
            }

            return HandType.HIGH_CARD;
        }

        @Override
        public int compareTo(Hand otherHand) {
            if (otherHand.handType.rank == this.handType.rank) {
                for (int i = 0; i < this.cards.length(); i++) {
                    if (this.cardRanks.get(i) > otherHand.cardRanks.get(i)) {
                        return 1;
                    } else if (this.cardRanks.get(i) < otherHand.cardRanks.get(i)) {
                        return -1;
                    }
                }

            } else {
                return Integer.compare(otherHand.handType.rank, this.handType.rank);
            }

            return 0;
        }
    }
}
