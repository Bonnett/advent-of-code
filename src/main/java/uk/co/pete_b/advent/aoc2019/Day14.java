package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    public static long calculateFuel(final List<String> reactions) {
        final Map<String, Reaction> nanoFactory = createNanoFactory(reactions);

        return getRawIngredients(nanoFactory, new HashMap<>(), "FUEL", 1);
    }

    public static long calculateNumberOfIterations(final List<String> reactions) {
        final Map<String, Reaction> nanoFactory = createNanoFactory(reactions);

        long iterations = 0;

        long lowerBound = 0;
        long upperBound = 100;
        long target = 1000000000000L;

        // Work out where to start
        while (getRawIngredients(nanoFactory, new HashMap<>(), "FUEL", upperBound) < target) {
            lowerBound = upperBound;
            upperBound *= 10;
        }

        while (lowerBound != upperBound) {
            iterations = (upperBound - lowerBound) / 2 + lowerBound;
            if (getRawIngredients(nanoFactory, new HashMap<>(), "FUEL", iterations) <= target) {
                lowerBound = iterations;
            } else {
                upperBound = iterations;
            }

            if (upperBound - lowerBound == 1)
            {
                iterations = lowerBound;
                break;
            }
        }

        return iterations;
    }

    private static long getRawIngredients(final Map<String, Reaction> nanoFactory, final Map<String, Long> production, final String chemicalName, final long amountNeeded) {
        long totalOreUsed = 0;
        final Reaction reaction = nanoFactory.get(chemicalName);
        final long multiplier = (long) Math.ceil((double) amountNeeded / reaction.getOutput().getAmount());
        for (final Chemical input : reaction.getInputs()) {
            if (input.getName().equals("ORE")) {
                totalOreUsed += multiplier * input.getAmount();
            } else {
                if (!production.containsKey(input.getName())) {
                    production.put(input.getName(), 0L);
                }

                if (production.get(input.getName()) < multiplier * input.getAmount()) {
                    totalOreUsed += getRawIngredients(nanoFactory, production, input.getName(), (multiplier * input.getAmount()) - production.get(input.getName()));
                }

                production.compute(input.getName(), (name, amount) -> {
                    if (amount == null) {
                        amount = 0L;
                    }
                    return amount - multiplier * input.amount;
                });
            }
        }

        production.compute(reaction.getOutput().getName(), (name, amount) -> {
            if (amount == null) {
                amount = 0L;
            }

            return amount + multiplier * reaction.getOutput().getAmount();
        });

        return totalOreUsed;
    }

    private static Map<String, Reaction> createNanoFactory(List<String> reactions) {
        final Map<String, Reaction> nanoFactory = new HashMap<>();
        for (final String reactionStr : reactions) {
            final String[] parts = reactionStr.split(" => ");
            final Chemical output = new Chemical(parts[1]);
            final List<Chemical> inputs = new ArrayList<>();
            for (final String input : parts[0].split(", ")) {
                inputs.add(new Chemical(input));
            }
            nanoFactory.put(output.getName(), new Reaction(output, inputs));
        }
        return nanoFactory;
    }

    private static class Reaction {
        private final Chemical output;
        private final List<Chemical> inputs;

        private Reaction(final Chemical output, final List<Chemical> inputs) {
            this.output = output;
            this.inputs = inputs;
        }

        public Chemical getOutput() {
            return output;
        }

        public List<Chemical> getInputs() {
            return inputs;
        }
    }

    private static class Chemical {
        private final String name;
        private final long amount;

        private Chemical(final String input) {
            final String[] parts = input.split(" ");
            this.name = parts[1];
            this.amount = Long.parseLong(parts[0]);
        }

        public String getName() {
            return name;
        }

        public long getAmount() {
            return amount;
        }
    }
}
