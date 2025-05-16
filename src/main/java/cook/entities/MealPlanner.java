package cook.entities;

import java.util.List;

public class MealPlanner {
    private String name;
    private int workload;

    public MealPlanner(String name, int workload) {
        this.name = name;
        this.workload = workload;
    }

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }*/

    // إضافة الطريقة التي تتيح للطاهي الوصول إلى سجل الطلبات الخاصة بالعميل
    public void accessOrderHistory(Customer customer) {
        List<Order> pastOrders = customer.getPastOrders();  // الحصول على الطلبات السابقة للعميل
        // الآن يمكن استخدام بيانات pastOrders لمساعدتك في تخصيص خطة الوجبات
        System.out.println("Accessing Customer's Order History: " + pastOrders);
    }

  /*  // إضافة الطريقة لتحضير خطة الوجبات للعميل
    public void prepareMealPlanForCustomer(Customer customer) {
        List<Order> pastOrders = customer.getPastOrders();
        
        // منطق تحضير خطة الوجبات بناءً على الطلبات السابقة
        System.out.println("Preparing a meal plan for Customer: " + customer.getName());
        
        // مثال بسيط: الطاهي يوصي بالوجبات المفضلة بناءً على الطلبات السابقة
        for (Order order : pastOrders) {
            System.out.println("Suggested meal: " + order.getMealName() + " based on past orders.");
        }
    }
*/
    public void login() {
        System.out.println(name + " has logged in.");
    }
}