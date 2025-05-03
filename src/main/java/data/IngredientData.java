package data;

import cook.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientData {
    private static final List<Ingredient> ingredients = new ArrayList<>();

    static {
        initializeIngredients();
    }

    public static void initializeIngredients() {
        ingredients.add(new Ingredient("Tomatoes", 3 , 5));
        ingredients.add(new Ingredient("Onions", 15, 5));
        ingredients.add(new Ingredient("Olive Oil", 5, 3));
        ingredients.add(new Ingredient("Milk", 8, 2));
        ingredients.add(new Ingredient("Avocado", 4, 3));
        ingredients.add(new Ingredient("Cheese", 4, 3));
        ingredients.add(new Ingredient("Chicken",20, 10));
        ingredients.add(new Ingredient("Pasta",10, 7));
         
                
    }

    public static List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public static Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    public static void updateStock(String name, int newStock) {
        Ingredient ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.setStock(newStock);
        }
    }

    public static boolean isLowStock(String name) {
        Ingredient ingredient = getIngredientByName(name);
        return ingredient != null && ingredient.getStock() <= ingredient.getLowStockThreshold();
        // Changed < to <=
    }

    public static void acknowledgeAlert(String name) {
        Ingredient ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.acknowledgeAlert();
        }
    }

}

