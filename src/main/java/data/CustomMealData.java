package data;

import cook.entities.CustomMeal;
import cook.entities.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class CustomMealData {

    private static final List<CustomMeal> customMeals = new ArrayList<>();
    private static final List<Ingredient> ingredients = new ArrayList<>();

    static {
        initializeIngredients();
        initializeCustomMeals();
    }

    // Initialize ingredients
    public static void initializeIngredients() {
        ingredients.add(new Ingredient("Chicken Breast", true));
        ingredients.add(new Ingredient("Quinoa", true));
        ingredients.add(new Ingredient("Broccoli", true));
        ingredients.add(new Ingredient("Garlic Sauce", true));
        ingredients.add(new Ingredient("Avocado", false)); // Unavailable ingredient
        ingredients.add(new Ingredient("Pineapple", true));
        ingredients.add(new Ingredient("Soy Sauce", true));
        ingredients.add(new Ingredient("Milk", true));
        ingredients.add(new Ingredient("Lemon Juice", true));
    }

    // Initialize custom meals
    public static void initializeCustomMeals() {
        List<Ingredient> mealIngredients1 = List.of(
                new Ingredient("Chicken Breast", true),
                new Ingredient("Quinoa", true),
                new Ingredient("Broccoli", true),
                new Ingredient("Garlic Sauce", true)
        );
        customMeals.add(new CustomMeal("Healthy Chicken Bowl", mealIngredients1));
    }

    // Adds a custom meal to the list
    public static void addCustomMeal(CustomMeal customMeal) {
        customMeals.add(customMeal);
    }

    // Gets all custom meals
    public static List<CustomMeal> getCustomMeals() {
        return new ArrayList<>(customMeals);
    }

    // Gets a custom meal by name
    public static CustomMeal getCustomMealByName(String name) {
        for (CustomMeal meal : customMeals) {
            if (meal.getName().equalsIgnoreCase(name)) {
                return meal;
            }
        }
        return null;
    }

    // Get ingredients by name
    public static Ingredient getIngredientByName(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                return ingredient;
            }
        }
        return null;
    }

    // Update ingredient availability
    public static void updateIngredientAvailability(String ingredientName, boolean availability) {
        Ingredient ingredient = getIngredientByName(ingredientName);
        if (ingredient != null) {
            ingredient.setAvailable(availability);
        }
    }

    // Check if the combination of ingredients is valid
    public static boolean validateIngredientCombination(List<String> selectedIngredients) {
        // Define incompatible combinations (example: Milk and Lemon Juice)
        List<String[]> incompatibleCombinations = List.of(
                new String[] {"Milk", "Lemon Juice"},
                new String[] {"Pineapple", "Soy Sauce"}
        );

        for (String[] combination : incompatibleCombinations) {
            if (selectedIngredients.contains(combination[0]) && selectedIngredients.contains(combination[1])) {
                return false; // Invalid combination
            }
        }
        return true; // Valid combination
    }

    // Verify if the ingredient is available
    public static boolean isIngredientAvailable(String ingredientName) {
        Ingredient ingredient = getIngredientByName(ingredientName);
        return ingredient != null && ingredient.isAvailable();
    }
}
