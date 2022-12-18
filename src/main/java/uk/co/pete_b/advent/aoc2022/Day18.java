package uk.co.pete_b.advent.aoc2022;

import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.*;

public class Day18 {

    public static int countVisibleFaces(final List<String> cubeSetup) {
        return countVisibleFaces(cubeSetup, false);
    }

    public static int countVisibleFaces(final List<String> cubeSetup, final boolean externalFacesOnly) {
        final Set<Coordinate3D> cubes = new HashSet<>();

        final Set<Set<Coordinate3D>> visibleFaces = new HashSet<>();
        for (String cubeLine : cubeSetup) {
            final String[] parts = cubeLine.split(",");
            final Cube cube = new Cube(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
            cubes.add(cube.topRight);

            for (Set<Coordinate3D> face : cube.faces) {
                if (visibleFaces.contains(face)) {
                    visibleFaces.remove(face);
                } else {
                    visibleFaces.add(face);
                }
            }
        }

        if (externalFacesOnly) {
            // Build a set of all cubes in our grid that aren't in the input data
            final int xMax = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getX).max().orElseThrow();
            final int xMin = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getX).min().orElseThrow();
            final int yMax = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getY).max().orElseThrow();
            final int yMin = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getY).min().orElseThrow();
            final int zMax = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getZ).max().orElseThrow();
            final int zMin = visibleFaces.stream().flatMap(Set::stream).mapToInt(Coordinate3D::getZ).min().orElseThrow();

            final Set<Coordinate3D> otherCubes = new HashSet<>();
            for (int z = zMin; z <= zMax; z++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int x = xMin; x <= xMax; x++) {
                        Coordinate3D cube = new Coordinate3D(x, y, z);
                        if (!cubes.contains(cube)) {
                            otherCubes.add(cube);
                        }
                    }
                }
            }

            // Find all the cubes that are reachable (via adjacent cubes) from the bottom corner
            final Set<Coordinate3D> adjacentCubes = new HashSet<>();
            final Coordinate3D startCube = new Coordinate3D(xMin, yMin, zMin);
            adjacentCubes.add(startCube);

            final Set<Coordinate3D> cubesChecked = new HashSet<>();

            while (true) {
                boolean addedCubes = false;
                final List<Coordinate3D> cubesToAdd = new ArrayList<>();

                for (Coordinate3D cube : adjacentCubes) {
                    if (cubesChecked.contains(cube)) {
                        continue;
                    }

                    final List<Coordinate3D> adjacent = getAdjacentCubes(cube);
                    for (Coordinate3D adjacentCube : adjacent) {
                        if (otherCubes.contains(adjacentCube)) {
                            addedCubes = true;
                            cubesToAdd.add(adjacentCube);
                        }
                    }
                    cubesChecked.add(cube);
                }
                adjacentCubes.addAll(cubesToAdd);

                if (!addedCubes) {
                    break;
                }
            }

            // Remove all the cubes that are adjacent to our starting point
            otherCubes.removeAll(adjacentCubes);

            // Remove all the edges from the cubes that aren't adjacent
            otherCubes.stream().map(Cube::new).flatMap(x -> x.faces.stream()).forEach(visibleFaces::remove);
        }

        return visibleFaces.size();
    }

    private static List<Coordinate3D> getAdjacentCubes(final Coordinate3D cube) {
        final List<Coordinate3D> cubes = new ArrayList<>();
        cubes.add(new Coordinate3D(cube.getX() + 1, cube.getY(), cube.getZ()));
        cubes.add(new Coordinate3D(cube.getX() - 1, cube.getY(), cube.getZ()));
        cubes.add(new Coordinate3D(cube.getX(), cube.getY() + 1, cube.getZ()));
        cubes.add(new Coordinate3D(cube.getX(), cube.getY() - 1, cube.getZ()));
        cubes.add(new Coordinate3D(cube.getX(), cube.getY(), cube.getZ() + 1));
        cubes.add(new Coordinate3D(cube.getX(), cube.getY(), cube.getZ() - 1));

        return cubes;
    }

    private static class Cube {
        private final Set<Set<Coordinate3D>> faces;

        private final Coordinate3D topRight;

        public Cube(final Coordinate3D topRightPoint) {
            this(topRightPoint.getX(), topRightPoint.getY(), topRightPoint.getZ());
        }

        public Cube(final int x, final int y, final int z) {
            // Find vertices
            final Coordinate3D frontTopRight = new Coordinate3D(x, y, z);
            final Coordinate3D frontTopLeft = new Coordinate3D(x, y, z - 1);
            final Coordinate3D frontBottomLeft = new Coordinate3D(x, y - 1, z - 1);
            final Coordinate3D frontBottomRight = new Coordinate3D(x, y - 1, z);
            final Coordinate3D backBottomLeft = new Coordinate3D(x - 1, y - 1, z - 1);
            final Coordinate3D backBottomRight = new Coordinate3D(x - 1, y - 1, z);
            final Coordinate3D backTopLeft = new Coordinate3D(x - 1, y, z - 1);
            final Coordinate3D backTopRight = new Coordinate3D(x - 1, y, z);

            // Build faces
            final Set<Coordinate3D> back = new HashSet<>(Arrays.asList(backTopLeft, backTopRight, backBottomRight, backBottomLeft));
            final Set<Coordinate3D> front = new HashSet<>(Arrays.asList(frontTopRight, frontTopLeft, frontBottomLeft, frontBottomRight));
            final Set<Coordinate3D> top = new HashSet<>(Arrays.asList(frontTopRight, frontTopLeft, backTopLeft, backTopRight));
            final Set<Coordinate3D> bottom = new HashSet<>(Arrays.asList(frontBottomLeft, frontBottomRight, backBottomRight, backBottomLeft));
            final Set<Coordinate3D> left = new HashSet<>(Arrays.asList(frontTopLeft, frontBottomLeft, backBottomLeft, backTopLeft));
            final Set<Coordinate3D> right = new HashSet<>(Arrays.asList(frontTopRight, frontBottomRight, backBottomRight, backTopRight));

            this.topRight = frontTopRight;
            this.faces = new HashSet<>(Arrays.asList(back, front, top, bottom, left, right));
        }
    }
}
