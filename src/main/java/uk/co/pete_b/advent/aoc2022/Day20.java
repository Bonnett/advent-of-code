package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class Day20 {
    public static long sumGroveCoordinates(final List<String> encryptedInput, final long multiplier, final int iteration) {
        final List<Num> numberPositions = new ArrayList<>();
        final List<Num> numbers = new ArrayList<>();

        Num zeroth = null;

        for (int i = 0; i < encryptedInput.size(); i++) {
            long num = Long.parseLong(encryptedInput.get(i)) * multiplier;
            Num number = new Num(num, i);
            numberPositions.add(number);
            numbers.add(number);
            if (num == 0) {
                zeroth = number;
            }
        }

        if (zeroth == null) {
            throw new IllegalStateException("No zero value found");
        }

        for (int i = 0; i < iteration; i++) {
            for (Num number : numberPositions) {
                long num = number.value;
                int numPos = numbers.indexOf(number);
                long newNumPos = numPos + num;

                numbers.remove(numPos);

                newNumPos = newNumPos % numbers.size();
                if (newNumPos < 0) {
                    newNumPos += numbers.size();
                }

                numbers.add((int) newNumPos, number);
            }
        }

        final int zerothPosition = numbers.indexOf(zeroth);
        int oneThousandthIndex = (zerothPosition + 1000) % numbers.size();
        int twoThousandthIndex = (zerothPosition + 2000) % numbers.size();
        int threeThousandthIndex = (zerothPosition + 3000) % numbers.size();
        long oneThousandth = numbers.get(oneThousandthIndex).value;
        long twoThousandth = numbers.get(twoThousandthIndex).value;
        long threeThousandth = numbers.get(threeThousandthIndex).value;

        return oneThousandth + twoThousandth + threeThousandth;
    }

    private record Num(long value, int initialPosition) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Num thing = (Num) o;

            return new EqualsBuilder().append(value, thing.value).append(initialPosition, thing.initialPosition).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(value).append(initialPosition).toHashCode();
        }
    }
}
