package cook.entities;
import java.util.Locale;
public class MealOrder {
    private String orderId;
    private String customerName;
    private String deliveryTime;
    private boolean isSubscription;
    private String status;
    private int etaMinutes;

    public MealOrder(String orderId, String customerName, String deliveryTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.deliveryTime = deliveryTime;
        this.status = "Scheduled";
        this.etaMinutes = 60; // الافتراضي ساعة
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryETA(int minutes) {
        this.etaMinutes = minutes;
    }

    public void setSubscription(boolean isSubscription) {
        this.isSubscription = isSubscription;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String generateReminderNotification() {
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
    }
}
