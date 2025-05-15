package cook.entities;
import java.util.Locale;

public class FoodOrder {
    private String orderId;
    private String customerName;
    private String deliveryTime;
    private boolean isSubscription;
    private String status;
    private int etaMinutes;
    private double totalAmount; // Added field for totalAmount

    public FoodOrder(String orderId, String customerName, String deliveryTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.deliveryTime = deliveryTime;
        this.status = "Scheduled"; // Default status
        this.etaMinutes = 60; // Default ETA of 1 hour
        this.totalAmount = calculateTotalAmount(); // Calculate the initial total amount
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getOrderId() {
        return orderId;
    }

   /* public void setDeliveryETA(int minutes) {
        this.etaMinutes = minutes;
    }*/

  /*  public void setSubscription(boolean isSubscription) {
        this.isSubscription = isSubscription;
        this.totalAmount = calculateTotalAmount(); // Recalculate total amount when subscription changes
    }*/

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    // Function to generate invoice
   /* public String generateInvoice() {
        String invoiceNumber = "INV-" + orderId.substring(1); // Create invoice number based on order ID
        String paymentStatus = "Pending"; // Default payment status
        return String.format("""
                Invoice Number: %s
                Order ID: %s
                Items: Grilled Salmon, Lemon Tart
                Total Amount: $%.2f
                Payment Status: %s
                """, invoiceNumber, orderId, totalAmount, paymentStatus);
    }*/

    // Function to calculate the total amount (example logic)
    private double calculateTotalAmount() {
        double baseAmount = 35.00; // Base amount for the items
        if (isSubscription) {
            baseAmount *= 0.9; // 10% discount for subscriptions
        }
        return baseAmount;
    }

    // Function to update delivery time with additional verification
  /*  public void updateDeliveryTime(String newDeliveryTime) {
        if (status.equals("Scheduled")) {
            this.deliveryTime = newDeliveryTime;
        } else {
            System.out.println("Cannot update delivery time, order is already " + status);
        }
    }*/

 /*   public String generateReminderNotification() {
        return String.format("""
            Reminder: Your meal delivery is scheduled to arrive at %s.
            Please be ready to receive your order. Track your delivery here: [Tracking Link]
            """, deliveryTime);
    }

    public String generate24hReminder() {
        return String.format("""
            Reminder: Your weekly meal plan delivery is scheduled for tomorrow at %s.
            Please confirm your availability or reschedule if needed.
            """, deliveryTime);
    }

    public String generateFinalNotification() {
        return String.format(Locale.ENGLISH, """
            Your order is arriving soon! The delivery is %d minutes away.
            Please be ready to receive your meal.
            """, etaMinutes);
    }*/

    // Getter for totalAmount
    public double getTotalAmount() {
        return totalAmount;
    }

    // Setter for totalAmount, in case you want to update it directly
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}