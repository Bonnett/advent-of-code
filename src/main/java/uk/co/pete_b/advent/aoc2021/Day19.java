package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.Coordinate3D;

import java.util.*;

public class Day19 {
    public static Answer arrangeUniverse(final List<String> inputData) {
        final List<Scanner> scanners = parseScanners(inputData);
        scanners.forEach(Scanner::arrangePossibilities);

        // Orient everything based on the first scanner
        final Scanner startScanner = scanners.get(0);
        startScanner.orientationSet = 0;
        startScanner.scannerLocation = new Coordinate3D(0, 0, 0);

        // We've already aligned up the first scanner
        int scannersAligned = 1;
        while (scannersAligned != scanners.size()) {
            for (Scanner sourceScanner : scanners) {
                // Don't try and orient if we don't know where the current scanner is
                if (sourceScanner.orientationSet == -1) {
                    continue;
                }

                final Set<Coordinate3D> startSet = sourceScanner.distances.get(sourceScanner.orientationSet).keySet();
                for (Scanner scanner : scanners) {
                    // Don't try to orient against ourselves or with one we've already oriented
                    if (scanner.scannerIndex == sourceScanner.scannerIndex || scanner.orientationSet != -1) {
                        continue;
                    }

                    final List<Map<Coordinate3D, Integer>> pairs = scanner.distances;
                    for (int i = 0; i < pairs.size(); i++) {
                        final List<Coordinate3D> matchingPairs = startSet.stream().filter(pairs.get(i).keySet()::contains).toList();

                        if (matchingPairs.size() >= 132) {
                            scanner.orientationSet = i;

                            // To find the offset we only need to use the first matching pair
                            final Coordinate3D firstPair = matchingPairs.get(0);
                            final Coordinate3D firstPoint = sourceScanner.beaconArrangements.get(sourceScanner.orientationSet).get(sourceScanner.distances.get(sourceScanner.orientationSet).get(firstPair));
                            final Coordinate3D secondPoint = scanner.beaconArrangements.get(i).get(pairs.get(i).get(firstPair));

                            final Coordinate3D offset = Coordinate3D.vector(secondPoint, firstPoint);
                            scanner.scannerLocation = new Coordinate3D(offset.getX() + sourceScanner.scannerLocation.getX(), offset.getY() + sourceScanner.scannerLocation.getY(), offset.getZ() + sourceScanner.scannerLocation.getZ());
                            scannersAligned++;
                        }
                    }
                }
            }
        }

        final Set<Coordinate3D> beacons = new HashSet<>();
        for (Scanner scanner : scanners) {
            final List<Coordinate3D> scannedBeacons = scanner.beaconArrangements.get(scanner.orientationSet);
            final Coordinate3D scannerLocation = scanner.scannerLocation;
            for (Coordinate3D beacon : scannedBeacons) {
                beacons.add(new Coordinate3D(beacon.getX() + scannerLocation.getX(), beacon.getY() + scannerLocation.getY(), beacon.getZ() + scannerLocation.getZ()));
            }
        }

        int largestManhattanDistance = 0;
        for (Scanner scannerOne : scanners) {
            for (Scanner scannerTwo : scanners) {
                if (scannerOne.scannerIndex == scannerTwo.scannerIndex) {
                    continue;
                }
                final Coordinate3D vector = Coordinate3D.vector(scannerOne.scannerLocation, scannerTwo.scannerLocation);
                if (largestManhattanDistance < vector.getManhattanDistance()) {
                    largestManhattanDistance = vector.getManhattanDistance();
                }
            }
        }

        return new Answer(beacons.size(), largestManhattanDistance);
    }

    private static List<Scanner> parseScanners(final List<String> inputData) {
        final List<Scanner> scanners = new ArrayList<>();

        int scannerIndex = 0;
        Scanner scanner = new Scanner(scannerIndex++);
        for (String input : inputData) {
            if (input.startsWith("---")) {
                continue;
            }
            if (input.isEmpty()) {
                scanners.add(scanner);
                scanner = new Scanner(scannerIndex++);
            } else {
                final String[] parts = input.split(",");
                final int x = Integer.parseInt(parts[0]);
                final int y = Integer.parseInt(parts[1]);
                final int z = Integer.parseInt(parts[2]);
                scanner.initialBeacons.add(new Coordinate3D(x, y, z));
            }
        }
        scanners.add(scanner);

        return scanners;
    }

    private static class Scanner {
        private final int scannerIndex;
        private final List<Coordinate3D> initialBeacons = new ArrayList<>();
        private final List<List<Coordinate3D>> beaconArrangements = new ArrayList<>();
        private final List<Map<Coordinate3D, Integer>> distances = new ArrayList<>();

        private int orientationSet = -1;
        private Coordinate3D scannerLocation = null;

        public Scanner(int scannerIndex) {
            this.scannerIndex = scannerIndex;
        }

        public void arrangePossibilities() {
            // There are 24 different ways to rotate/align a cube (6 sides, 4 rotations)
            for (int i = 0; i < 24; i++) {
                final List<Coordinate3D> arrangement = new ArrayList<>();
                this.beaconArrangements.add(arrangement);
                this.distances.add(new HashMap<>());
            }

            for (final Coordinate3D beacon : this.initialBeacons) {
                // x	y	z
                this.beaconArrangements.get(0).add(new Coordinate3D(beacon.getX(), beacon.getY(), beacon.getZ()));
                // y	-x	z
                this.beaconArrangements.get(1).add(new Coordinate3D(beacon.getY(), -beacon.getX(), beacon.getZ()));
                // -x	-y	z
                this.beaconArrangements.get(2).add(new Coordinate3D(-beacon.getX(), -beacon.getY(), beacon.getZ()));
                // -y	x	z
                this.beaconArrangements.get(3).add(new Coordinate3D(-beacon.getY(), beacon.getX(), beacon.getZ()));
                // -y	-z	x
                this.beaconArrangements.get(4).add(new Coordinate3D(-beacon.getY(), -beacon.getZ(), beacon.getX()));
                // -z	y	x
                this.beaconArrangements.get(5).add(new Coordinate3D(-beacon.getZ(), beacon.getY(), beacon.getX()));
                // y	z	x
                this.beaconArrangements.get(6).add(new Coordinate3D(beacon.getY(), beacon.getZ(), beacon.getX()));
                // z	-y	x
                this.beaconArrangements.get(7).add(new Coordinate3D(beacon.getZ(), -beacon.getY(), beacon.getX()));
                // z	-x	-y
                this.beaconArrangements.get(8).add(new Coordinate3D(beacon.getZ(), -beacon.getX(), -beacon.getY()));
                // -x	-z	-y
                this.beaconArrangements.get(9).add(new Coordinate3D(-beacon.getX(), -beacon.getZ(), -beacon.getY()));
                // -z	x	-y
                this.beaconArrangements.get(10).add(new Coordinate3D(-beacon.getZ(), beacon.getX(), -beacon.getY()));
                // x	z	-y
                this.beaconArrangements.get(11).add(new Coordinate3D(beacon.getX(), beacon.getZ(), -beacon.getY()));
                // y	x	-z
                this.beaconArrangements.get(12).add(new Coordinate3D(beacon.getY(), beacon.getX(), -beacon.getZ()));
                // x	-y	-z
                this.beaconArrangements.get(13).add(new Coordinate3D(beacon.getX(), -beacon.getY(), -beacon.getZ()));
                // -y	-x	-z
                this.beaconArrangements.get(14).add(new Coordinate3D(-beacon.getY(), -beacon.getX(), -beacon.getZ()));
                // -x	y	-z
                this.beaconArrangements.get(15).add(new Coordinate3D(-beacon.getX(), beacon.getY(), -beacon.getZ()));
                // z	y	-x
                this.beaconArrangements.get(16).add(new Coordinate3D(beacon.getZ(), beacon.getY(), -beacon.getX()));
                // y	-z	-x
                this.beaconArrangements.get(17).add(new Coordinate3D(beacon.getY(), -beacon.getZ(), -beacon.getX()));
                // -z	-y	-x
                this.beaconArrangements.get(18).add(new Coordinate3D(-beacon.getZ(), -beacon.getY(), -beacon.getX()));
                // -y	z	-x
                this.beaconArrangements.get(19).add(new Coordinate3D(-beacon.getY(), beacon.getZ(), -beacon.getX()));
                // -x	z	y
                this.beaconArrangements.get(20).add(new Coordinate3D(-beacon.getX(), beacon.getZ(), beacon.getY()));
                // z	x	y
                this.beaconArrangements.get(21).add(new Coordinate3D(beacon.getZ(), beacon.getX(), beacon.getY()));
                // x	-z	y
                this.beaconArrangements.get(22).add(new Coordinate3D(beacon.getX(), -beacon.getZ(), beacon.getY()));
                // -z	-x	y
                this.beaconArrangements.get(23).add(new Coordinate3D(-beacon.getZ(), -beacon.getX(), beacon.getY()));
            }

            // For each arrangement calculate the vectors between each beacon they can see
            for (int index = 0; index < this.beaconArrangements.size(); index++) {
                final List<Coordinate3D> arrangement = this.beaconArrangements.get(index);
                for (int i = 0; i < arrangement.size(); i++) {
                    for (int j = 0; j < arrangement.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        this.distances.get(index).put(Coordinate3D.vector(arrangement.get(i), arrangement.get(j)), i);
                    }
                }
            }
        }
    }

    public record Answer(int numberOfBeacons, int largestDistance) {
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).numberOfBeacons == this.numberOfBeacons && ((Answer) otherAnswer).largestDistance == this.largestDistance;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Number of Beacons %d, Largest Manhattan distance: %d", numberOfBeacons, largestDistance);
        }
    }
}
