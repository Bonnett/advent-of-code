package uk.co.pete_b.advent.aoc2018;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day03 {

    public static Answers getAnswers(final List<String> input) {
        final List<Claim> claims = input.stream().map(Claim::new).collect(Collectors.toList());
        final List<Integer> allClaims = claims.stream().map(claim -> claim.id).collect(Collectors.toList());
        final Set<Integer> overlaps = new HashSet<>();

        final Integer maxWidth = claims.stream().map(claim -> claim.width + claim.startX).max(Integer::compareTo).orElse(2000);
        final Integer maxHeight = claims.stream().map(claim -> claim.height + claim.startY).max(Integer::compareTo).orElse(2000);

        final List<List<List<Integer>>> grid = new ArrayList<>(maxHeight);

        for (int y = 0; y < maxHeight; y++) {
            grid.add(new ArrayList<>());
            for (int x = 0; x < maxWidth; x++) {
                grid.get(y).add(new ArrayList<>());
            }
        }

        for (Claim claim : claims) {
            for (int y = claim.startY; y < claim.startY + claim.height; y++) {
                for (int x = claim.startX; x < claim.startX + claim.width; x++) {
                    final List<Integer> entry = grid.get(y).get(x);
                    entry.add(claim.id);
                }
            }
        }

        int totalDuplicates = 0;

        for (List<List<Integer>> row : grid) {
            for (List<Integer> entry : row) {
                if (entry.size() > 1) {
                    totalDuplicates++;
                    overlaps.addAll(entry);
                }
            }
        }

        allClaims.removeAll(overlaps);

        return new Answers(totalDuplicates, allClaims.get(0));
    }

    private static class Claim {
        private final int id;

        private final int startX;
        private final int startY;

        private final int width;
        private final int height;

        Claim(final String input) {
            final String[] parts = input.split(" ");

            this.id = Integer.valueOf(parts[0].replaceAll("#", ""));

            final String[] start = parts[2].replaceAll(":", "").split(",");
            this.startX = Integer.valueOf(start[0]);
            this.startY = Integer.valueOf(start[1]);

            final String[] dimension = parts[3].split("x");
            this.width = Integer.valueOf(dimension[0]);
            this.height = Integer.valueOf(dimension[1]);
        }
    }

    public static class Answers {
        private final int numberOfDuplicates;
        private final int idOfNonOverlapping;

        Answers(final int numberOfDuplicates, final int idOfNonOverlapping) {
            this.numberOfDuplicates = numberOfDuplicates;
            this.idOfNonOverlapping = idOfNonOverlapping;
        }

        int getNumberOfDuplicates() {
            return numberOfDuplicates;
        }

        int getIdOfNonOverlapping() {
            return idOfNonOverlapping;
        }
    }
}
