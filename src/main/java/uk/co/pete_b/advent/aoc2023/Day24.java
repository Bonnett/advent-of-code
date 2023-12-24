package uk.co.pete_b.advent.aoc2023;

import com.microsoft.z3.*;
import uk.co.pete_b.advent.utils.LongCoordinate;
import uk.co.pete_b.advent.utils.LongCoordinate3D;

import java.util.*;

public class Day24 {
    public static int findTwoDimensionalIntersections(final List<String> hailstoneList, long minValue, long maxValue) {
        int count = 0;

        final List<Hailstone2D> hailstones = new ArrayList<>();
        for (final String hailstone : hailstoneList) {
            final String[] parts = hailstone.split(" @ ");
            final long[] stone = Arrays.stream(parts[0].split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            final long[] velocity = Arrays.stream(parts[1].split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            final LongCoordinate positionOne = new LongCoordinate(stone[0], stone[1]);
            final LongCoordinate positionTwo = new LongCoordinate(stone[0] + velocity[0], stone[1] + velocity[1]);
            final double gradient = (double) (positionTwo.getY() - positionOne.getY()) / (positionTwo.getX() - positionOne.getX());
            final double intercept = ((double) positionOne.getY()) - (gradient * positionOne.getX());

            final HailstoneDirection direction;
            if (gradient > 0) {
                direction = (velocity[0] > 0) ? HailstoneDirection.NE : HailstoneDirection.SW;
            } else {
                direction = (velocity[0] > 0) ? HailstoneDirection.SE : HailstoneDirection.NW;
            }

            hailstones.add(new Hailstone2D(hailstone, positionOne, positionTwo, direction, gradient, intercept));
        }

        for (int i = 0; i < hailstones.size(); i++) {
            final Hailstone2D stoneOne = hailstones.get(i);
            for (int j = i; j < hailstones.size(); j++) {
                if (i == j) {
                    continue;
                }

                final Hailstone2D stoneTwo = hailstones.get(j);

                if (stoneOne.gradient == stoneTwo.gradient) {
                    continue;
                }

                final double collisionX = (stoneTwo.intercept - stoneOne.intercept) / (stoneOne.gradient - stoneTwo.gradient);
                final double collisionY = (collisionX * stoneOne.gradient) + stoneOne.intercept;

                if (minValue <= collisionX && collisionX <= maxValue && minValue <= collisionY && collisionY <= maxValue) {
                    final boolean futureForStoneOne = isCollisionInTheFuture(stoneOne, collisionX);
                    final boolean futureForStoneTwo = isCollisionInTheFuture(stoneTwo, collisionX);

                    if (futureForStoneOne && futureForStoneTwo) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static long findBestInitialPosition(final List<String> hailstoneList) {
        final List<Hailstone> hailstones = new ArrayList<>();
        for (final String hailstone : hailstoneList) {
            final String[] parts = hailstone.split(" @ ");
            final long[] stone = Arrays.stream(parts[0].split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            final long[] velocityParts = Arrays.stream(parts[1].split(", ")).map(String::trim).mapToLong(Long::parseLong).toArray();
            final LongCoordinate3D position = new LongCoordinate3D(stone[0], stone[1], stone[2]);
            final LongCoordinate3D velocity = new LongCoordinate3D(velocityParts[0], velocityParts[1], velocityParts[2]);
            hailstones.add(new Hailstone(position, velocity));
        }

        // This is horrible - we're using Z3 to solve it for us
        try (Context ctx = new Context()) {
            final Solver solver = ctx.mkSolver();
            final IntExpr positionX = ctx.mkIntConst("positionX");
            final IntExpr positionY = ctx.mkIntConst("positionY");
            final IntExpr positionZ = ctx.mkIntConst("positionZ");

            final IntExpr velocityX = ctx.mkIntConst("velocityX");
            final IntExpr velocityY = ctx.mkIntConst("velocityY");
            final IntExpr velocityZ = ctx.mkIntConst("velocityZ");

            for (int i = 0; i < 3; i++) {
                final Hailstone hailstone = hailstones.get(i);
                final IntExpr time = ctx.mkIntConst("time" + i);
                solver.add(ctx.mkEq(ctx.mkAdd(positionX, ctx.mkMul(velocityX, time)), ctx.mkAdd(ctx.mkInt(hailstone.start.getX()), ctx.mkMul(ctx.mkInt(hailstone.movement.getX()), time))));
                solver.add(ctx.mkEq(ctx.mkAdd(positionY, ctx.mkMul(velocityY, time)), ctx.mkAdd(ctx.mkInt(hailstone.start.getY()), ctx.mkMul(ctx.mkInt(hailstone.movement.getY()), time))));
                solver.add(ctx.mkEq(ctx.mkAdd(positionZ, ctx.mkMul(velocityZ, time)), ctx.mkAdd(ctx.mkInt(hailstone.start.getZ()), ctx.mkMul(ctx.mkInt(hailstone.movement.getZ()), time))));
            }

            if (solver.check() == Status.SATISFIABLE) {
                final Model model = solver.getModel();
                final IntNum startX = (IntNum) model.getConstInterp(positionX);
                final IntNum startY = (IntNum) model.getConstInterp(positionY);
                final IntNum startZ = (IntNum) model.getConstInterp(positionZ);
                return startX.getInt64() + startY.getInt64() + startZ.getInt64();
            }
        }

        throw new IllegalStateException("Failed to solve");
    }

    private static boolean isCollisionInTheFuture(Hailstone2D stone, double collisionX) {
        switch (stone.direction) {
            case NE, SE -> {
                return collisionX > stone.startPoint.getX();
            }
            case SW, NW -> {
                return collisionX < stone.startPoint.getX();
            }
        }

        throw new IllegalStateException("Invalid Line Direction");
    }

    private record Hailstone2D(String debug, LongCoordinate startPoint, LongCoordinate nextPoint, HailstoneDirection direction, double gradient,
                               double intercept) {
    }

    private enum HailstoneDirection {
        NE, SE, SW, NW
    }

    private record Hailstone(LongCoordinate3D start, LongCoordinate3D movement) {
    }
}
