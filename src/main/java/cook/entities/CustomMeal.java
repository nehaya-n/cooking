package cook.entities;

import java.util.HashSet;
import java.util.Set;
import cook.entities.Ingredient;

public class CustomMeal {

    public String mealName;
    public Set<String> ingredients;
    public Set<Ingredient> ingredient1;
    private boolean saved;

    public CustomMeal(String mealName) {
        this.mealName = mealName;
        this.ingredients = new HashSet<>();
        this.saved = false;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);

    }

    public void replaceIngredient(String original, String substitute) {

        ingredient1.stream()
                .filter(ingredient -> ingredient.getName().equals(original))
                .forEach(ingredient -> {
                    ingredient.setName(substitute);

        });
    }

    public boolean containsIngredient(String ingredient) {
        return ingredients.contains(ingredient);
    }

    public void saveMeal() {
        this.saved = true;

    }

    public boolean isSaved() {
        return saved;
    }
    /*
        public String getMealName() {
            return mealName;
        }

        public Set<String> getIngredients() {
            return ingredients;
        }
    */
    public String getName() {
        return mealName;
    }
}
