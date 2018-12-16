package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class Day15 {

    public static int getOutcomeWithDeaths(final List<String> caveLayout) {
        final Cave cave = new Cave(caveLayout, 3);
        int score = -1;
        while (score == -1) {
            score = cave.takeTurn();
        }

        return score;
    }

    public static Answer getAttackPowerNoDeaths(final List<String> caveLayout) {
        int attackPower = 1;
        int score;
        while (true) {
            score = -1;
            final Cave cave = new Cave(caveLayout, attackPower);
            final long numberOfElves = cave.elves.size();
            boolean haveDied = false;
            while (score == -1) {
                score = cave.takeTurn();
                if (numberOfElves != cave.elves.size()) {
                    haveDied = true;
                    break;
                }
            }

            if (!haveDied && !cave.elves.isEmpty()) {
                break;
            }

            attackPower++;
        }

        return new Answer(attackPower, score);
    }

    private static class Cave {

        private enum UnitType {
            ELF("E"), GOBLIN("G");

            private final String icon;

            UnitType(final String icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return this.icon;
            }
        }

        private enum CaveSquareType {
            WALL("#"), OPEN(".");
            private final String icon;

            CaveSquareType(final String icon) {
                this.icon = icon;
            }

            @Override
            public String toString() {
                return this.icon;
            }
        }

        private List<Unit> elves = new ArrayList<>();
        private List<Unit> goblins = new ArrayList<>();
        private List<Unit> units = new ArrayList<>();
        private Map<Coordinate, CaveSquare> caveMap = new HashMap<>();

        int mapWidth = 0;
        int mapHeight;
        private int rounds = 0;

        public Cave(final List<String> layout, final int elfDamageScore) {
            this.mapHeight = layout.size();
            for (int y = 0; y < layout.size(); y++) {
                final String line = layout.get(y);
                this.mapWidth = line.length();
                for (int x = 0; x < line.length(); x++) {
                    final Coordinate coords = new Coordinate(x, y);
                    final char square = line.charAt(x);
                    final CaveSquare caveSquare;
                    if (square == '#') {
                        caveSquare = new CaveSquare(CaveSquareType.WALL, coords);
                    } else if (square == '.') {
                        caveSquare = new CaveSquare(CaveSquareType.OPEN, coords);
                    } else if (square == 'G') {
                        caveSquare = new CaveSquare(CaveSquareType.OPEN, coords);
                        this.goblins.add(new Unit(UnitType.GOBLIN, caveSquare));
                    } else {
                        caveSquare = new CaveSquare(CaveSquareType.OPEN, coords);
                        this.elves.add(new Unit(UnitType.ELF, caveSquare, elfDamageScore));
                    }

                    this.caveMap.put(coords, caveSquare);
                }
            }

            this.units.addAll(this.elves);
            this.units.addAll(this.goblins);
        }

        public int takeTurn() {
            this.rounds++;
            int offset = 0;
            Collections.sort(this.units);

            for (final Unit unit : this.units) {
                if (unit.isAlive()) {
                    if (unit.unitType == UnitType.GOBLIN) {
                        if (this.elves.isEmpty()) {
                            offset = 1;
                            break;
                        }
                        takeUnitTurn(unit, elves);
                        this.elves.removeIf(elf -> !elf.isAlive());
                    } else {
                        if (this.goblins.isEmpty()) {
                            offset = 1;
                            break;
                        }
                        takeUnitTurn(unit, goblins);
                        this.goblins.removeIf(goblin -> !goblin.isAlive());
                    }
                }
            }

            this.units.removeIf(unit -> !unit.isAlive());

            if (this.elves.isEmpty()) {
                return (this.rounds - offset) * this.goblins.stream().mapToInt(x -> x.hp).sum();
            } else if (this.goblins.isEmpty()) {
                return (this.rounds - offset) * this.elves.stream().mapToInt(x -> x.hp).sum();
            }

            return -1;
        }

        private void takeUnitTurn(final Unit unit, final List<Unit> targets) {
            Unit enemy = findAdjacentEnemy(unit);

            if (enemy != null) {
                enemy.takeDamage(unit.damageScore);
            } else {
                final Set<CaveSquare> inRange = findInRangeSquares(targets);
                if (inRange.isEmpty()) {
                    return;
                }

                moveUnit(unit, inRange);

                enemy = findAdjacentEnemy(unit);
                if (enemy != null) {
                    enemy.takeDamage(unit.damageScore);
                }
            }
        }

        private void moveUnit(final Unit unit, final Set<CaveSquare> inRange) {
            CaveSquare newSquare = null;

            final boolean[][] visitedSquares = new boolean[this.mapHeight][this.mapWidth];
            visitedSquares[unit.caveSquare.location.getY()][unit.caveSquare.location.getX()] = true;

            List<LinkedList<CaveSquare>> paths = new ArrayList<>();
            paths.add(new LinkedList<>(Collections.singletonList(unit.caveSquare)));

            while (true) {
                List<LinkedList<CaveSquare>> newPaths = new ArrayList<>();
                List<LinkedList<CaveSquare>> finishedPaths = new ArrayList<>();

                for (final LinkedList<CaveSquare> path : paths)  {
                    final List<Coordinate> adjacentSquares = getAdjacentSquares(path.getLast());
                    for (final Coordinate adjacentSquare : adjacentSquares) {
                        if (this.caveMap.containsKey(adjacentSquare)) {
                            final CaveSquare square = this.caveMap.get(adjacentSquare);
                            if (inRange.contains(square)) {
                                final LinkedList<CaveSquare> targetPath = new LinkedList<>(path);
                                targetPath.add(square);
                                finishedPaths.add(targetPath);
                            } else if (!visitedSquares[adjacentSquare.getY()][adjacentSquare.getX()] && square.squareType == CaveSquareType.OPEN && !square.isOccupied()) {
                                final LinkedList<CaveSquare> newPath = new LinkedList<>(path);
                                newPath.add(square);
                                newPaths.add(newPath);
                            }

                            visitedSquares[adjacentSquare.getY()][adjacentSquare.getX()] = true;
                        }
                    }
                }

                if (!finishedPaths.isEmpty()) {
                    finishedPaths.sort(Comparator.comparing(path -> path.getLast().location));
                    newSquare = finishedPaths.get(0).get(1);
                    break;
                }

                paths = newPaths;
                if (paths.isEmpty()) {
                    break;
                }
            }
            if (newSquare != null) {
                unit.moveTo(newSquare);
            }
        }

        private Unit findAdjacentEnemy(final Unit unit) {
            Unit target = null;
            int lowestHealth = Integer.MAX_VALUE;

            final List<Coordinate> adjacentSquares = getAdjacentSquares(unit.caveSquare);
            for (final Coordinate adjacent : adjacentSquares) {
                if (this.caveMap.containsKey(adjacent)) {
                    final CaveSquare square = this.caveMap.get(adjacent);
                    if (square.isOccupied() && square.unit.unitType != unit.unitType && square.unit.hp < lowestHealth) {
                        target = square.unit;
                        lowestHealth = square.unit.hp;
                    }
                }
            }

            return target;
        }

        private Set<CaveSquare> findInRangeSquares(final List<Unit> targets) {
            final Set<CaveSquare> inRange = new HashSet<>();
            for (final Unit target : targets) {
                if (target.isAlive()) {
                    final List<Coordinate> adjacentSquares = getAdjacentSquares(target.caveSquare);
                    for (final Coordinate square : adjacentSquares) {
                        addSquare(inRange, square);
                    }
                }
            }

            return inRange;
        }

        private void addSquare(final Set<CaveSquare> squareSet, final Coordinate coordinate) {
            final CaveSquare square = this.caveMap.get(coordinate);
            if (square != null && square.squareType == CaveSquareType.OPEN && !square.isOccupied()) {
                squareSet.add(square);
            }
        }

        private List<Coordinate> getAdjacentSquares(final CaveSquare caveSquares) {
            return Arrays.asList(caveSquares.location.up(), caveSquares.location.left(), caveSquares.location.right(), caveSquares.location.down());
        }

        private static class Unit implements Comparable<Unit> {
            private CaveSquare caveSquare;
            private final UnitType unitType;
            private int hp;
            private int damageScore = 3;

            public Unit(final UnitType unitType, final CaveSquare caveSquare) {
                this.caveSquare = caveSquare;
                this.unitType = unitType;
                this.hp = 200;
                this.caveSquare.unit = this;
            }

            public Unit(final UnitType unitType, final CaveSquare caveSquare, int damageScore) {
                this(unitType, caveSquare);
                this.damageScore = damageScore;
            }

            public boolean isAlive() {
                return this.hp > 0;
            }

            public void moveTo(final CaveSquare newSquare) {
                this.caveSquare.unit = null;
                this.caveSquare = newSquare;
                this.caveSquare.unit = this;
            }

            public void takeDamage(final int damageScore) {
                this.hp -= damageScore;

                if (!this.isAlive()) {
                    this.caveSquare.unit = null;
                }
            }

            @Override
            public int compareTo(final Unit o) {
                return this.caveSquare.location.compareTo(o.caveSquare.location);
            }

            @Override
            public String toString() {
                return this.unitType.toString() + "(" + this.hp + ")";
            }
        }

        private static class CaveSquare {
            private final CaveSquareType squareType;
            private final Coordinate location;
            private Unit unit;

            public CaveSquare(final CaveSquareType squareType, final Coordinate location) {
                this.squareType = squareType;
                this.location = location;
            }

            public boolean isOccupied() {
                return this.unit != null;
            }

            @Override
            public String toString() {
                if (this.isOccupied()) {
                    return this.unit.unitType.toString();
                } else {
                    return this.squareType.toString();
                }
            }
        }
    }

    public static class Answer {
        private final int attackPower;
        private final int outcome;

        public Answer(final int attackPower, final int outcome) {
            this.attackPower = attackPower;
            this.outcome = outcome;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherCoords) {
            boolean isEqual = false;
            if (otherCoords instanceof Answer) {
                isEqual = ((Answer) otherCoords).attackPower == this.attackPower && ((Answer) otherCoords).outcome == this.outcome;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Attack Power: %d, Outcome: %d", attackPower, outcome);
        }
    }
}
