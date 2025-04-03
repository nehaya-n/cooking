package data;

import cook.entities.Ingredient;
import cook.entities.CustomerDietaryPreferences;
import java.util.HashMap;
import java.util.Map;

public class IngredientSubstitutionData {
    public static final Map<String, Ingredient> ingredients = new HashMap<>();
    public static final Map<String, CustomerDietaryPreferences> customerPreferences = new HashMap<>();


    public static void initializePreferences() {
        customerPreferences.put("Lactose-Free", new CustomerDietaryPreferences("Lactose-Free"));
        customerPreferences.put("Vegan", new CustomerDietaryPreferences("Vegan"));
        customerPreferences.put("Gluten-Free", new CustomerDietaryPreferences("Gluten-Free"));
    }

    public static Ingredient getIngredientByName(String name) {
        return ingredients.get(name);
    }

    public static void updateStock(String name, int stock) {
        Ingredient ingredient = ingredients.get(name);
        if (ingredient != null) {
            ingredient.setStock(stock);
        }
    }

    public static String checkIngredientSubstitution(String ingredient, String substitute) {
        Ingredient ing = IngredientSubstitutionData.getIngredientByName(ingredient);
        if (ing != null && ing.getStock() == 0) {
            return ingredient + " is unavailable. Would you like to use " + substitute + " as a substitute?";
        }
        return "";
    }

    public static String generateChefNotification(String originalIngredient, String substitutedIngredient) {
        return "Customer has requested a substitution: " + originalIngredient + " → " + substitutedIngredient + ". Please review and approve the modification.";
    }

    public static String generateSubstitutionSummary(String[] originalIngredients, String[] substitutedIngredients) {
        StringBuilder summary = new StringBuilder("The following ingredient substitutions have been made:\n");
        for (int i = 0; i < originalIngredients.length; i++) {
            summary.append("- ").append(originalIngredients[i]).append(" → ").append(substitutedIngredients[i]).append("\n");
        }
        summary.append("Please confirm before proceeding with your order.");
        return summary.toString();
    }

    public static String validateSubstitution(String ingredient, String substitute, String dietaryRestriction) {
        if (ingredient.equals("Whole Wheat Bread") && substitute.equals("Regular Bread") &&
                dietaryRestriction.equals("Vegan")) {
            return "Regular Bread is not suitable for a gluten-free diet. Please choose a different option.";
        }
        return "";
    }
}

