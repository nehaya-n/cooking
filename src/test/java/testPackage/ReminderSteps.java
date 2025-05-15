package testPackage;

import cook.entities.Chef;
import cook.entities.MealOrder;
import cook.entities.Task;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class ReminderSteps {

    private String actualNotification;
    private String expectedNotification;
    private MealOrder activeOrder;
    private Chef chef;

    // --------- Scenario 1 ---------
    @Given("a customer has an active meal order scheduled for delivery")
    public void customerHasActiveOrder() {
        activeOrder = new MealOrder("Order #001", "Test Customer", "12:30 PM");
    }

    @And("the delivery time is within 1 hour")
    public void deliveryTimeIsClose() {
        activeOrder.setDeliveryETA(45); // 45 minutes
    }

    @When("the system detects the upcoming delivery")
    public void systemDetectsDelivery() {
        actualNotification = activeOrder.generateReminderNotification();
    }

    @Then("the customer should receive a notification:")
    public void customerShouldReceiveNotification(String expected) {
        expectedNotification = expected;
        Assert.assertEquals(expectedNotification.trim(), actualNotification.trim());
    }

    // --------- Scenario 2 ---------
    @Given("a chef has a scheduled cooking task for {string} at {string}")
    public void chefHasTask(String dish, String time) {
        chef = new Chef("Chef Ali", "Italian Cuisine");
        Task task = new Task(dish, time);
        chef.addTask(task); // إضافة المهمة للطباخ
    }

    @And("the task is due within 30 minutes")
    public void taskIsSoon() {
        // هنا لا نحتاج لتغييرات إضافية، لأننا نستخدم الوقت المخصص
        chef.getTaskList().forEach(task -> task.markAsCompleted()); // تفعيل المهمة للاختبار
    }

    @When("the system detects the approaching task time")
    public void systemDetectsCookingTask() {
        actualNotification = chef.getUpcomingTaskReminder(); // يجب التأكد من أن الوظيفة تتعامل مع المهام الصحيحة
    }

    @Then("the chef should receive a notification:")
    public void chefShouldReceiveNotification(String expected) {
        expectedNotification = expected;
        Assert.assertEquals(expectedNotification.trim(), actualNotification.trim());
    }

    // --------- Scenario 3 ---------
    @Given("a customer is subscribed to a weekly meal plan")
    public void customerSubscribedToPlan() {
        activeOrder = new MealOrder("Sub-Plan", "Test Customer", "1:00 PM Tomorrow");
        activeOrder.setSubscription(true);
    }

    @And("the next delivery is scheduled for tomorrow at {string}")
    public void deliveryScheduledTomorrow(String time) {
        activeOrder.setDeliveryTime(time);
    }

    @Then("the customer should receive a reminder 24 hours in advance:")
    public void customerGetsAdvanceReminder(String expected) {
        actualNotification = activeOrder.generate24hReminder();
        Assert.assertEquals(expected.trim(), actualNotification.trim());
    }

    // --------- Scenario 4 ---------
    @Given("a chef has multiple scheduled cooking tasks for the day")
    public void chefHasMultipleTasks() {
        chef = new Chef("Chef Ali", "Italian Cuisine");
        chef.addTask(new Task("Grilled Salmon", "10:00 AM"));
        chef.addTask(new Task("Vegan Pasta", "12:30 PM"));
        chef.addTask(new Task("Chicken Stir-Fry", "3:00 PM"));
    }

    @When("the chef logs into the system in the morning")
    public void chefLogsInMorning() {
        System.out.println("Chef logged in.");
    }

    @Then("they should receive a task summary notification:")
    public void chefGetsSummary(String expected) {
        actualNotification = chef.generateDailySummary();
        Assert.assertEquals(expected.trim(), actualNotification.trim());
    }

    // --------- Scenario 5 ---------
    @Given("a meal delivery is on its way to the customer")
    public void deliveryOnWay() {
        activeOrder = new MealOrder("Order #002", "Test Customer", "12:30 PM");
        activeOrder.setStatus("On The Way");
    }

    @And("the delivery is estimated to arrive within 10 minutes")
    public void deliveryClose() {
        activeOrder.setDeliveryETA(10);
    }

    @When("the system detects the delivery status update")
    public void systemDetectsUpdate() {
        actualNotification = activeOrder.generateFinalNotification();
    }

   
}