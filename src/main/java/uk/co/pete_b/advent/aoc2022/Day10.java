package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Day10 {
    public static Answer sumSignalStrengths(final List<String> operations) {
        final StringBuilder sb = new StringBuilder();
        int signalStrength = 0;
        int cycleCount = 0;
        int registerX = 1;
        for (String operation : operations) {
            sb.append(drawScreen(cycleCount, registerX));
            if (!operation.equals("noop")) {
                cycleCount++;
                if ((cycleCount - 20) % 40 == 0) {
                    signalStrength += (cycleCount * registerX);
                }
                sb.append(drawScreen(cycleCount, registerX));
                cycleCount++;
                if ((cycleCount - 20) % 40 == 0) {
                    signalStrength += (cycleCount * registerX);
                }
                registerX += Integer.parseInt(operation.substring(5));
            } else {
                cycleCount++;
                if ((cycleCount - 20) % 40 == 0) {
                    signalStrength += (cycleCount * registerX);
                }
            }
        }

        return new Answer(signalStrength, sb.toString().trim());
    }

    private static String drawScreen(final int cycleCount, final int registerX) {
        final int curPos = cycleCount % 40;
        String newLine = "";
        if (curPos == 39) {
            newLine = "\n";
        }
        if (registerX == curPos || Math.abs(curPos - registerX) == 1) {
            return "#" + newLine;
        } else {
            return "." + newLine;
        }
    }

    public static class Answer {
        private final int signalStrength;
        private final String screenDisplay;

        Answer(final int signalStrength, final String screenDisplay) {
            this.signalStrength = signalStrength;
            this.screenDisplay = screenDisplay;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).signalStrength == this.signalStrength && ((Answer) otherAnswer).screenDisplay.equals(this.screenDisplay);
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Signal Strength: %d, Screen Display:\r\n%s", signalStrength, screenDisplay);
        }
    }
}
