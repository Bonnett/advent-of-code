package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 {
    private static final Pattern MATCHER = Pattern.compile("^p=<([^>]+)>, v=<([^>]+)>, a=<([^>]+)>$");

    public static int getClosestParticle(final String input) {
        final String[] lines = input.split("\r?\n");
        final Map<Integer, Particle> particles = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            final Matcher match = MATCHER.matcher(lines[i]);
            if (match.find()) {
                final String position = match.group(1);
                final String velocity = match.group(2);
                final String acceleration = match.group(3);
                final Particle p = new Particle(position, velocity, acceleration);
                particles.put(i, p);
                p.advance(600);
            }
        }

        int closest = -1;
        double lowestAverage = Double.MAX_VALUE;
        for (Map.Entry<Integer, Particle> entry : particles.entrySet()) {
            final double average = entry.getValue().getAverageManhattan();
            if (average < lowestAverage) {
                lowestAverage = average;
                closest = entry.getKey();
            }
        }

        return closest;
    }

    public static int howManyLeft(final String input) {
        final String[] lines = input.split("\r?\n");
        final Map<Integer, Particle> particles = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            final Matcher match = MATCHER.matcher(lines[i]);
            if (match.find()) {
                final String position = match.group(1);
                final String velocity = match.group(2);
                final String acceleration = match.group(3);
                final Particle p = new Particle(position, velocity, acceleration);
                particles.put(i, p);
            }
        }

        for (int i = 0; i < 40; i++) {
            final Map<Position, List<Integer>> positions = new HashMap<>();
            for (Map.Entry<Integer, Particle> entry : particles.entrySet()) {
                final Position pos = entry.getValue().position;
                if (positions.containsKey(pos)) {
                    positions.get(pos).add(entry.getKey());
                } else {
                    positions.put(pos, new ArrayList<>(Collections.singletonList(entry.getKey())));
                }
            }

            for (Map.Entry<Position, List<Integer>> entry : positions.entrySet()) {
                if (entry.getValue().size() > 1) {
                    for (Integer key : entry.getValue()) {
                        particles.remove(key);
                    }
                }
            }

            for (Map.Entry<Integer, Particle> entry : particles.entrySet()) {
                entry.getValue().advance(1);
            }
        }

        return particles.size();
    }

    private static class Particle {
        private Position position;

        private long velocityX;
        private long velocityY;
        private long velocityZ;

        private long accelerationX;
        private long accelerationY;
        private long accelerationZ;

        long currentManhattan = -1;
        long totalManhattan = -1;
        long steps = 0;

        Particle(final String position, final String velocity, final String acceleration) {
            this.position = new Position(position);

            final String[] velocities = velocity.split(",");
            this.velocityX = Long.valueOf(velocities[0].trim());
            this.velocityY = Long.valueOf(velocities[1].trim());
            this.velocityZ = Long.valueOf(velocities[2].trim());

            final String[] accelerations = acceleration.split(",");
            this.accelerationX = Long.valueOf(accelerations[0].trim());
            this.accelerationY = Long.valueOf(accelerations[1].trim());
            this.accelerationZ = Long.valueOf(accelerations[2].trim());

            updateManhattan();
        }

        double getAverageManhattan() {
            return ((double) totalManhattan) / steps;
        }

        void advance(final int numOfSteps) {
            for (int i = 0; i < numOfSteps; i++) {
                velocityX += accelerationX;
                velocityY += accelerationY;
                velocityZ += accelerationZ;

                position.x += velocityX;
                position.y += velocityY;
                position.z += velocityZ;

                updateManhattan();
            }
        }

        private void updateManhattan() {
            steps++;
            currentManhattan = Math.abs(position.x) + Math.abs(position.y) + Math.abs(position.z);
            totalManhattan += currentManhattan;
        }
    }

    private static class Position {

        private long x;
        private long y;
        private long z;

        Position(final String position) {
            final String[] positions = position.split(",");
            x = Long.valueOf(positions[0].trim());
            y = Long.valueOf(positions[1].trim());
            z = Long.valueOf(positions[2].trim());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            return new EqualsBuilder()
                    .append(x, position.x)
                    .append(y, position.y)
                    .append(z, position.z)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(x)
                    .append(y)
                    .append(z)
                    .toHashCode();
        }
    }
}
