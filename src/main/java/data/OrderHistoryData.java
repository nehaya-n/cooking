package data;

import cook.entities.CustomMeal;
import cook.entities.OrderHistory;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryData {
    private final OrderHistory orderHistory;

    public OrderHistoryData() {
        orderHistory = new OrderHistory();
        orderHistory.setMeals(new ArrayList<>());
    }

    public void initialize() {
        CustomMeal meal1 = new CustomMeal("Grilled Salmon");
        CustomMeal meal2 = new CustomMeal("Vegan Pesto Pasta");
        CustomMeal meal3 = new CustomMeal("Chicken Caesar Salad");


        add(meal1);
        add(meal2);
        add(meal3);
    }

    public void add(CustomMeal meal) {
        orderHistory.getMeals().add(meal);
    }

    public List<CustomMeal> getOrderHistory() {
        return orderHistory.getMeals();
    }



}
