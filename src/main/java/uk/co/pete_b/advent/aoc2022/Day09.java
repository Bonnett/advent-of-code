package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.function.Function;

public class Day09 {

    private static final Map<String, Function<Coordinate, Coordinate>> MOVES;

    static {
        MOVES = new HashMap<>();
        MOVES.put("U", Coordinate::up);
        MOVES.put("D", Coordinate::down);
        MOVES.put("L", Coordinate::left);
        MOVES.put("R", Coordinate::right);
    }

    public static int countTailPositions(final List<String> ropeMoves, final int numberOfKnots) {
        final Set<Coordinate> tailPositionsVisited = new HashSet<>();
        final int tailKnotIndex = numberOfKnots - 1;

        final List<Coordinate> knotPositions = new ArrayList<>();
        for (int i = 0; i < numberOfKnots; i++) {
            knotPositions.add(new Coordinate(0, 0));
        }
        tailPositionsVisited.add(knotPositions.get(tailKnotIndex));

        for (String ropeMove : ropeMoves) {
            final String[] moveParts = ropeMove.split(" ");
            final Function<Coordinate, Coordinate> moveFn = MOVES.get(moveParts[0]);
            final int moveDistance = Integer.parseInt(moveParts[1]);
            for (int i = 0; i < moveDistance; i++) {
                knotPositions.set(0, moveFn.apply(knotPositions.get(0)));
                tailPositionsVisited.add(knotPositions.get(tailKnotIndex));

                for (int j = 1; j < knotPositions.size(); j++) {
                    final Coordinate headPosition = knotPositions.get(j - 1);
                    final Coordinate tailPosition = knotPositions.get(j);
                    if (headPosition.equals(tailPosition)) {
                        continue;
                    }

                    if (headPosition.getX() == tailPosition.getX()) {
                        final int yDiff = headPosition.getY() - tailPosition.getY();
                        if (Math.abs(yDiff) == 1) {
                            continue;
                        }

                        if (yDiff > 0) {
                            knotPositions.set(j, tailPosition.down());
                        } else {
                            knotPositions.set(j, tailPosition.up());
                        }
                    } else if (headPosition.getY() == tailPosition.getY()) {
                        final int xDiff = headPosition.getX() - tailPosition.getX();
                        if (Math.abs(xDiff) == 1) {
                            continue;
                        }

                        if (xDiff > 0) {
                            knotPositions.set(j, tailPosition.right());
                        } else {
                            knotPositions.set(j, tailPosition.left());
                        }
                    } else {
                        final int yDiff = headPosition.getY() - tailPosition.getY();
                        final int xDiff = headPosition.getX() - tailPosition.getX();
                        if (Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1) {
                            continue;
                        }

                        if (xDiff > 0) {
                            knotPositions.set(j, tailPosition.right());
                        } else {
                            knotPositions.set(j, tailPosition.left());
                        }

                        if (yDiff > 0) {
                            knotPositions.set(j, knotPositions.get(j).down());
                        } else {
                            knotPositions.set(j, knotPositions.get(j).up());
                        }
                    }
                }
            }
        }

        tailPositionsVisited.add(knotPositions.get(tailKnotIndex));

        return tailPositionsVisited.size();
    }
}
