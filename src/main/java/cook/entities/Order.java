package cook.entities;

import java.util.Objects;

public class Order {
    private String orderId;
    private String mealName;
    private String orderDate;
    private String status;

   
    public Order(String orderId, String mealName, String orderDate, String status) {
        this.orderId = orderId;
        this.mealName = mealName;
        this.orderDate = orderDate;
        this.status = status;
    }
 
    public Order(String mealName, String orderDate, String status) {
        this.mealName = mealName;
        this.orderDate = orderDate;
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // إذا كانت الكائنات هي نفس الكائن
        if (o == null || getClass() != o.getClass()) return false;  // تحقق من النوع
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&  // مقارنة orderId
               Objects.equals(mealName, order.mealName) &&  // مقارنة mealName
               Objects.equals(orderDate, order.orderDate) &&  // مقارنة orderDate
               Objects.equals(status, order.status);  // مقارنة status
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, mealName, orderDate, status);  // تأكد من أن الحقول المقارنة تتطابق
    }



    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getMealName() {
        return mealName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }
}