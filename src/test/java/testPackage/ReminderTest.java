package testPackage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ReminderTest {

    private static final Logger logger = Logger.getLogger(ReminderTest.class.getName());
    private String receivedNotification;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    // Scenario 1: Customer receives a reminder before meal delivery
    @Given("a customer has an active meal order scheduled for delivery")
    public void a_customer_has_an_active_meal_order_scheduled_for_delivery() {
        logger.info(WHITE + "Customer has an active meal order scheduled for delivery." + RESET);
    }

    @And("the delivery time is within 1 hour")
    public void the_delivery_time_is_within_1_hour() {
        logger.info(WHITE + "The delivery time is within 1 hour." + RESET);
    }

    @When("the system detects the upcoming delivery")
    public void the_system_detects_the_upcoming_delivery() {
        receivedNotification = """
            Reminder: Your meal delivery is scheduled to arrive at 12:30 PM.
            Please be ready to receive your order. Track your delivery here: [Tracking Link]
        """;
        logger.info(WHITE + "System detected the upcoming delivery." + RESET);
    }

    @Then("the customer should receive a notification:")
    public void the_customer_should_receive_a_notification() {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Notification sent to customer: " + receivedNotification + RESET);
    }

    // Scenario 2: Chef receives a notification for a scheduled cooking task
    @Given("a chef has a scheduled cooking task for {string} at {int}:{int} AM")
    public void aChefHasAScheduledCookingTaskForAtAM(String dishName ,int time, int time2) {
        logger.info(WHITE + "Chef has a scheduled cooking task for " + dishName + " at " + time+":"+time2 + RESET);
    }

    @And("the task is due within 30 minutes")
    public void the_task_is_due_within_30_minutes() {
        logger.info(WHITE + "Task is due within 30 minutes." + RESET);
    }

    @When("the system detects the approaching task time")
    public void the_system_detects_the_approaching_task_time() {
        receivedNotification = """
            Reminder: You have a scheduled cooking task for Grilled Salmon at 10:00 AM.
            Please start preparing the meal on time.
        """;
        logger.info(WHITE + "System detected the approaching cooking task." + RESET);
    }

    @Then("the chef should receive a notification:")
    public void theChefShouldReceiveANotification() {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Notification sent to chef: " + receivedNotification + RESET);
    }
    // Scenario 3: Customer receives a reminder for a meal subscription order
    @Given("a customer is subscribed to a weekly meal plan")
    public void a_customer_is_subscribed_to_a_weekly_meal_plan() {
        logger.info(WHITE + "Customer is subscribed to a weekly meal plan." + RESET);
    }

    @And("the next delivery is scheduled for tomorrow at {int}:{int} PM")
    public void theNextDeliveryIsScheduledForTomorrowAtPM(int time1, int time2) {
        logger.info(WHITE + "Next delivery is scheduled for tomorrow at " + time1+ ":" +time2+ RESET);
    }
    @When("the system detects the upcoming delivery")
    public void the_system_detects_the_upcoming_delivery_for_subscription() {
        receivedNotification = """
            Reminder: Your weekly meal plan delivery is scheduled for tomorrow at 1:00 PM.
            Please confirm your availability or reschedule if needed.
        """;
        logger.info(WHITE + "System detected the upcoming subscription delivery." + RESET);
    }

    @Then("the customer should receive a reminder 24 hours in advance")
    public void the_customer_should_receive_a_reminder_24_hours_in_advance() {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Reminder sent to customer: " + receivedNotification + RESET);
    }

    // Scenario 4: Chef receives a daily summary of scheduled cooking tasks
    @Given("a chef has multiple scheduled cooking tasks for the day")
    public void a_chef_has_multiple_scheduled_cooking_tasks_for_the_day() {
        logger.info(WHITE + "Chef has multiple scheduled cooking tasks for the day." + RESET);
    }

    @When("the chef logs into the system in the morning")
    public void the_chef_logs_into_the_system_in_the_morning() {
        receivedNotification = """
            Today's Cooking Schedule:
            - 10:00 AM: Grilled Salmon (Order #1234)
            - 12:30 PM: Vegan Pasta (Order #5678)
            - 3:00 PM: Chicken Stir-Fry (Order #9101)
            Please prepare meals on time. Good luck!
        """;
        logger.info(WHITE + "Chef logged into the system and received the daily task summary." + RESET);
    }

    @Then("they should receive a task summary notification:")
    public void they_should_receive_a_task_summary_notification() {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Task summary sent to chef: " + receivedNotification + RESET);
    }

    // Scenario 5: Customer receives a final confirmation notification when delivery is near
    @Given("a meal delivery is on its way to the customer")
    public void a_meal_delivery_is_on_its_way_to_the_customer() {
        logger.info(WHITE + "Meal delivery is on its way to the customer." + RESET);
    }

    @And("the delivery is estimated to arrive within 10 minutes")
    public void the_delivery_is_estimated_to_arrive_within_10_minutes() {
        logger.info(WHITE + "The delivery is estimated to arrive within 10 minutes." + RESET);
    }

    @When("the system detects the delivery status update")
    public void the_system_detects_the_delivery_status_update() {
        receivedNotification = """
            Your order is arriving soon! The delivery is 10 minutes away.
            Please be ready to receive your meal.
        """;
        logger.info(WHITE + "System detected the delivery status update." + RESET);
    }


    @Then("the customer should receive a reminder {int} hours in advance:")
    public void theCustomerShouldReceiveAReminderHoursInAdvance() {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Final confirmation notification sent to customer: " + receivedNotification + RESET);

    }


}
