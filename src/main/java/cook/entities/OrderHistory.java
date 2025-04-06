package cook.entities;

import java.util.List;

public class OrderHistory {
    private List<CustomMeal> meals;

    // Getter and Setter for meals
    public List<CustomMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<CustomMeal> meals) {
        this.meals = meals;
    }
}
