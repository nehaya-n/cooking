package data;
import cook .entities.Customer;
import cook .entities.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealData {
    private static final List<Meal> meals = new ArrayList<>();

    public static void addMeal(Meal meal) {
        meals.add(meal);
    }

    public static List<Meal> getMealRecommendations(Customer customer) {
        // Filter meals based on customer's dietary preferences
        return meals.stream()
                .filter(meal -> (meal.isVegetarian() == customer.isVegetarian()) &&
                        (meal.isGlutenFree() == customer.isGlutenFree()))
                .collect(Collectors.toList());
    }

    public static List<Meal> getSuggestedMeals() {
        return meals; // This could be modified to return only the recommended meals
    }
}

// Customer class to hold dietary preferences
