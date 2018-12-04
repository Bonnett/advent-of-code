package uk.co.pete_b.advent.aoc2018;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day04 {
    public static Answers getAnswers(final List<String> input) {
        input.sort(Comparator.comparing(record -> record.substring(0, 17)));

        final Map<Integer, List<Integer>> sleepLog = new HashMap<>();

        int id = -1;
        final AtomicInteger startSleep = new AtomicInteger(-1);

        final AtomicInteger mostSleptId = new AtomicInteger(-1);
        final AtomicInteger maxLength = new AtomicInteger(-1);

        for (String entry : input) {
            if (entry.contains("Guard")) {
                id = Integer.valueOf(entry.substring(26, entry.length() - 13));
            } else if (entry.contains("falls asleep")) {
                startSleep.set(Integer.valueOf(entry.substring(15, 17)));
            } else {
                final int wakesUp = Integer.valueOf(entry.substring(15, 17));
                sleepLog.compute(id, (key, value) -> {
                    if (value == null) {
                        value = new ArrayList<>();
                    }

                    for (int i = startSleep.get(); i < wakesUp; i++) {
                        value.add(i);
                    }

                    if (value.size() > maxLength.get()) {
                        maxLength.set(value.size());
                        mostSleptId.set(key);
                    }

                    return value;
                });
            }
        }

        int guardWithMostCommonMinute = -1;
        int guardsMostCommonMinuteCount = -1;
        int guardsMostCommonMinute = -1;

        final Map<Integer, Integer> mostCommonMinutes = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : sleepLog.entrySet()) {
            final AtomicInteger mostCommonMinute = new AtomicInteger(-1);
            final AtomicInteger highestValue = new AtomicInteger(-1);

            final Map<Integer, Integer> minuteMap = new HashMap<>();
            for (Integer minute : entry.getValue()) {
                minuteMap.compute(minute, (key, value) -> {
                    if (value == null) {
                        value = 1;
                    } else {
                        value++;
                    }

                    if (value > highestValue.get()) {
                        highestValue.set(value);
                        mostCommonMinute.set(key);
                    }
                    return value;
                });
            }

            if (highestValue.get() > guardsMostCommonMinuteCount) {
                guardsMostCommonMinuteCount = highestValue.get();
                guardsMostCommonMinute = mostCommonMinute.get();
                guardWithMostCommonMinute = entry.getKey();
            }

            mostCommonMinutes.put(entry.getKey(), mostCommonMinute.get());
        }

        return new Answers(mostSleptId.get() * mostCommonMinutes.get(mostSleptId.get()), guardWithMostCommonMinute * guardsMostCommonMinute);
    }

    public static class Answers {
        private final int strategyOneAnswer;
        private final int strategyTwoAnswer;

        Answers(final int strategyOneAnswer, final int strategyTwoAnswer) {
            this.strategyOneAnswer = strategyOneAnswer;
            this.strategyTwoAnswer = strategyTwoAnswer;
        }

        int getStrategyOneAnswer() {
            return strategyOneAnswer;
        }

        int getStrategyTwoAnswer() {
            return strategyTwoAnswer;
        }
    }
}
