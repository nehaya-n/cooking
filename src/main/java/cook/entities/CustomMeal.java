package cook.entities;

import java.util.HashSet;
import java.util.Set;


public class CustomMeal {

    public String mealName;
    public Set<String> ingredients;
    private boolean saved;

    public CustomMeal(String mealName) {
        this.mealName = mealName;
        this.ingredients = new HashSet<>();
        this.saved = false;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);

    }

    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient);

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
