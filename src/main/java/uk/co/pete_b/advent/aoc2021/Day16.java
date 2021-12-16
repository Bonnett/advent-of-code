package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Day16 {
    public static Answer sumVersionNumbers(final String packet) {
        final StringBuilder binary = new StringBuilder();
        for (int i = 0; i < packet.length(); i++) {
            binary.append(String.format("%1$4s", Integer.toBinaryString(Integer.parseInt(packet.substring(i, i + 1), 16))).replaceAll(" ", "0"));
        }

        final Pair<Integer, List<Long>> answer = calculateTotal(binary);
        return new Answer(answer.getLeft(), answer.getRight().get(0));
    }

    private static Pair<Integer, List<Long>> calculateTotal(final StringBuilder binary) {
        int total = 0;
        final List<Long> output = new ArrayList<>();

        final long packetVersion = Long.parseLong(getValue(binary, 3), 2);
        total += packetVersion;

        final long type = Long.parseLong(getValue(binary, 3), 2);
        if (type == 4) {
            final StringBuilder nextNum = new StringBuilder();
            while (binary.charAt(0) == '1') {
                nextNum.append(getValue(binary, 5), 1, 5);
            }
            nextNum.append(getValue(binary, 5), 1, 5);
            final long nextNumber = Long.parseLong(nextNum.toString(), 2);
            output.add(nextNumber);
        } else {
            final List<Long> numbersToOperateOn = new ArrayList<>();
            final String lengthType = getValue(binary, 1);
            if (lengthType.equals("0")) {
                final int length = Integer.parseInt(getValue(binary, 15), 2);
                final StringBuilder nextPackets = new StringBuilder(getValue(binary, length));
                while (!nextPackets.isEmpty()) {
                    final Pair<Integer, List<Long>> values = calculateTotal(nextPackets);
                    numbersToOperateOn.addAll(values.getRight());
                    total += values.getLeft();
                }
            } else {
                final long numPackets = Long.parseLong(getValue(binary, 11), 2);
                for (int i = 0; i < numPackets; i++) {
                    final Pair<Integer, List<Long>> values = calculateTotal(binary);
                    numbersToOperateOn.addAll(values.getRight());
                    total += values.getLeft();
                }
            }

            if (type == 0) {
                output.add(numbersToOperateOn.stream().mapToLong(Long::valueOf).sum());
            } else if (type == 1) {
                output.add(numbersToOperateOn.stream().mapToLong(value -> value).reduce(1, (a, b) -> a * b));
            } else if (type == 2) {
                output.add(numbersToOperateOn.stream().mapToLong(value -> value).min().orElseThrow());
            } else if (type == 3) {
                output.add(numbersToOperateOn.stream().mapToLong(value -> value).max().orElseThrow());
            } else if (type == 5) {
                output.add(numbersToOperateOn.get(0) > numbersToOperateOn.get(1) ? 1L : 0L);
            } else if (type == 6) {
                output.add(numbersToOperateOn.get(0) < numbersToOperateOn.get(1) ? 1L : 0L);
            } else if (type == 7) {
                output.add(numbersToOperateOn.get(0).longValue() == numbersToOperateOn.get(1).longValue() ? 1L : 0L);
            }
        }

        return Pair.of(total, output);
    }

    private static String getValue(final StringBuilder builder, final int length) {
        final String output = builder.substring(0, length);
        builder.delete(0, length);
        return output;
    }

    public record Answer(int versionTotal, long output) {
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).versionTotal == this.versionTotal && ((Answer) otherAnswer).output == this.output;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Sum of Version Numbers %d, Expression Output: %d", versionTotal, output);
        }
    }
}
