package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Day17 {
    public static Answer calculateHighestElevation(final String inputRules) {
        final Set<Coordinate> targetArea = new HashSet<>();

        final String[] parts = inputRules.substring(13).split(", ");
        final String[] minMaxX = parts[0].substring(2).split("\\.\\.");
        final int startX = Integer.parseInt(minMaxX[0]);
        final int endX = Integer.parseInt(minMaxX[1]);
        final String[] minMaxY = parts[1].substring(2).split("\\.\\.");
        final int startY = Integer.parseInt(minMaxY[0]);
        final int endY = Integer.parseInt(minMaxY[1]);
        final int maxX = Math.max(startX, endX);
        final int minY = Math.min(startY, endY);

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                targetArea.add(new Coordinate(x, y));
            }
        }

        int maxHeight = Integer.MIN_VALUE;
        int totalThatHit = 0;

        for (int x = 0; x <= maxX; x++) {
            for (int y = minY; y < Math.abs(minY); y++) {
                boolean isContained = false;
                int velX = x;
                int velY = y;
                int posX = 0;
                int posY = 0;
                int maxHeightThisTime = 0;

                while (true) {
                    posX += velX;
                    posY += velY;
                    if (velX > 0) {
                        velX--;
                    } else if (velX < 0) {
                        velX++;
                    }
                    velY--;

                    if (maxHeightThisTime < posY) {
                        maxHeightThisTime = posY;
                    }

                    if (targetArea.contains(new Coordinate(posX, posY))) {
                        isContained = true;
                        break;
                    } else if (posX > maxX || posY < minY) {
                        break;
                    }
                }
                if (isContained) {
                    if (maxHeight < maxHeightThisTime) {
                        maxHeight = maxHeightThisTime;
                    }
                    totalThatHit++;
                }
            }
        }

        return new Answer(maxHeight, totalThatHit);
    }

    public record Answer(int maxHeight, int totalHits) {
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).maxHeight == this.maxHeight && ((Answer) otherAnswer).totalHits == this.totalHits;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Max Height: %d, Total that hit: %d", maxHeight, totalHits);
        }
    }
}
