package cook.entities;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    public static String name;
    public boolean isVegetarian;
    public boolean isGlutenFree;
    private List<Order> pastOrders;

    // الحقول المفقودة
    private String favoriteMeal;  // لتخزين الوجبة المفضلة
    private int timesOrdered;     // لتخزين عدد مرات طلب الوجبة المفضلة

    // البناء الذي يستقبل معلمات String, boolean, boolean
    public Customer(String name, boolean isVegetarian, boolean isGlutenFree) {
        Customer.name = name;
        this.isVegetarian = isVegetarian;
        this.isGlutenFree = isGlutenFree;
        this.pastOrders = new ArrayList<>();
    }

    // دوال الوصول (Getters) للوصول إلى الحقول
    public static String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public String getFavoriteMeal() {
        return favoriteMeal;
    }

    public int getTimesOrdered() {
        return timesOrdered;
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
        this.favoriteMeal = mealName;  // تعيين الوجبة المفضلة
        this.timesOrdered = timesOrdered;  // تعيين عدد المرات
        System.out.println("Favorite meal set to: " + mealName + " with " + timesOrdered + " orders.");
    }
}
