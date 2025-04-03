package data;

import cook.entities.ReminderNotification;

import java.util.ArrayList;
import java.util.List;

public class ReminderNotificationData {
    private static final List<ReminderNotification> reminders = new ArrayList<>();

    static {
        initializeReminders();
    }

    // Initialize sample reminders
    public static void initializeReminders() {
        // Sample reminder for customer
        addReminder("Your meal delivery is scheduled to arrive at 12:30 PM. Please be ready to receive your order. Track your delivery here: [Tracking Link]", "12:30 PM", null, "Customer Meal Delivery Reminder");

        // Sample reminder for chef
        addReminder("You have a scheduled cooking task for Grilled Salmon at 10:00 AM. Please start preparing the meal on time.", "10:00 AM", null, "Chef Cooking Task Reminder");

        // Sample reminder for subscription
        addReminder("Your weekly meal plan delivery is scheduled for tomorrow at 1:00 PM. Please confirm your availability or reschedule if needed.", "1:00 PM", "24 hours", "Customer Meal Subscription Reminder");

        // Sample daily summary for chef
        addReminder("Today's Cooking Schedule:\n- 10:00 AM: Grilled Salmon (Order #1234)\n- 12:30 PM: Vegan Pasta (Order #5678)\n- 3:00 PM: Chicken Stir-Fry (Order #9101)\nPlease prepare meals on time. Good luck!", null, null, "Chef Daily Summary Reminder");

        // Sample final delivery confirmation for customer
        addReminder("Your order is arriving soon! The delivery is 10 minutes away. Please be ready to receive your meal.", "10 minutes", null, "Customer Final Delivery Reminder");
    }

    // Adds a new reminder to the list
    public static void addReminder(String message, String deliveryTime, String advanceNotice, String type) {
        ReminderNotification newReminder = new ReminderNotification(message, deliveryTime, advanceNotice, type);
        reminders.add(newReminder);
    }

    // Get all reminders
    public static List<ReminderNotification> getReminders() {
        return new ArrayList<>(reminders);
    }

    // Get reminder by type
    public static ReminderNotification getReminderByType(String type) {
        for (ReminderNotification reminder : reminders) {
            if (reminder.getType().equalsIgnoreCase(type)) {
                return reminder;
            }
        }
        return null;
    }

    // Update the reminder message
    public static void updateReminderMessage(String type, String newMessage) {
        ReminderNotification reminder = getReminderByType(type);
        if (reminder != null) {
            reminder.setMessage(newMessage);
        }
    }
}
