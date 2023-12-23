package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 {
    public static Answer countDisintegratingBricks(final List<String> inputBricks) {
        int count = 0;
        final List<Brick> bricks = new ArrayList<>();
        for (int i = 0; i < inputBricks.size(); i++) {
            final String[] startEnd = inputBricks.get(i).split("~");
            final int[] startCoordinates = Arrays.stream(startEnd[0].split(",")).mapToInt(Integer::parseInt).toArray();
            final int[] endCoordinates = Arrays.stream(startEnd[1].split(",")).mapToInt(Integer::parseInt).toArray();
            final Coordinate3D startCube = new Coordinate3D(startCoordinates[0], startCoordinates[1], startCoordinates[2]);
            final Coordinate3D endCube = new Coordinate3D(endCoordinates[0], endCoordinates[1], endCoordinates[2]);
            bricks.add(getBrick(i, startCube, endCube));
        }

        bricks.sort(Comparator.comparingInt(o -> o.cubes.get(0).getZ()));

        final Map<Coordinate3D, Integer> cubeLayout = new HashMap<>();
        final List<Brick> newBrickLayout = fallDownwards(cubeLayout, bricks);

        final Map<Integer, Set<Integer>> bricksSupportedBy = new HashMap<>();
        final Map<Integer, Set<Integer>> bricksSupporting = new HashMap<>();

        for (final Brick brick : newBrickLayout) {
            final List<Coordinate3D> aboveCoordinates = goUp(brick.cubes);
            final Set<Integer> supportingBricks = aboveCoordinates.stream().map(cubeLayout::get).filter(Objects::nonNull).filter(x -> x != brick.brickId).collect(Collectors.toSet());
            bricksSupporting.put(brick.brickId, supportingBricks);
            for (Integer brickId : supportingBricks) {
                bricksSupportedBy.compute(brickId, (integer, below) -> {
                    if (below == null) {
                        below = new HashSet<>();
                    }
                    below.add(brick.brickId);

                    return below;
                });
            }
        }

        final List<Integer> unsafeBricks = new ArrayList<>();

        for (int i = 0; i < bricks.size(); i++) {
            final Set<Integer> supporting = bricksSupporting.getOrDefault(i, Collections.emptySet());

            if (supporting.isEmpty()) {
                count++;
            } else {
                boolean canDisintegrate = true;
                for (Integer brickAbove : supporting) {
                    if (bricksSupportedBy.getOrDefault(brickAbove, Collections.emptySet()).size() == 1) {
                        canDisintegrate = false;
                        unsafeBricks.add(i);
                        break;
                    }
                }

                if (canDisintegrate) {
                    count++;
                }
            }
        }

        int total = 0;
        for (Integer unsafeBrick : unsafeBricks) {
            total += getAllChildren(bricksSupporting, bricksSupportedBy, new HashSet<>(Collections.singletonList(unsafeBrick)), unsafeBrick).size();
        }

        return new Answer(count, total);
    }

    private static Set<Integer> getAllChildren(final Map<Integer, Set<Integer>> bricksSupporting, final Map<Integer, Set<Integer>> bricksSupportedBy,
                                               final Set<Integer> bricksDeleted, final Integer targetBrick) {
        final Set<Integer> validChildren = bricksSupporting.getOrDefault(targetBrick, Collections.emptySet())
                .stream()
                .filter(child -> bricksDeleted.containsAll(bricksSupportedBy.get(child)))
                .collect(Collectors.toSet());

        bricksDeleted.addAll(validChildren);

        final Set<Integer> children = new HashSet<>(validChildren);
        for (Integer child : validChildren) {
            children.addAll(getAllChildren(bricksSupporting, bricksSupportedBy, bricksDeleted, child));
        }

        return children;
    }

    private static List<Brick> fallDownwards(final Map<Coordinate3D, Integer> cubeLayout, final List<Brick> bricks) {
        final List<Brick> newLayout = new ArrayList<>();
        final Set<Coordinate3D> allCubes = new HashSet<>();

        for (Brick brick : bricks) {
            final List<Coordinate3D> cubes = brick.cubes;

            if (cubes.get(0).getZ() == 1) {
                allCubes.addAll(cubes);
                cubes.forEach(x -> cubeLayout.put(x, brick.brickId));
                newLayout.add(brick);
            } else {
                List<Coordinate3D> newCubes = new ArrayList<>(cubes);
                List<Coordinate3D> oldCubes = new ArrayList<>(cubes);
                while (true) {
                    newCubes = fallDown(newCubes);
                    if (newCubes.stream().anyMatch(allCubes::contains) || newCubes.get(0).getZ() == 0) {
                        allCubes.addAll(oldCubes);
                        oldCubes.forEach(x -> cubeLayout.put(x, brick.brickId));
                        newLayout.add(new Brick(brick.brickId, oldCubes));
                        break;
                    }

                    oldCubes = newCubes;
                }
            }
        }

        return newLayout;
    }

    private static List<Coordinate3D> fallDown(final List<Coordinate3D> cubes) {
        return cubes.stream().map(x -> {
            final Coordinate3D newCoordinate = new Coordinate3D(x);
            newCoordinate.move(0, 0, -1);
            return newCoordinate;
        }).toList();
    }

    private static List<Coordinate3D> goUp(final List<Coordinate3D> cubes) {
        return cubes.stream().map(x -> {
            final Coordinate3D newCoordinate = new Coordinate3D(x);
            newCoordinate.move(0, 0, 1);
            return newCoordinate;
        }).toList();
    }

    private static Brick getBrick(final int brickId, final Coordinate3D startCube, final Coordinate3D endCube) {
        final Set<Coordinate3D> cubes = new HashSet<>();

        if (startCube.equals(endCube)) {
            cubes.add(startCube);
        } else {
            if (startCube.getX() != endCube.getX()) {
                final int startX = Math.min(startCube.getX(), endCube.getX());
                final int endX = Math.max(startCube.getX(), endCube.getX());
                for (int x = startX; x <= endX; x++) {
                    cubes.add(new Coordinate3D(x, startCube.getY(), startCube.getZ()));
                }
            }
            if (startCube.getY() != endCube.getY()) {
                final int startY = Math.min(startCube.getY(), endCube.getY());
                final int endY = Math.max(startCube.getY(), endCube.getY());
                for (int y = startY; y <= endY; y++) {
                    cubes.add(new Coordinate3D(startCube.getX(), y, startCube.getZ()));
                }
            }
            if (startCube.getZ() != endCube.getZ()) {
                final int startZ = Math.min(startCube.getZ(), endCube.getZ());
                final int endZ = Math.max(startCube.getZ(), endCube.getZ());
                for (int z = startZ; z <= endZ; z++) {
                    cubes.add(new Coordinate3D(startCube.getX(), startCube.getY(), z));
                }
            }
        }

        final List<Coordinate3D> sortedCubes = new ArrayList<>(cubes);
        sortedCubes.sort(Comparator.comparingInt(Coordinate3D::getZ));

        return new Brick(brickId, sortedCubes);
    }

    private record Brick(int brickId, List<Coordinate3D> cubes) {
    }

    public record Answer(int soloBricks, int totalBricks) {
    }
}
