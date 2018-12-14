package uk.co.pete_b.advent.aoc2018;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    public static final int CHARACTER_INTEGER_OFFSET = 48;

    public static String getNextTenRecipeScores(final int target) {
        final int targetRecipeLength = target + 10;
        final List<Integer> recipes = new ArrayList<>();
        int elfOneRecipeScore = 3;
        int elfTwoRecipeScore = 7;
        recipes.add(elfOneRecipeScore);
        recipes.add(elfTwoRecipeScore);
        int elfOnePosition = 0;
        int elfTwoPosition = 1;

        while (recipes.size() < targetRecipeLength) {
            final int total = elfOneRecipeScore + elfTwoRecipeScore;
            if (total > 9) {
                recipes.add(1);
                recipes.add(total % 10);
            } else {
                recipes.add(total);
            }
            elfOnePosition = (elfOnePosition + 1 + elfOneRecipeScore) % recipes.size();
            elfTwoPosition = (elfTwoPosition + 1 + elfTwoRecipeScore) % recipes.size();
            elfOneRecipeScore = recipes.get(elfOnePosition);
            elfTwoRecipeScore = recipes.get(elfTwoPosition);
        }

        return StringUtils.join(recipes.subList(target, targetRecipeLength).toArray(new Integer[0]));
    }

    public static int findRecipesBeforeMatchingString(final String targetString) {
        final int targetStringLength = targetString.length();
        final StringBuilder recipeList = new StringBuilder();

        int elfOneRecipeScore = 3;
        int elfTwoRecipeScore = 7;
        recipeList.append(elfOneRecipeScore);
        recipeList.append(elfTwoRecipeScore);
        int elfOnePosition = 0;
        int elfTwoPosition = 1;

        int recipeListLength = 2;

        while (recipeListLength - targetStringLength < targetStringLength ||
                !recipeList.substring(recipeListLength - targetStringLength).equals(targetString)) {

            final int total = elfOneRecipeScore + elfTwoRecipeScore;
            if (total > 9) {
                recipeList.append(1);
                recipeList.append(total % 10);
                recipeListLength += 2;
            } else {
                recipeList.append(total);
                recipeListLength++;
            }
            elfOnePosition = (elfOnePosition + 1 + elfOneRecipeScore) % recipeListLength;
            elfTwoPosition = (elfTwoPosition + 1 + elfTwoRecipeScore) % recipeListLength;
            elfOneRecipeScore = recipeList.charAt(elfOnePosition) - CHARACTER_INTEGER_OFFSET;
            elfTwoRecipeScore = recipeList.charAt(elfTwoPosition) - CHARACTER_INTEGER_OFFSET;
        }

        return recipeList.indexOf(targetString);
    }
}
