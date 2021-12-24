package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

public class Day23 {
    private static final List<String> EXTRA_DATA = Arrays.asList("  #D#C#B#A#", "  #D#B#A#C#");

    public static int calculateMinimumScore(final List<String> input, boolean withExtra) {
        final Burrow startBurrow = new Burrow(input, withExtra);

        final PriorityQueue<Burrow> queue = new PriorityQueue<>(Comparator.comparing(Burrow::getEnergyUsed));
        queue.add(startBurrow);
        final Map<Map<Coordinate, AmphipodType>, Integer> energyMap = new HashMap<>();

        List<Integer> energies = new ArrayList<>();

        while (!queue.isEmpty()) {
            final Burrow burrow = queue.poll();

            if (burrow.allPlaced()) {
                energies.add(burrow.getEnergyUsed());
                continue;
            }

            if (energyMap.containsKey(burrow.amphipodsLocations) && energyMap.get(burrow.amphipodsLocations) <= burrow.energyUsed) {
                continue;
            }

            energyMap.put(burrow.amphipodsLocations, burrow.energyUsed);

            queue.addAll(burrow.getAllPossibleNextMoves());
        }

        return energies.stream().min(Integer::compareTo).orElse(-1);
    }

    private enum AmphipodType {
        AMBER("A", 1), BRONZE("B", 10), COOPER("C", 100), DESERT("D", 1000);

        final String letter;
        final int energy;

        AmphipodType(final String letter, final int energy) {
            this.letter = letter;
            this.energy = energy;
        }

        @Override
        public String toString() {
            return letter;
        }
    }

    private enum TileType {
        OPEN_SPACE("."), WALL("#"), BLANK(" ");

        final String tile;

        TileType(final String tile) {
            this.tile = tile;
        }

        @Override
        public String toString() {
            return tile;
        }
    }

    private static class Burrow {
        private static final Set<Coordinate> HALLWAY = new LinkedHashSet<>() {{
            for (int i = 1; i <= 11; i++) {
                this.add(new Coordinate(i, 1));
            }
        }};

        private static final Set<Coordinate> NON_STOPPING_SPACES = new LinkedHashSet<>() {{
            this.add(new Coordinate(3, 1));
            this.add(new Coordinate(5, 1));
            this.add(new Coordinate(7, 1));
            this.add(new Coordinate(9, 1));
        }};

        private final Map<Coordinate, TileType> burrow;
        private final Map<Coordinate, AmphipodType> amphipodsLocations;
        private final Set<Coordinate> amphipodsPlaced;

        private final Map<AmphipodType, List<Coordinate>> correctLocations;

        private final boolean withExtra;

        private int energyUsed = 0;

        public Burrow(Burrow previousBurrow) {
            this.burrow = previousBurrow.burrow;
            this.energyUsed = previousBurrow.energyUsed;
            this.correctLocations = previousBurrow.correctLocations;
            this.withExtra = previousBurrow.withExtra;

            this.amphipodsLocations = new HashMap<>(previousBurrow.amphipodsLocations);
            this.amphipodsPlaced = new HashSet<>(previousBurrow.amphipodsPlaced);
        }

        public Burrow(List<String> map, boolean withExtra) {
            List<String> input = new ArrayList<>();
            if (withExtra) {
                input.addAll(map.subList(0, 3));
                input.addAll(EXTRA_DATA);
                input.addAll(map.subList(3, 5));
            } else {
                input.addAll(map);
            }

            this.withExtra = withExtra;
            this.burrow = new HashMap<>();
            this.amphipodsLocations = new HashMap<>();
            this.amphipodsPlaced = new HashSet<>();

            this.correctLocations = new HashMap<>();
            if (withExtra) {
                this.correctLocations.put(AmphipodType.AMBER, Arrays.asList(new Coordinate(3, 5), new Coordinate(3, 4), new Coordinate(3, 3), new Coordinate(3, 2)));
                this.correctLocations.put(AmphipodType.BRONZE, Arrays.asList(new Coordinate(5, 5), new Coordinate(5, 4), new Coordinate(5, 3), new Coordinate(5, 2)));
                this.correctLocations.put(AmphipodType.COOPER, Arrays.asList(new Coordinate(7, 5), new Coordinate(7, 4), new Coordinate(7, 3), new Coordinate(7, 2)));
                this.correctLocations.put(AmphipodType.DESERT, Arrays.asList(new Coordinate(9, 5), new Coordinate(9, 4), new Coordinate(9, 3), new Coordinate(9, 2)));
            } else {
                this.correctLocations.put(AmphipodType.AMBER, Arrays.asList(new Coordinate(3, 3), new Coordinate(3, 2)));
                this.correctLocations.put(AmphipodType.BRONZE, Arrays.asList(new Coordinate(5, 3), new Coordinate(5, 2)));
                this.correctLocations.put(AmphipodType.COOPER, Arrays.asList(new Coordinate(7, 3), new Coordinate(7, 2)));
                this.correctLocations.put(AmphipodType.DESERT, Arrays.asList(new Coordinate(9, 3), new Coordinate(9, 2)));
            }

            for (int y = 0; y < input.size(); y++) {
                final String line = input.get(y);
                for (int x = 0; x < line.length(); x++) {
                    final char c = line.charAt(x);
                    final Coordinate point = new Coordinate(x, y);
                    final TileType tile;
                    switch (c) {
                        case ' ' -> tile = TileType.BLANK;
                        case '#' -> tile = TileType.WALL;
                        case '.' -> tile = TileType.OPEN_SPACE;
                        case 'A' -> {
                            this.amphipodsLocations.put(point, AmphipodType.AMBER);
                            tile = TileType.OPEN_SPACE;
                        }
                        case 'B' -> {
                            this.amphipodsLocations.put(point, AmphipodType.BRONZE);
                            tile = TileType.OPEN_SPACE;
                        }
                        case 'C' -> {
                            this.amphipodsLocations.put(point, AmphipodType.COOPER);
                            tile = TileType.OPEN_SPACE;
                        }
                        case 'D' -> {
                            this.amphipodsLocations.put(point, AmphipodType.DESERT);
                            tile = TileType.OPEN_SPACE;
                        }
                        default -> throw new IllegalStateException(String.format("Unrecognised symbol '%c'", c));
                    }

                    this.burrow.put(point, tile);
                }
            }

            for (Map.Entry<Coordinate, AmphipodType> amphipod : this.amphipodsLocations.entrySet()) {
                if (isAmphipodInPlace(amphipod.getKey(), amphipod.getValue())) {
                    this.amphipodsPlaced.add(amphipod.getKey());
                }
            }
        }

        private boolean isAmphipodInPlace(Coordinate position, AmphipodType amphipod) {
            final List<Coordinate> locations = this.correctLocations.get(amphipod);

            boolean correct = false;
            for (Coordinate location : locations) {
                if (location.equals(position)) {
                    correct = true;
                    break;
                }
                if (this.amphipodsLocations.containsKey(location) && this.amphipodsLocations.get(location) != amphipod) {
                    break;
                }
            }

            return correct;
        }

        public void moveAmphipod(Coordinate from, Coordinate to) {
            final AmphipodType type = this.amphipodsLocations.remove(from);
            this.amphipodsLocations.put(to, type);
            this.energyUsed += type.energy * (Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()));
            if (isAmphipodInPlace(to, type)) {
                this.amphipodsPlaced.add(to);
            }
        }

        public boolean allPlaced() {
            return this.amphipodsLocations.size() == this.amphipodsPlaced.size();
        }

        public int getEnergyUsed() {
            return this.energyUsed;
        }

        public List<Burrow> getAllPossibleNextMoves() {
            final List<Burrow> burrows = new ArrayList<>();
            for (Map.Entry<Coordinate, AmphipodType> amphipod : this.amphipodsLocations.entrySet()) {
                final List<Coordinate> possibleMoves = getPossibleMoves(amphipod.getKey(), amphipod.getValue());
                for (Coordinate target : possibleMoves) {
                    final Burrow newBurrow = new Burrow(this);
                    newBurrow.moveAmphipod(amphipod.getKey(), target);
                    burrows.add(newBurrow);
                }
            }

            return burrows;
        }

        private List<Coordinate> getPossibleMoves(Coordinate currentLocation, AmphipodType type) {
            if (this.amphipodsPlaced.contains(currentLocation)) {
                return Collections.emptyList();
            }

            final List<Coordinate> possibleLocations = new ArrayList<>();
            if (HALLWAY.contains(currentLocation)) {
                boolean canMoveAlong = true;
                final List<Coordinate> targets = this.correctLocations.get(type);
                final Coordinate doorway = targets.get(targets.size() - 1).up();
                Coordinate startPoint = new Coordinate(currentLocation);
                if (startPoint.getX() < doorway.getX()) {
                    while (!startPoint.equals(doorway)) {
                        startPoint = startPoint.right();
                        if (this.amphipodsLocations.containsKey(startPoint)) {
                            canMoveAlong = false;
                            break;
                        }
                    }
                } else {
                    while (!startPoint.equals(doorway)) {
                        startPoint = startPoint.left();
                        if (this.amphipodsLocations.containsKey(startPoint)) {
                            canMoveAlong = false;
                            break;
                        }
                    }
                }

                if (canMoveAlong) {
                    Coordinate bottom = new Coordinate(doorway);
                    do {
                        bottom = bottom.down();
                    }
                    while (this.burrow.get(bottom.down()) != TileType.WALL);

                    while (!bottom.equals(doorway)) {
                        if (!this.amphipodsLocations.containsKey(bottom)) {
                            possibleLocations.add(bottom);
                            break;
                        } else if (this.amphipodsLocations.get(bottom) != type) {
                            break;
                        }
                        bottom = bottom.up();
                    }
                }
            } else {
                Coordinate above = currentLocation.up();
                boolean canMoveUp = true;
                while (!HALLWAY.contains(above)) {
                    if (this.amphipodsLocations.containsKey(above)) {
                        canMoveUp = false;
                        break;
                    }
                    above = above.up();
                }

                if (canMoveUp) {
                    Coordinate left = above.left();
                    while (this.burrow.get(left) != TileType.WALL && !this.amphipodsLocations.containsKey(left)) {
                        if (!NON_STOPPING_SPACES.contains(left)) {
                            possibleLocations.add(left);
                        }
                        left = left.left();
                    }
                    Coordinate right = above.right();
                    while (this.burrow.get(right) != TileType.WALL && !this.amphipodsLocations.containsKey(right)) {
                        if (!NON_STOPPING_SPACES.contains(right)) {
                            possibleLocations.add(right);
                        }
                        right = right.right();
                    }
                }
            }

            return possibleLocations;
        }
    }
}
