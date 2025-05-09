package cook.entities;

public class Order {
    private String mealName;
    private String orderDate;
    private String status;

    public Order(String mealName, String orderDate, String status) {
        this.mealName = mealName;
        this.orderDate = orderDate;
        this.status = status;
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
