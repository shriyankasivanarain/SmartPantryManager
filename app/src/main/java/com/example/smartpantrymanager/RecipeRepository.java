package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class providing a pre-loaded catalog of 15+ recipes
 * with names, required ingredients, and preparation steps as required by the assignment brief.
 */
public class RecipeRepository {

    public static List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        // 1. Simple Omelette
        recipes.add(new Recipe(
                "Simple Omelette",
                Arrays.asList("eggs", "milk", "butter"),
                "1. Whisk eggs and milk together in a bowl.\n2. Melt butter in a pan over medium heat.\n3. Pour mixture and cook until set."
        ));

        // 2. Butter Toast
        recipes.add(new Recipe(
                "Butter Toast",
                Arrays.asList("bread", "butter"),
                "1. Toast the bread slices until golden brown.\n2. Spread butter evenly while warm."
        ));

        // 3. Fresh Garden Salad
        recipes.add(new Recipe(
                "Fresh Garden Salad",
                Arrays.asList("lettuce", "tomato", "cucumber"),
                "1. Chop lettuce, tomatoes, and cucumber.\n2. Toss together in a bowl and serve fresh."
        ));

        // 4. Cheese Sandwich
        recipes.add(new Recipe(
                "Cheese Sandwich",
                Arrays.asList("bread", "cheese", "butter"),
                "1. Place cheese between two slices of bread.\n2. Butter the outside and grill in a pan until golden."
        ));

        // 5. Classic Scrambled Eggs
        recipes.add(new Recipe(
                "Classic Scrambled Eggs",
                Arrays.asList("eggs", "butter", "salt"),
                "1. Whisk eggs with a pinch of salt.\n2. Melt butter in a pan, add eggs, and stir gently until soft curds form."
        ));

        // 6. Tomato Pasta
        recipes.add(new Recipe(
                "Tomato Pasta",
                Arrays.asList("pasta", "tomato", "garlic"),
                "1. Boil pasta until tender.\n2. Sauté garlic and chopped tomatoes to make a sauce, then toss with pasta."
        ));

        // 7. Garlic Bread
        recipes.add(new Recipe(
                "Garlic Bread",
                Arrays.asList("bread", "garlic", "butter"),
                "1. Mix minced garlic with softened butter.\n2. Spread on bread slices and bake until crispy."
        ));

        // 8. Fried Egg Rice
        recipes.add(new Recipe(
                "Fried Egg Rice",
                Arrays.asList("rice", "eggs", "oil"),
                "1. Cook rice and let cool.\n2. Scramble eggs in a pan with oil, then mix in the cooked rice and stir-fry."
        ));

        // 9. Cucumber Salad
        recipes.add(new Recipe(
                "Cucumber Salad",
                Arrays.asList("cucumber", "salt", "vinegar"),
                "1. Slice cucumbers thinly.\n2. Toss with salt and a splash of vinegar, then chill before serving."
        ));

        // 10. Boiled Eggs
        recipes.add(new Recipe(
                "Boiled Eggs",
                Arrays.asList("eggs", "water"),
                "1. Place eggs in boiling water for 8-10 minutes.\n2. Cool in ice water before peeling."
        ));

        // 11. Butter Rice
        recipes.add(new Recipe(
                "Butter Rice",
                Arrays.asList("rice", "butter", "salt"),
                "1. Cook white rice.\n2. Stir in butter and salt while hot until melted and fluffy."
        ));

        // 12. Cheese Omelette
        recipes.add(new Recipe(
                "Cheese Omelette",
                Arrays.asList("eggs", "cheese", "butter"),
                "1. Whisk eggs and pour into a buttered pan.\n2. Add cheese in the center, fold over, and cook until melted."
        ));

        // 13. Tomato Soup
        recipes.add(new Recipe(
                "Tomato Soup",
                Arrays.asList("tomato", "garlic", "water"),
                "1. Boil tomatoes and garlic in water until soft.\n2. Blend into a smooth soup and season to taste."
        ));

        // 14. French Toast
        recipes.add(new Recipe(
                "French Toast",
                Arrays.asList("bread", "eggs", "milk"),
                "1. Whisk eggs and milk together.\n2. Dip bread slices into the mixture and pan-fry until golden brown."
        ));

        // 15. Garlic Rice
        recipes.add(new Recipe(
                "Garlic Rice",
                Arrays.asList("rice", "garlic", "oil"),
                "1. Sauté minced garlic in oil until golden.\n2. Add cooked rice and stir-fry until fragrant."
        ));

        return recipes;
    }
}