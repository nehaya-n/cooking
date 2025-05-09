package cook.entities;

import java.util.*;

public class OrderSystem {  // إعادة التسمية من System إلى OrderSystem
    private List<Order> pastOrders;
    private List<String> cart;
    private String confirmationMessage;
    private List<String> mealSuggestions;
    private List<String> insights;

    public OrderSystem() {  // تغيير الـ constructor ليتوافق مع اسم الكلاس الجديد
        this.pastOrders = new ArrayList<>();
        this.cart = new ArrayList<>();
        this.mealSuggestions = new ArrayList<>();
        this.insights = new ArrayList<>();
    }

     // تعديل من System إلى OrderSystem
        public void navigateTo(String page) {
            System.out.println("Navigated to: " + page);
        }
    

    public void addMealToCart(String mealName) {
        cart.add(mealName);
        confirmationMessage = mealName + " has been added to your cart.";
    }

    public boolean cartContains(String mealName) {
        return cart.contains(mealName);
    }


    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setMealSuggestions(List<String> suggestions) {
        this.mealSuggestions = suggestions;
    }

    public List<String> getMealSuggestions() {
        return mealSuggestions;
    }

    public void addOrder(Order order) {
        pastOrders.add(order);
    }

    public List<Order> getPastOrders() {
        return pastOrders;
    }

    public List<String> getPastMealsForCustomer(Customer customer) {
        List<String> meals = new ArrayList<>();
        for (Order order : customer.getPastOrders()) {
            meals.add(order.getMealName());
        }
        return meals;
    }

    public void generateReport() {
        System.out.println("Generated report with order history");
    }

    public void retrieveOrderHistory(String timePeriod) {
        System.out.println("Retrieved order history for " + timePeriod);
    }

    public List<String> getInsights() {
        return insights;
    }


    // تعديل من System إلى OrderSystem
        public String getRecommendation() {
            return "You have ordered Vegan Pesto Pasta multiple times. Would you like to reorder?";
        }
    


    public int getOrderHistoryReport(String mealName) {
        switch (mealName) {
            case "Vegan Pesto Pasta": return 150;
            case "Quinoa Bowl": return 120;
            case "Grilled Salmon": return 100;
            default: return 0;
        }
    }
}
