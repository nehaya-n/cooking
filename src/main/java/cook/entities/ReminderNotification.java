package cook.entities;

public class ReminderNotification {

    private String message;
    private String deliveryTime;
    private String advanceNotice;
    private String type;

    // Constructor to initialize the object
    public ReminderNotification(String message, String deliveryTime, String advanceNotice, String type) {
        this.message = message;
        this.deliveryTime = deliveryTime;
        this.advanceNotice = advanceNotice;
        this.type = type;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and Setter for deliveryTime
    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    // Getter and Setter for advanceNotice
    public String getAdvanceNotice() {
        return advanceNotice;
    }

    public void setAdvanceNotice(String advanceNotice) {
        this.advanceNotice = advanceNotice;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Method to display reminder details
    @Override
    public String toString() {
        return "ReminderNotification{" +
                "message='" + message + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", advanceNotice='" + advanceNotice + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
