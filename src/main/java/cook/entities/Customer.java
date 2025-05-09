package cook.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    public static String name;
    public boolean isVegetarian;
    public boolean isGlutenFree;
    private List<Order> pastOrders;  // قائمة لتخزين الطلبات السابقة
    private String favoriteMeal;
    private int timesOrdered; // عدد مرات طلب الوجبة المفضلة

    public Customer(String name, boolean isVegetarian, boolean isGlutenFree) {
        Customer.name = name;
        this.isVegetarian = isVegetarian;
        this.isGlutenFree = isGlutenFree;
        this.pastOrders = new ArrayList<>();  // تهيئة قائمة الطلبات السابقة
    }

    public static String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    // إضافة طريقة لإضافة طلبات سابقة
    public void addPastOrders(Order order) {
        this.pastOrders.add(order);  // إضافة الطلب إلى قائمة الطلبات السابقة
    }

    // إرجاع قائمة الطلبات السابقة
    public List<Order> getPastOrders() {
        return this.pastOrders;  // إرجاع قائمة الطلبات السابقة
    }

    // إضافة طريقة تسجيل الدخول
    public void login() {
        System.out.println(name + " has logged in.");
    }

    // إضافة طريقة لتعيين الوجبة المفضلة وعدد مرات طلبها
    public void setFavoriteMeal(String mealName, int timesOrdered) {
        this.favoriteMeal = mealName;
        this.timesOrdered = timesOrdered;
        System.out.println("Favorite meal set to: " + mealName + " with " + timesOrdered + " orders.");
    }
}
