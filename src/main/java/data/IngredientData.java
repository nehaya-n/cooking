package data;
import cook .entities.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class IngredientData {
    private static final List<Ingredient> ingredientList = new ArrayList<>();

    static {
        initializeIngredients();
    }

    private static void initializeIngredients() {
        ingredientList.add(new Ingredient("Tomatoes", 10, 5));
        ingredientList.add(new Ingredient("Onions", 8, 5));
        ingredientList.add(new Ingredient("Olive Oil", 5, 3));
        ingredientList.add(new Ingredient("Milk", 2, 1));
        ingredientList.add(new Ingredient("Flour", 20, 10));

        // Simulating some low stock levels
        ingredientList.get(0).setStock(3); // Tomatoes now at 3kg
        ingredientList.get(1).setStock(2); // Onions now at 2kg
        ingredientList.get(2).setStock(1); // Olive Oil now at 1L
    }

    public static List<Ingredient> getAllIngredients() {
        return new ArrayList<>(ingredientList);
    }

    public static Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }
}
