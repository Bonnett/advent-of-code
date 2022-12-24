package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.CompassBearing;
import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.stream.Collectors;

public class Day23 {
    public static Answer calculateMinimumEmptyGroundTiles(final List<String> elfLocations) {
        final List<Elf> elfList = new ArrayList<>();
        for (int y = 0; y < elfLocations.size(); y++) {
            final String elf = elfLocations.get(y);
            for (int x = 0; x < elf.length(); x++) {
                if (elf.charAt(x) == '#') {
                    elfList.add(new Elf(new Coordinate(x, y)));
                }
            }
        }

        final Deque<CompassBearing> bearings = new ArrayDeque<>();
        bearings.addLast(CompassBearing.NORTH);
        bearings.addLast(CompassBearing.SOUTH);
        bearings.addLast(CompassBearing.WEST);
        bearings.addLast(CompassBearing.EAST);

        int answerPartOne = -1;

        int round = 0;
        while (true) {
            round++;
            final Set<Coordinate> currentPositions = elfList.stream().map(Elf::getCurrentPosition).collect(Collectors.toSet());
            for (Elf elf : elfList) {
                final List<Coordinate> adjacentSquares = getAdjacentSpaces(elf.getCurrentPosition());
                if (adjacentSquares.stream().anyMatch(currentPositions::contains)) {
                    for (CompassBearing bearing : bearings) {
                        if (bearing == CompassBearing.NORTH && !currentPositions.contains(adjacentSquares.get(0))
                                && !currentPositions.contains(adjacentSquares.get(1)) && !currentPositions.contains(adjacentSquares.get(2))) {
                            elf.proposeMove(elf.getCurrentPosition().up());
                            break;
                        } else if (bearing == CompassBearing.SOUTH && !currentPositions.contains(adjacentSquares.get(4))
                                && !currentPositions.contains(adjacentSquares.get(5)) && !currentPositions.contains(adjacentSquares.get(6))) {
                            elf.proposeMove(elf.getCurrentPosition().down());
                            break;
                        } else if (bearing == CompassBearing.EAST && !currentPositions.contains(adjacentSquares.get(6))
                                && !currentPositions.contains(adjacentSquares.get(7)) && !currentPositions.contains(adjacentSquares.get(0))) {
                            elf.proposeMove(elf.getCurrentPosition().right());
                            break;
                        } else if (bearing == CompassBearing.WEST && !currentPositions.contains(adjacentSquares.get(2))
                                && !currentPositions.contains(adjacentSquares.get(3)) && !currentPositions.contains(adjacentSquares.get(4))) {
                            elf.proposeMove(elf.getCurrentPosition().left());
                            break;
                        }
                    }
                }
            }

            final Map<Coordinate, List<Elf>> proposedMoves = elfList.stream()
                    .filter(elf -> elf.getProposedMove() != null)
                    .collect(Collectors.groupingBy(Elf::getProposedMove));

            final Set<Elf> elvesToMove = proposedMoves.values()
                    .stream()
                    .filter(x -> x.size() == 1)
                    .map(list -> list.get(0))
                    .collect(Collectors.toSet());

            elvesToMove.forEach(Elf::doProposedMove);

            if (elvesToMove.isEmpty())
            {
                break;
            }

            elfList.forEach(Elf::reset);

            bearings.addLast(bearings.poll());

            if (round == 10)
            {
                int minX = elfList.stream().mapToInt(elf -> elf.getCurrentPosition().getX()).min().orElseThrow();
                int maxX = elfList.stream().mapToInt(elf -> elf.getCurrentPosition().getX()).max().orElseThrow();

                int minY = elfList.stream().mapToInt(elf -> elf.getCurrentPosition().getY()).min().orElseThrow();
                int maxY = elfList.stream().mapToInt(elf -> elf.getCurrentPosition().getY()).max().orElseThrow();

                answerPartOne = (((maxY - minY) + 1) * ((maxX - minX) + 1)) - elfList.size();
            }
        }

        return new Answer(answerPartOne, round);
    }

    private static List<Coordinate> getAdjacentSpaces(final Coordinate position) {
        return Arrays.asList(position.upRight(), position.up(), position.upLeft(), position.left(), position.downLeft(),
                position.down(), position.downRight(), position.right());
    }

    private static class Elf {

        private Coordinate currentPosition;
        private Coordinate proposedMove = null;

        public Elf(Coordinate startingLocation) {
            this.currentPosition = startingLocation;
        }

        public Coordinate getCurrentPosition() {
            return currentPosition;
        }

        public void proposeMove(Coordinate proposedMove) {
            this.proposedMove = proposedMove;
        }

        public Coordinate getProposedMove() {
            return proposedMove;
        }

        public void doProposedMove() {
            this.currentPosition = proposedMove;
        }

        public void reset() {
            this.proposedMove = null;
        }
    }

    public record Answer(int gridSizeRound10, int roundWithNoMoves) {
    }
}
