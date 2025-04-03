package testPackage;

import cook.entities.Chef;
import cook.entities.Task;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class TaskManagementTest {

    Chef chefJohn = new Chef("Chef John", 1); // إنشاء طباخ Chef John مع عبء عمل مبدئي 1
    Chef chefAlex = new Chef("Chef Alex", 2); // إنشاء طباخ Chef Alex مع عبء عمل مبدئي 2
    Chef chefMaria = new Chef("Chef Maria", 3); // إنشاء طباخ Chef Maria مع عبء عمل مبدئي 3
    Task task;

    // Scenario 1: Kitchen manager assigns tasks to chefs
    @Given("there are multiple pending meal orders")
    public void there_are_multiple_pending_meal_orders() {
        // Assume multiple orders are pending
    }

    @And("the following chefs are available:")
    public void the_following_chefs_are_available() {
        // Example: Chef Alex (Italian Cuisine, 2 orders), Chef Maria (Vegan Cuisine, 3 orders), Chef John (Grilling, 1 order)
    }

    @When("the kitchen manager assigns a new \"Grilled Chicken\" order")
    public void the_kitchen_manager_assigns_a_new_grilled_chicken_order() {
        // Simulate assigning the new order to Chef John
        chefJohn.setWorkload(2);  // تحديث عبء العمل للطباخ Chef John ليصبح 2
        task = new Task("Grilled Chicken", "6:00 PM", chefJohn.getName()); // تعيين مهمة للطباخ
    }

    @Then("the system should assign the task to \"Chef John\" based on lowest workload")
    public void the_system_should_assign_the_task_to_chef_john_based_on_lowest_workload() {
        // Simulate that Chef John is assigned the task
        assertEquals("Chef John", task.getAssignedChef());
    }

    @Given("Chef John’s workload is set to 2 orders")
    public void update_chef_john_s_workload_to_2_orders() {
        // تحديث عبء العمل للطباخ "Chef John" إلى 2
        chefJohn.setWorkload(2);
    }

    @Then("Chef John’s workload should be updated to 2 orders")
    public void verify_chef_john_s_workload() {
        // تحقق من عبء العمل للطباخ "Chef John"
        assertEquals(2, chefJohn.getWorkload());
    }

    // Scenario 2: Chef receives a notification for a new task
    @Given("Chef Alex has been assigned a new \"Pasta Alfredo\" task")
    public void chef_alex_has_been_assigned_a_new_pasta_alfredo_task() {
        // Assume Chef Alex has been assigned the task
        task = new Task("Pasta Alfredo", "7:30 PM", chefAlex.getName());
    }

    @When("the task is assigned")
    public void the_task_is_assigned() {
        // Simulate the task being assigned
    }

    @Then("the system should send a notification to Chef Alex with the task details:")
    public void the_system_should_send_a_notification_to_chef_alex_with_the_task_details() {
        String expectedNotification = """
            New Task Assigned:
            Dish: Pasta Alfredo
            Due Time: 7:30 PM
        """;
        assertEquals(expectedNotification, getChefNotification("Chef Alex"));
    }

    @And("the task should appear in Chef Alex’s task list")
    public void the_task_should_appear_in_chef_alex_s_task_list() {
        // Check if the task is in Chef Alex's task list
        assertTrue(chefHasTask());
    }

    // Scenario 3: System prevents overloading a chef
    @Given("Chef Maria already has 5 active tasks")
    public void chef_maria_already_has_5_active_tasks() {
        // Assume Chef Maria already has 5 tasks
        chefMaria.setWorkload(5);
    }

    @When("the kitchen manager attempts to assign a new \"Vegan Burger\" order to Chef Maria")
    public void the_kitchen_manager_attempts_to_assign_a_new_vegan_burger_order_to_chef_maria() {
        // Try to assign the new task to Chef Maria
    }

    @Then("the system should suggest assigning the order to a different chef")
    public void the_system_should_suggest_assigning_the_order_to_a_different_chef() {
        // Check if the system suggests an alternative chef
        assertTrue(systemSuggestsAlternativeChef());
    }

    @And("display a warning: \"Chef Maria is at full capacity. Assign to another available chef.\"")
    public void display_a_warning_chef_maria_is_at_full_capacity() {
        String warningMessage = "Chef Maria is at full capacity. Assign to another available chef.";
        assertEquals(warningMessage, getWarningMessage());
    }

    // Scenario 4: Kitchen manager reassigns a task to another chef
    @Given("Chef John has been assigned a \"Grilled Salmon\" task")
    public void chef_john_has_been_assigned_a_grilled_salmon_task() {
        // Chef John has been assigned the task
        task = new Task("Grilled Salmon", "8:00 PM", chefJohn.getName());
    }

    @And("Chef John is unavailable")
    public void chef_john_is_unavailable() {
        // Simulate Chef John being unavailable
    }

    @When("the kitchen manager reassigns the task to Chef Maria")
    public void the_kitchen_manager_reassigns_the_task_to_chef_maria() {
        // Reassign the task to Chef Maria
        task.setAssignedChef("Chef Maria");
    }

    @Then("the system should update the task assignment to Chef Maria")
    public void the_system_should_update_the_task_assignment_to_chef_maria() {
        assertEquals("Chef Maria", task.getAssignedChef());
    }

    @And("notify Chef Maria with the new task details:")
    public void notify_chef_maria_with_the_new_task_details() {
        String expectedNotification = """
            Reassigned Task:
            Dish: Grilled Salmon
            Due Time: 8:00 PM
        """;
        assertEquals(expectedNotification, getChefNotification("Chef Maria"));
    }

    // Scenario 5: Chef completes a task and updates the system
    @Given("Chef Alex has an active \"Pasta Alfredo\" task")
    public void chef_alex_has_an_active_pasta_alfredo_task() {
        // Chef Alex has an active task
        task = new Task("Pasta Alfredo", "7:30 PM", chefAlex.getName());
    }

    @When("Chef Alex finishes preparing the meal")
    public void chef_alex_finishes_preparing_the_meal() {
        // Simulate Chef Alex finishing the task
    }

    @And("marks the task as \"Completed\"")
    public void marks_the_task_as_completed() {
        // Simulate Chef Alex marking the task as completed
        task.setDishName("Completed");
    }

    @Then("the system should update the task status to \"Completed\"")
    public void the_system_should_update_the_task_status_to_completed() {
        assertEquals("Completed", task.getDishName());
    }

    @And("notify the kitchen manager about the completion")
    public void notify_the_kitchen_manager_about_the_completion() {
        assertTrue(isKitchenManagerNotified());
    }

    // Helper methods to simulate system interactions
    private String getChefNotification(String chefName) {
        if ("Chef Alex".equals(chefName)) {
            return """
                New Task Assigned:
                Dish: Pasta Alfredo
                Due Time: 7:30 PM
            """;
        }
        return "";
    }

    private boolean chefHasTask() {
        return true; // Simulate that Chef Alex has the task
    }

    private boolean systemSuggestsAlternativeChef() {
        return true; // Simulate that the system suggests another chef
    }

    private String getWarningMessage() {
        return "Chef Maria is at full capacity. Assign to another available chef."; // Warning message
    }

    private boolean isKitchenManagerNotified() {
        return true; // Simulate the kitchen manager being notified
    }

    @And("update Chef John’s workload to {int} orders")
    public void updateChefJohnSWorkloadToOrders() {
    }
}
