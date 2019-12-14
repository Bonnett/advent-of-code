package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Day07 {
    public static long findMaximumSignal(final List<Long> operations) {
        final List<int[]> permutations = calculatePermutations(0, 4);
        return permutations.parallelStream().mapToLong(p -> calculateSignal(operations, p, false)).max().orElse(-1);
    }

    public static long findMaximumSignalWithFeedback(final List<Long> operations) {
        final List<int[]> permutations = calculatePermutations(5, 9);
        return permutations.parallelStream().mapToLong(p -> calculateSignal(operations, p, true)).max().orElse(-1);
    }

    private static long calculateSignal(final List<Long> operations, final int[] input, final boolean withFeedback) {
        try {
            final List<BlockingQueue<Long>> queues = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                queues.add(new ArrayBlockingQueue<>(2));
            }

            if (withFeedback) {
                queues.remove(queues.size() - 1);
                queues.add(queues.size(), queues.get(0));
            }

            final ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 5; i++) {
                final int index = i;
                queues.get(i).add((long) input[i]);
                executor.execute(new OpCodeComputer(operations, () -> {
                    try {
                        return queues.get(index).take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, (value) -> queues.get(index + 1).add(value)));
            }

            queues.get(0).add((long) 0);
            executor.shutdown();
            executor.awaitTermination(2L, TimeUnit.SECONDS);

            return queues.get(queues.size() - 1).take();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static List<int[]> calculatePermutations(final int startIndex, final int endIndex) {
        final List<int[]> permutations = new ArrayList<>();
        for (int digitOne = startIndex; digitOne <= endIndex; digitOne++) {
            for (int digitTwo = startIndex; digitTwo <= endIndex; digitTwo++) {
                if (digitOne == digitTwo) {
                    continue;
                }
                for (int digitThree = startIndex; digitThree <= endIndex; digitThree++) {
                    if (digitOne == digitThree || digitTwo == digitThree) {
                        continue;
                    }
                    for (int digitFour = startIndex; digitFour <= endIndex; digitFour++) {
                        if (digitOne == digitFour || digitTwo == digitFour || digitThree == digitFour) {
                            continue;
                        }
                        for (int digitFive = startIndex; digitFive <= endIndex; digitFive++) {
                            if (digitOne == digitFive || digitTwo == digitFive || digitThree == digitFive || digitFour == digitFive) {
                                continue;
                            }
                            permutations.add(new int[]{digitOne, digitTwo, digitThree, digitFour, digitFive});
                        }
                    }
                }
            }
        }

        return permutations;
    }
}
