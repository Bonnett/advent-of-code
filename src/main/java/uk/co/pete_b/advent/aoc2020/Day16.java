package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    public static Answer calculateTicketErrorRate(final String ticketData, final String matchingRegex) {
        final String[] details = ticketData.split("\n");
        final List<Pair<String, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>>> rules = new ArrayList<>();

        final List<Integer> yourTicket = new ArrayList<>();
        final List<List<Integer>> nearbyTickets = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < details.length; lineIndex++) {
            String line = details[lineIndex];
            if (line.length() == 0) {
                continue;
            } else if (line.startsWith("your")) {
                lineIndex++;
                Arrays.stream(details[lineIndex].split(",")).mapToInt(Integer::parseInt).boxed().forEach(yourTicket::add);
                continue;
            } else if (line.startsWith("nearby")) {
                lineIndex++;
                for (; lineIndex < details.length; lineIndex++) {
                    nearbyTickets.add(Arrays.stream(details[lineIndex].split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
                }
                break;
            }

            final int colonPos = line.indexOf(": ");
            final String field = line.substring(0, colonPos);
            final Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> lineRules = createPair(line.substring(colonPos + 2).split(" or "));

            rules.add(Pair.of(field, lineRules));
        }

        int errorRate = 0;
        final Iterator<List<Integer>> ticketIterator = nearbyTickets.iterator();
        while (ticketIterator.hasNext()) {
            List<Integer> nearbyTicket = ticketIterator.next();
            boolean toBeRemoved = false;
            for (final Integer ticketValue : nearbyTicket) {
                boolean validForOneField = false;
                for (Pair<String, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> ticketRule : rules) {
                    if (checkFieldIsValid(ticketRule, ticketValue)) {
                        validForOneField = true;
                        break;
                    }
                }

                if (!validForOneField) {
                    errorRate += ticketValue;
                    toBeRemoved = true;
                }
            }

            if (toBeRemoved) {
                ticketIterator.remove();
            }
        }

        nearbyTickets.add(yourTicket);

        final Map<String, Set<Integer>> possibleRulePositionsFound = new HashMap<>();
        final Map<String, Integer> positionsFound = new HashMap<>();

        // Iterate through each rule
        for (final Pair<String, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> ticketRule : rules) {
            // Iterate through each ticket field
            for (int ticketField = 0; ticketField < yourTicket.size(); ticketField++) {
                final List<Integer> ticketsWithRuleMatches = new ArrayList<>();
                // Iterate through each ticket
                for (List<Integer> ticket : nearbyTickets) {
                    // Check if the field at position 'ticketField' matches the rule
                    if (checkFieldIsValid(ticketRule, ticket.get(ticketField))) {
                        ticketsWithRuleMatches.add(ticketField);
                    } else {
                        break;
                    }
                }

                // If the number of matching tickets is the same as the total number of tickets add this is a possible position for the field
                if (ticketsWithRuleMatches.size() == nearbyTickets.size()) {
                    final int fieldPosition = ticketField;
                    possibleRulePositionsFound.compute(ticketRule.getLeft(), (ruleName, possiblePositions) ->
                    {
                        if (possiblePositions == null) {
                            possiblePositions = new HashSet<>();
                        }

                        possiblePositions.add(fieldPosition);

                        return possiblePositions;
                    });
                }
            }
        }

        // Iterate until we've located where all the ticket fields go
        while (positionsFound.size() < yourTicket.size()) {
            int lastEntryFound = -1;
            final Iterator<Map.Entry<String, Set<Integer>>> ruleIterator = possibleRulePositionsFound.entrySet().iterator();
            // Find a ticket rule that can only go in one space, assign the rule to the position and remove it from the list of rules to locate
            while (ruleIterator.hasNext()) {
                final Map.Entry<String, Set<Integer>> entry = ruleIterator.next();
                if (entry.getValue().size() == 1) {
                    lastEntryFound = entry.getValue().iterator().next();
                    positionsFound.put(entry.getKey(), entry.getValue().iterator().next());
                    ruleIterator.remove();
                    break;
                }
            }

            // Remove the rule position we've located to avoid assigning it again
            for (Map.Entry<String, Set<Integer>> entry : possibleRulePositionsFound.entrySet()) {
                entry.getValue().remove(lastEntryFound);
            }
        }


        long yourTicketMultiplied = 1;
        for (Map.Entry<String, Integer> rule : positionsFound.entrySet()) {
            if (rule.getKey().matches(matchingRegex)) {
                yourTicketMultiplied *= yourTicket.get(rule.getValue());
            }
        }

        return new Answer(errorRate, yourTicketMultiplied);
    }

    private static boolean checkFieldIsValid(final Pair<String, Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> ticketRule, final Integer ticketValue) {
        final Pair<Integer, Integer> lowerBound = ticketRule.getRight().getLeft();
        final Pair<Integer, Integer> upperBound = ticketRule.getRight().getRight();

        return lowerBound.getLeft() <= ticketValue && ticketValue <= lowerBound.getRight() || upperBound.getLeft() <= ticketValue && ticketValue <= upperBound.getRight();
    }

    private static Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> createPair(final String[] split) {
        final Pair<Integer, Integer> lowerBound = Pair.of(Integer.parseInt(split[0].split("-")[0]), Integer.parseInt(split[0].split("-")[1]));
        final Pair<Integer, Integer> upperBound = Pair.of(Integer.parseInt(split[1].split("-")[0]), Integer.parseInt(split[1].split("-")[1]));

        return Pair.of(lowerBound, upperBound);
    }

    public static class Answer {
        private final int errorRate;
        private final long matchingFieldsMultiplied;

        Answer(int errorRate, long matchingFieldsMultiplied) {
            this.errorRate = errorRate;
            this.matchingFieldsMultiplied = matchingFieldsMultiplied;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).errorRate == this.errorRate && ((Answer) otherAnswer).matchingFieldsMultiplied == this.matchingFieldsMultiplied;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Error Rate: %d, Matching Fields Multiplied: %d", errorRate, matchingFieldsMultiplied);
        }
    }
}
