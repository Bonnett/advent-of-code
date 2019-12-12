package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day12 {
    private static final Pattern LINE_PATTERN = Pattern.compile("<x=*([^,]+), *y=([^,]+), *z=([^,]+)>");

    public static int calculateTotalEnergy(final List<String> inputMoons, final int inputSteps) {
        final List<Moon> moons = parseMoons(inputMoons);

        for (int i = 0; i < inputSteps; i++) {
            updateVelocity(moons);
            moons.forEach(Moon::move);
        }

        return moons.stream().mapToInt(Moon::getEnergy).sum();
    }

    public static long calculateSolarSystemPeriod(final List<String> inputMoons) {
        final List<Moon> moons = parseMoons(inputMoons);

        // Get the starting positions & velocities
        final List<Coordinate> startingX = moons.stream().map(moon -> new Coordinate(moon.getPosition().getX(), moon.getVelocity().getX())).collect(Collectors.toList());
        final List<Coordinate> startingY = moons.stream().map(moon -> new Coordinate(moon.getPosition().getY(), moon.getVelocity().getY())).collect(Collectors.toList());
        final List<Coordinate> startingZ = moons.stream().map(moon -> new Coordinate(moon.getPosition().getZ(), moon.getVelocity().getZ())).collect(Collectors.toList());

        long movesMadeX = 0;
        long movesMadeY = 0;
        long movesMadeZ = 0;

        long steps = 0;

        while (movesMadeX == 0 || movesMadeY == 0 || movesMadeZ == 0) {
            steps++;
            updateVelocity(moons);
            moons.forEach(Moon::move);

            // Find the period where all the X positions are the same
            if (movesMadeX == 0) {
                final List<Coordinate> currentX = moons.stream().map(moon -> new Coordinate(moon.getPosition().getX(), moon.getVelocity().getX())).collect(Collectors.toList());
                if (currentX.equals(startingX)) {
                    movesMadeX = steps;
                }
            }

            // Find the period where all the Y positions are the same
            if (movesMadeY == 0) {
                final List<Coordinate> currentY = moons.stream().map(moon -> new Coordinate(moon.getPosition().getY(), moon.getVelocity().getY())).collect(Collectors.toList());
                if (currentY.equals(startingY)) {
                    movesMadeY = steps;
                }
            }

            // Find the period where all the Z positions are the same
            if (movesMadeZ == 0) {
                final List<Coordinate> currentZ = moons.stream().map(moon -> new Coordinate(moon.getPosition().getZ(), moon.getVelocity().getZ())).collect(Collectors.toList());
                if (currentZ.equals(startingZ)) {
                    movesMadeZ = steps;
                }
            }
        }

        // Solution is the lowest common multiple of all x, y, z periods
        return lcm(movesMadeX, movesMadeY, movesMadeZ);
    }

    private static void updateVelocity(final List<Moon> moons) {
        for (int i = 0; i < moons.size(); i++) {
            final Moon moon = moons.get(i);
            for (int j = i; j < moons.size(); j++) {
                final Moon target = moons.get(j);
                final int x = Integer.compare(target.getPosition().getX(), moon.getPosition().getX());
                final int y = Integer.compare(target.getPosition().getY(), moon.getPosition().getY());
                final int z = Integer.compare(target.getPosition().getZ(), moon.getPosition().getZ());

                // Move both - target needs to be the reverse of this moon
                moon.getVelocity().move(x, y, z);
                target.getVelocity().move(-x, -y, -z);
            }
        }
    }

    private static List<Moon> parseMoons(final List<String> inputMoons) {
        final List<Moon> moons = new ArrayList<>();
        for (final String input : inputMoons) {
            final Matcher matcher = LINE_PATTERN.matcher(input);

            if (matcher.matches()) {
                final int x = Integer.parseInt(matcher.group(1));
                final int y = Integer.parseInt(matcher.group(2));
                final int z = Integer.parseInt(matcher.group(3));
                moons.add(new Moon(new Coordinate3D(x, y, z), new Coordinate3D(0, 0, 0)));
            }
        }

        return moons;
    }

    public static long lcm(long... numbers) {
        return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    private static class Moon {
        private final Coordinate3D position;
        private final Coordinate3D velocity;

        public Moon(final Coordinate3D position, final Coordinate3D velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public Coordinate3D getPosition() {
            return this.position;
        }

        public Coordinate3D getVelocity() {
            return this.velocity;
        }

        public void move() {
            this.position.move(this.velocity);
        }

        public int getEnergy() {
            final int potentialEnergy = Math.abs(this.position.getX()) + Math.abs(this.position.getY()) + Math.abs(this.position.getZ());
            final int kineticEnergy = Math.abs(this.velocity.getX()) + Math.abs(this.velocity.getY()) + Math.abs(this.velocity.getZ());
            return potentialEnergy * kineticEnergy;
        }

        @Override
        public String toString() {
            return String.format("pos=<%s>, vel<%s>", position, velocity);
        }
    }
}
