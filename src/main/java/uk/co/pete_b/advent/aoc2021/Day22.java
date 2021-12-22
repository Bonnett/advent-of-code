package uk.co.pete_b.advent.aoc2021;

import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.*;

public class Day22 {
    public static long countNumberOfCubesOn(final List<String> inputCubes, boolean applyMinMaxRule) {
        final List<Cube> placed = new ArrayList<>();

        for (String input : inputCubes) {
            final String[] parts = input.split(" ");
            final String operation = parts[0];
            final String[] rules = parts[1].split(",");
            final String[] xAxis = rules[0].substring(2).split("\\.\\.");
            final String[] yAxis = rules[1].substring(2).split("\\.\\.");
            final String[] zAxis = rules[2].substring(2).split("\\.\\.");

            final int startX = Integer.parseInt(xAxis[0]);
            final int endX = Integer.parseInt(xAxis[1]);

            final int startY = Integer.parseInt(yAxis[0]);
            final int endY = Integer.parseInt(yAxis[1]);

            final int startZ = Integer.parseInt(zAxis[0]);
            final int endZ = Integer.parseInt(zAxis[1]);

            if (applyMinMaxRule && (startX < -50 || startY < -50 || startZ < -50 || endX > 50 || endY > 50 || endZ > 50))
            {
                continue;
            }

            final Coordinate3D start = new Coordinate3D(startX, startY, startZ);
            final Coordinate3D end = new Coordinate3D(endX, endY, endZ);

            final Cube newCube = new Cube(start, end, operation.equals("on"));

            final List<Cube> cubesToPlace = new ArrayList<>();
            if (newCube.turnedOn) {
                cubesToPlace.add(newCube);
            }
            for (Cube p : placed) {
                if (p.isIntersecting(newCube)) {
                    cubesToPlace.add(p.getIntersection(newCube));
                }
            }

            placed.addAll(cubesToPlace);
        }

        return placed.stream().mapToLong(Cube::volume).sum();
    }

    private record Cube(Coordinate3D start, Coordinate3D end, boolean turnedOn) {
        public long volume() {
            return (turnedOn ? 1 : -1) * (long) (end.getX() - start.getX() + 1) * (long) (end.getY() - start().getY() + 1) * (long) (end.getZ() - start().getZ() + 1);
        }

        public boolean isIntersecting(Cube otherCube) {
            return this.start.getX() <= otherCube.end.getX() && otherCube.start.getX() <= this.end.getX()
                    && this.start.getY() <= otherCube.end.getY() && otherCube.start.getY() <= this.end.getY()
                    && this.start.getZ() <= otherCube.end.getZ() && otherCube.start.getZ() <= this.end.getZ();
        }

        public Cube getIntersection(Cube otherCube) {
            return new Cube(
                    new Coordinate3D(Math.max(this.start.getX(), otherCube.start.getX()),
                            Math.max(this.start.getY(), otherCube.start.getY()),
                            Math.max(this.start.getZ(), otherCube.start.getZ())),
                    new Coordinate3D(Math.min(this.end.getX(), otherCube.end.getX()),
                            Math.min(this.end.getY(),otherCube.end.getY()),
                            Math.min(this.end.getZ(), otherCube.end.getZ())), !turnedOn);
        }
    }
}
