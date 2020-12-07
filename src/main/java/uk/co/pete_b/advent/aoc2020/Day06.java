package uk.co.pete_b.advent.aoc2020;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day06 {

    public static int countUniqueDeclarationGroups(final String input) {
        return Arrays.stream(input.split("\n\n")).mapToInt(Day06::countUniqueGroups).sum();
    }

    public static long countDeclarationGroups(final String input) {
        return Arrays.stream(input.split("\n\n")).mapToLong(Day06::countCompleteGroups).sum();
    }

    private static int countUniqueGroups(final String declarations) {
        return Arrays.stream(declarations.split("\n"))
                .flatMapToInt(String::chars)
                .boxed()
                .collect(Collectors.groupingBy(Integer::intValue))
                .size();
    }

    private static long countCompleteGroups(final String declarations) {
        final String[] members = declarations.split("\n");

        return Arrays.stream(members)
                .flatMapToInt(String::chars)
                .boxed()
                .collect(Collectors.groupingBy(Integer::intValue))
                .values()
                .stream()
                .filter(group -> group.size() == members.length)
                .count();
    }

}
