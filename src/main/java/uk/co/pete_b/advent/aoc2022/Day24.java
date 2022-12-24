package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.*;
import java.util.stream.Collectors;

public class Day24 {

    public static int findQuickestRoute(final List<String> map) {
        final Set<Blizzard> blizzardMap = new LinkedHashSet<>();
        final int minX = 1;
        final int maxX = map.get(0).length() - 2;
        final int minY = 1;
        final int maxY = map.size() - 2;

        final Coordinate startingPoint = new Coordinate(minX, minY - 1);
        final Coordinate target = new Coordinate(maxX, maxY + 1);

        for (int y = minY; y <= maxY; y++) {
            final String line = map.get(y);
            for (int x = minX; x <= maxX; x++) {
                switch (line.charAt(x)) {
                    case '<' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.LEFT, minX, maxX, minY, maxY));
                    case '>' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.RIGHT, minX, maxX, minY, maxY));
                    case '^' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.UP, minX, maxX, minY, maxY));
                    case 'v' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.DOWN, minX, maxX, minY, maxY));
                }
            }
        }

        final WorldState worldState = buildBlizzardStates(blizzardMap, maxX, maxY);

        return getTime(worldState, startingPoint, target, 1);
    }

    public static int findQuickestRouteWhenReturningToCollectSnacks(final List<String> map) {
        final Set<Blizzard> blizzardMap = new LinkedHashSet<>();
        final int minX = 1;
        final int maxX = map.get(0).length() - 2;
        final int minY = 1;
        final int maxY = map.size() - 2;

        final Coordinate startingPoint = new Coordinate(minX, minY - 1);
        final Coordinate target = new Coordinate(maxX, maxY + 1);

        for (int y = minY; y <= maxY; y++) {
            final String line = map.get(y);
            for (int x = minX; x <= maxX; x++) {
                switch (line.charAt(x)) {
                    case '<' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.LEFT, minX, maxX, minY, maxY));
                    case '>' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.RIGHT, minX, maxX, minY, maxY));
                    case '^' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.UP, minX, maxX, minY, maxY));
                    case 'v' -> blizzardMap.add(new Blizzard(new Coordinate(x, y), Direction.DOWN, minX, maxX, minY, maxY));
                }
            }
        }

        final WorldState worldState = buildBlizzardStates(blizzardMap, maxX, maxY);

        final int leg1End = getTime(worldState, startingPoint, target, 1);
        final int leg2End = getTime(worldState, target, startingPoint, leg1End);
        return getTime(worldState, startingPoint, target, leg2End);
    }

    private static int getTime(final WorldState worldState, final Coordinate startingPoint, final Coordinate target, final int startingRound) {
        final List<Set<Coordinate>> blizzardStates = worldState.blizzardStates();
        final List<Coordinate> positions = new ArrayList<>();
        positions.add(startingPoint);
        int round = startingRound;
        while (!positions.isEmpty()) {
            round++;
            final Set<Coordinate> newPositions = new HashSet<>();
            Set<Coordinate> currentState = blizzardStates.get(round % blizzardStates.size());
            while (!positions.isEmpty()) {
                Coordinate position = positions.remove(0);
                final List<Coordinate> possiblePositions = getPossibleMoves(position, startingPoint, target, worldState.maxX, worldState.maxY);
                for (Coordinate newPosition : possiblePositions) {
                    if (newPosition.equals(target)) {
                        return round;
                    }

                    if (!currentState.contains(newPosition)) {
                        newPositions.add(newPosition);
                    }
                }

                if (!currentState.contains(position)) {
                    newPositions.add(position);
                }
            }

            positions.addAll(newPositions);
        }

        return -1;
    }

    private static WorldState buildBlizzardStates(Set<Blizzard> blizzardMap, int maxX, int maxY) {
        final List<Set<Blizzard>> states = new ArrayList<>();
        states.add(blizzardMap);

        // Blizzard maps loop on the lcm of width/height
        int n = lcm(maxX, maxY);
        for (int i = 1; i < n; i++) {
            final Set<Blizzard> nextState = new LinkedHashSet<>();
            states.get(states.size() - 1).forEach(blizzard -> nextState.add(new Blizzard(blizzard)));

            nextState.forEach(Blizzard::move);
            states.add(nextState);
        }

        final List<Set<Coordinate>> blizzardStates = new ArrayList<>();
        states.forEach(state -> blizzardStates.add(state.stream().map(x -> x.position).collect(Collectors.toSet())));

        return new WorldState(blizzardStates, maxX, maxY);
    }

    private static List<Coordinate> getPossibleMoves(final Coordinate position, final Coordinate start, final Coordinate target,
                                                     final int maxX, final int maxY) {
        final List<Coordinate> moves = new ArrayList<>();

        if (position.equals(start)) {
            if (start.getY() == 0) {
                return Collections.singletonList(position.down());
            } else {
                return Collections.singletonList(position.up());
            }
        }

        final Coordinate left = position.left();
        if (left.getX() >= 1) {
            moves.add(left);
        }

        final Coordinate right = position.right();
        if (right.getX() <= maxX) {
            moves.add(right);
        }

        final Coordinate up = position.up();
        if (up.getY() >= 1 || up.equals(target)) {
            moves.add(up);
        }

        final Coordinate down = position.down();
        if (down.getY() <= maxY || down.equals(target)) {
            moves.add(down);
        }

        return moves;
    }

    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        int absNumber1 = Math.abs(number1);
        int absNumber2 = Math.abs(number2);
        int absHigherNumber = Math.max(absNumber1, absNumber2);
        int absLowerNumber = Math.min(absNumber1, absNumber2);
        int lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    private static class Blizzard {

        private Coordinate position;

        private final Direction direction;
        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;

        public Blizzard(final Coordinate position, final Direction direction, final int minX, final int maxX, final int minY, final int maxY) {
            this.position = position;
            this.direction = direction;
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        public Blizzard(final Blizzard blizzard) {
            this.position = blizzard.position;
            this.direction = blizzard.direction;
            this.minX = blizzard.minX;
            this.maxX = blizzard.maxX;
            this.minY = blizzard.minY;
            this.maxY = blizzard.maxY;
        }

        public void move() {
            if (this.direction == Direction.LEFT) {
                this.position = this.position.left();
                if (this.position.getX() < this.minX) {
                    this.position = new Coordinate(this.maxX, this.position.getY());
                }
            } else if (this.direction == Direction.RIGHT) {
                this.position = this.position.right();
                if (this.position.getX() > this.maxX) {
                    this.position = new Coordinate(this.minX, this.position.getY());
                }
            } else if (this.direction == Direction.UP) {
                this.position = this.position.up();
                if (this.position.getY() < this.minY) {
                    this.position = new Coordinate(this.position.getX(), this.maxY);
                }
            } else if (this.direction == Direction.DOWN) {
                this.position = this.position.down();
                if (this.position.getY() > this.maxY) {
                    this.position = new Coordinate(this.position.getX(), this.minY);
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Blizzard blizzard = (Blizzard) o;

            return new EqualsBuilder().append(position, blizzard.position).append(direction, blizzard.direction).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(position).append(direction).toHashCode();
        }

        @Override
        public String toString() {
            return this.position + " " + this.direction;
        }
    }

    private record WorldState(List<Set<Coordinate>> blizzardStates, int maxX, int maxY) {

    }
}
