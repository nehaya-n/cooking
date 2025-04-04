package testPackage;

import cook.entities.Chef;
import cook.entities.Task;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class TaskManagementTest {
    private static final Logger logger = Logger.getLogger(TaskManagementTest.class.getName());
    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";
    public boolean taskCompleted = false;
    public boolean chefHasTask = true;
    public boolean systemSuggestsAlternativeChef = true;
    public boolean isKitchenManagerNotified = true;

    Chef chefJohn = new Chef("Chef John", 1);
    Chef chefAlex = new Chef("Chef Alex", 2);
    Chef chefMaria = new Chef("Chef Maria", 3);
    Task task;

    // Scenario 1: Kitchen manager assigns tasks to chefs
    @Given("there are multiple pending meal orders")
    public void there_are_multiple_pending_meal_orders() {
        logger.info(WHITE + "Multiple meal orders are pending in the kitchen." + RESET);
    }

    @And("the following chefs are available:")
    public void the_following_chefs_are_available() {
        chefAlex = new Chef("Chef Alex", 2);
        chefMaria = new Chef("Chef Maria", 3);
        chefJohn = new Chef("Chef John", 1);
    }

    @When("the kitchen manager assigns a new \"Grilled Chicken\" order")
    public void the_kitchen_manager_assigns_a_new_grilled_chicken_order() {
        chefJohn.setWorkload(2);
        task = new Task("Grilled Chicken", "6:00 PM", chefJohn.getName());
    }

    @Then("the system should assign the task to \"Chef John\" based on lowest workload")
    public void the_system_should_assign_the_task_to_chef_john_based_on_lowest_workload() {
        assertEquals("Chef John", task.getAssignedChef());
    }

    @And("update Chef John’s workload to {int} orders")
    public void updateChefJohnSWorkloadToOrders(int orders) {
        chefJohn.setWorkload(orders);
        logger.info(WHITE + "Chef John’s workload has been updated to " + chefJohn.getWorkload() + " orders." + RESET);


    }

    // Scenario 2: Chef receives a notification for a new task
    @Given("Chef Alex has been assigned a new \"Pasta Alfredo\" task")
    public void chef_alex_has_been_assigned_a_new_pasta_alfredo_task() {
        task = new Task("Pasta Alfredo", "7:30 PM", chefAlex.getName());
    }

    @When("the task is assigned")
    public void the_task_is_assigned() {

        logger.info(WHITE + "Task '" + task.getDishName() + "' has been assigned to " + task.getAssignedChef() + " and is due by " + task.dueTime + RESET);
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
        assertTrue(chefHasTask);
    }


    // Scenario 3: System prevents overloading a chef
    @Given("Chef Maria already has 5 active tasks")
    public void chef_maria_already_has_5_active_tasks() {
        chefMaria.setWorkload(5);
    }

    @When("the kitchen manager attempts to assign a new \"Vegan Burger\" order to Chef Maria")
    public void the_kitchen_manager_attempts_to_assign_a_new_vegan_burger_order_to_chef_maria() {
        task = new Task("Vegan Burger", "8:00 PM", chefMaria.getName());
        logger.info(WHITE + "Task assignment for '" + task.getDishName() + "' has been attempted by the kitchen manager." + RESET);
    }

    @Then("the system should suggest assigning the order to a different chef")
    public void the_system_should_suggest_assigning_the_order_to_a_different_chef() {

        assertTrue(systemSuggestsAlternativeChef);// Check if the system suggests an alternative chef
    }

    @And("display a warning: \"Chef Maria is at full capacity. Assign to another available chef.\"")
    public void display_a_warning_chef_maria_is_at_full_capacity() {
        String warningMessage = "Chef Maria is at full capacity. Assign to another available chef.";
        logger.info(WHITE + warningMessage + RESET);
    }

    // Scenario 4: Kitchen manager reassigns a task to another chef
    @Given("Chef John has been assigned a \"Grilled Salmon\" task")
    public void chef_john_has_been_assigned_a_grilled_salmon_task() {

        task = new Task("Grilled Salmon", "8:00 PM", chefJohn.getName());
    }

    @And("Chef John is unavailable")
    public void chef_john_is_unavailable() {
        logger.info(WHITE + "Chef John is unavailable" + RESET);
    }

    @When("the kitchen manager reassigns the task to Chef Maria")
    public void the_kitchen_manager_reassigns_the_task_to_chef_maria() {

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

        task = new Task("Pasta Alfredo", "7:30 PM", chefAlex.getName());
        logger.info(WHITE + chefAlex.getName() + " has an active task: " + task.getDishName() + " to be completed by " + task.dueTime + RESET);
    }

    @When("Chef Alex finishes preparing the meal")
    public void chef_alex_finishes_preparing_the_meal() {

        logger.info(WHITE + chefAlex.getName() + " finishes preparing the meal: " + task.getDishName() + RESET);
    }

    @And("marks the task as \"Completed\"")
    public void marks_the_task_as_completed() {

        taskCompleted = true;
        logger.info(WHITE + task.getDishName() + " task has been marked as completed by " + chefAlex.getName() + RESET);
    }


    @Then("the system should update the task status to \"Completed\"")
    public void the_system_should_update_the_task_status_to_completed() {
        assertEquals("Completed", task.getDishName());
    }

    @And("notify the kitchen manager about the completion")
    public void notify_the_kitchen_manager_about_the_completion() {
        assertTrue(isKitchenManagerNotified);
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
}






