package cook.entities;

import java.util.List;

public class Chef {
    private String name;
    private int workload;

    public Chef(String name, int workload) {
        this.name = name;
        this.workload = workload;
    }

    public String getName() {
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
    }

    // إضافة الطريقة التي يمكن أن تتيح للـ Chef الوصول إلى سجل الطلبات الخاصة بالعميل
    public void accessOrderHistory(Customer customer) {
        List<Order> pastOrders = customer.getPastOrders();  // الحصول على الطلبات السابقة للعميل
        // الآن يمكن استخدام بيانات pastOrders لمساعدتك في تخصيص خطة الوجبات
        System.out.println("Accessing Customer's Order History: " + pastOrders);
    }

    public void login() {
        System.out.println(name + " has logged in.");
    }
}
