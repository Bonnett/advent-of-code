package uk.co.pete_b.advent.aoc2019;

import java.util.*;

public class Day06 {
    public static int calculateOrbits(final List<String> planets) {
        final Map<String, Planet> solarSystem = generateSolarSystem(planets);

        int orbitCount = 0;
        for (final Planet planet : solarSystem.values()) {
            Planet primary = planet.primary;
            while (primary != null) {
                orbitCount++;
                primary = primary.primary;
            }
        }

        return orbitCount;
    }

    public static int calculateOrbitalTransfers(final List<String> planets) {
        final Map<String, Planet> solarSystem = generateSolarSystem(planets);
        final Map<String, Set<Planet>> tree = new HashMap<>();
        for (final Planet planet : solarSystem.values()) {
            tree.compute(planet.name, (name, children) -> {
                if (children == null) {
                    children = new HashSet<>();
                }

                return children;
            });

            Planet primary = planet.primary;
            while (primary != null) {
                tree.compute(primary.name, (name, children) -> {
                    if (children == null) {
                        children = new HashSet<>();
                    }
                    children.add(planet);

                    return children;
                });

                primary = primary.primary;
            }
        }

        int transfers = 0;
        final Planet start = solarSystem.get("YOU");
        final Planet target = solarSystem.get("SAN");
        Planet current = start;

        // Find the shared parent between YOU and SAN
        while (!tree.get(current.name).contains(target)) {
            current = current.primary;
            transfers++;
        }

        // Navigate back up the tree to get to SAN
        while (!current.name.equals(target.name)) {
            final Set<Planet> children = tree.get(current.name);
            for (final Planet child : children) {
                if (child.primary.equals(current)) {
                    if (child.equals(target)) {
                        current = child;
                        break;
                    } else if (tree.get(child.name).contains(target)) {
                        current = child;
                        transfers++;
                        break;
                    }
                }
            }
        }

        // Ignore the first hop away from "YOU"
        return transfers - 1;
    }

    private static Map<String, Planet> generateSolarSystem(List<String> planets) {
        final Map<String, Planet> solarSystem = new HashMap<>();
        for (final String planetLine : planets) {
            final String[] parts = planetLine.split("\\)");
            Planet primary = solarSystem.get(parts[0]);
            if (primary == null) {
                primary = new Planet(parts[0]);
                solarSystem.put(parts[0], primary);
            }
            Planet satellite = solarSystem.get(parts[1]);
            if (satellite == null) {
                satellite = new Planet(parts[1]);
                solarSystem.put(parts[1], satellite);
            }

            primary.addSatellite(satellite);
        }
        return solarSystem;
    }

    private static class Planet {
        private final String name;
        private Planet primary = null;

        Planet(final String name) {
            this.name = name;
        }

        public void addSatellite(final Planet satellite) {
            satellite.primary = this;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Planet) {
                isEqual = ((Planet) otherAnswer).name.equals(this.name);
            }

            return isEqual;
        }
    }
}
