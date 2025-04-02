package data;

import cook.entities.Integratewithsupp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntegratewithsuppData {
    private static final List<Integratewithsupp> ingredients = new ArrayList<>();

    static {
        initializeIngredients();
    }

    // Initialize sample ingredients with prices from different suppliers
    public static void initializeIngredients() {
        Map<String, Double> tomatoPrices = Map.of("Supplier A", 2.50, "Supplier B", 3.30, "Supplier C", 2.75);
        Map<String, Double> oliveOilPrices = Map.of("Supplier A", 5.00, "Supplier B", 4.80, "Supplier C", 5.20);
        Map<String, Double> chickenPrices = Map.of("Supplier A", 8.00, "Supplier B", 7.50, "Supplier C", 7.00);

        addIngredient("Tomatoes", 10, 5, tomatoPrices);
        addIngredient("Olive Oil", 5, 3, oliveOilPrices);
        addIngredient("Chicken", 15, 8, chickenPrices);
    }

    // Adds a new ingredient to the list with prices
    public static void addIngredient(String name, int stock, int lowStockThreshold, Map<String, Double> prices) {
        Integratewithsupp newIngredient = new Integratewithsupp(name, stock, lowStockThreshold, prices);
        ingredients.add(newIngredient);
    }

    // Get all ingredients
    public static List<Integratewithsupp> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    // Get ingredient by name
    public static Integratewithsupp getIngredientByName(String name) {
        for (Integratewithsupp ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    // Update the stock of an ingredient
    public static void updateStock(String name, int newStock) {
        Integratewithsupp ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.setStock(newStock);
        }
    }

    // Check if the ingredient stock is low
    public static boolean isLowStock(String name) {
        Integratewithsupp ingredient = getIngredientByName(name);
        return ingredient != null && ingredient.getStock() <= ingredient.getLowStockThreshold();
    }

    // Acknowledge the low stock alert
    public static void acknowledgeAlert(String name) {
        Integratewithsupp ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.setAlertAcknowledged(true);
        }
    }
}
