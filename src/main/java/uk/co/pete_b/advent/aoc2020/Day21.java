package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    public static Answer countAllergenAbsentIngredients(final String inputData) {
        final Map<String, String> allergenToIngredientMap = new HashMap<>();
        final Map<String, String> ingredientToAllergenMap = new HashMap<>();
        final Set<String> allergens = new HashSet<>();

        final List<Set<String>> recipeList = new ArrayList<>();

        final Map<String, Set<Integer>> allergenToRecipeMap = new HashMap<>();

        final String[] recipeStrings = inputData.split("\n");
        for (int i = 0; i < recipeStrings.length; i++) {
            final int recipeIndex = i;
            final String recipe = recipeStrings[i];
            final String[] parts = recipe.split(" \\(contains ");
            final Set<String> ingredients = Arrays.stream(parts[0].split(" ")).collect(Collectors.toSet());
            final Set<String> recipeAllergens = Arrays.stream(parts[1].substring(0, parts[1].length() - 1).split(", ")).collect(Collectors.toSet());
            recipeList.add(ingredients);

            for (String allergen : recipeAllergens) {
                allergens.add(allergen);
                allergenToRecipeMap.compute(allergen, (key, allergenRecipes) -> {
                    if (allergenRecipes == null) {
                        allergenRecipes = new HashSet<>();
                    }
                    allergenRecipes.add(recipeIndex);

                    return allergenRecipes;
                });
            }
        }

        final Map<String, Set<String>> possibleAllergenMatches = new HashMap<>();
        for (String allergen : allergens) {
            final Set<String> possibleMatches = new HashSet<>();
            final Set<Integer> recipes = allergenToRecipeMap.get(allergen);
            for (Integer recipeId : recipes) {
                final Set<String> recipeContents = recipeList.get(recipeId);
                for (String ingredient : recipeContents) {
                    int timesEncountered = 1;
                    timesEncountered += recipes.stream()
                            .filter(secondRecipeId -> secondRecipeId.intValue() != recipeId.intValue())
                            .filter(secondRecipeId -> recipeList.get(secondRecipeId).contains(ingredient))
                            .count();

                    if (timesEncountered == recipes.size()) {
                        possibleMatches.add(ingredient);
                    }
                }
            }

            possibleAllergenMatches.put(allergen, possibleMatches);
        }

        while (allergenToIngredientMap.size() != allergens.size()) {
            final Map.Entry<String, Set<String>> correctMatch = possibleAllergenMatches.entrySet().stream().filter(x -> x.getValue().size() == 1).findFirst().orElseThrow();
            final String allergen = correctMatch.getKey();
            final String ingredient = correctMatch.getValue().iterator().next();

            possibleAllergenMatches.forEach((key, value) -> value.remove(ingredient));

            allergenToIngredientMap.put(allergen, ingredient);
            ingredientToAllergenMap.put(ingredient, allergen);
        }


        return new Answer(recipeList.stream().flatMap(Collection::stream).filter(x -> !ingredientToAllergenMap.containsKey(x)).count(),
                allergens.stream().sorted().map(allergenToIngredientMap::get).collect(Collectors.joining(",")));
    }

    public static class Answer {
        private final long safeIngredients;
        private final String dangerousIngredientList;

        Answer(long safeIngredients, String dangerousIngredientList) {
            this.safeIngredients = safeIngredients;
            this.dangerousIngredientList = dangerousIngredientList;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).safeIngredients == this.safeIngredients && ((Answer) otherAnswer).dangerousIngredientList.equals(this.dangerousIngredientList);
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Safe Ingredient Count: %d, Dangerous Ingredient List: %s", safeIngredients, dangerousIngredientList);
        }
    }
}
