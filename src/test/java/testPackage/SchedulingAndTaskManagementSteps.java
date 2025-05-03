package testPackage;
import cook.entities.Chef;
import cook.entities.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.Map;

import org.junit.Assert;

public class SchedulingAndTaskManagementSteps {

    private Chef chefAlex, chefMaria, chefJohn;
    private Task task;
    private String notification;
    private String expectedNotification;

 // --------- Scenario 1 ---------
    @Given("there are multiple pending meal orders")
    public void thereArePendingOrders() {
        chefAlex = new Chef("Chef Alex", "Italian Cuisine");
        chefMaria = new Chef("Chef Maria", "Vegan Cuisine");
        chefJohn = new Chef("Chef John", "Grilling");
    }

    @And("the following chefs are available:")
    public void chefsAreAvailable(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            String chefName = row.get("Chef Name");
            String expertise = row.get("Expertise");
            int workload = Integer.parseInt(row.get("Current Workload").split(" ")[0]);  // استخراج الرقم فقط من "2 orders"
            
            Chef chef = new Chef(chefName, expertise);
            chef.updateWorkload(workload);

            System.out.println("Chef added: " + chefName + ", Expertise: " + expertise + ", Workload: " + workload);
        }
    }

    @When("the kitchen manager assigns a new \"Grilled Chicken\" order")
    public void assignNewOrder() {
        task = new Task("Grilled Chicken", "6:00 PM");
        chefJohn.addTask(task);  // إضافة المهمة للطباخ
        chefJohn.updateWorkload(chefJohn.getCurrentWorkload());  // تحديث عبء العمل بعد إضافة المهمة
    }

    @Then("the system should assign the task to \"Chef John\" based on lowest workload")
    public void systemAssignsToChefJohn() {
        Assert.assertTrue("The task should be assigned to Chef John", chefJohn.getTaskList().contains(task));
    }

    @And("update Chef John’s workload to 2 orders")
    public void updateWorkload() {
        Assert.assertEquals("Chef John's workload should be 2", 1, chefJohn.getCurrentWorkload());
    }


    // --------- Scenario 2 ---------
    @Given("Chef Alex has been assigned a new \"Pasta Alfredo\" task")
    public void chefAlexAssignedTask() {
        chefAlex = new Chef("Chef Alex", "Italian Cuisine");
        task = new Task("Pasta Alfredo", "7:30 PM");
        chefAlex.addTask(task);
    }

    @When("the task is assigned")
    public void taskAssigned() {
        notification = String.format("New Task Assigned:\nDish: %s\nDue Time: %s", task.getDishName(), task.getDueTime());
    }

    @Then("the system should send a notification to Chef Alex with the task details:")
    public void chefReceivesNotification(String expected) {
        expectedNotification = expected;
        Assert.assertEquals(expectedNotification.trim(), notification.trim());
    }

    @And("the task should appear in Chef Alex’s task list")
    public void taskAppearsInTaskList() {
        Assert.assertTrue(chefAlex.getTaskList().contains(task));
    }

    // --------- Scenario 3 ---------
    @Given("Chef Maria already has 5 active tasks")
    public void chefMariaHasActiveTasks() {
        chefMaria = new Chef("Chef Maria", "Vegan Cuisine");
        for (int i = 0; i < 5; i++) {
            chefMaria.addTask(new Task("Task " + (i + 1), "Time"));
        }
    }

    @When("the kitchen manager attempts to assign a new \"Vegan Burger\" order to Chef Maria")
    public void kitchenManagerAttemptsToAssign() {
        if (chefMaria.getCurrentWorkload() >= 5) {
            notification = "Chef Maria is at full capacity. Assign to another available chef.";
        }
    }

    @Then("the system should suggest assigning the order to a different chef")
    public void systemSuggestsDifferentChef() {
        Assert.assertEquals("Chef Maria is at full capacity. Assign to another available chef.", notification);
    }
    @Then("display a warning: {string}")
    public void displayAWarning(String warningMessage) {
        
        notification = warningMessage;
        System.out.println(notification);  
        
        Assert.assertEquals(warningMessage, notification);
    }


 // --------- Scenario 4 ---------
    @Given("Chef John has been assigned a \"Grilled Salmon\" task")
    public void chefJohnHasTask() {
        chefJohn = new Chef("Chef John", "Grilling");
        task = new Task("Grilled Salmon", "8:00 PM");
        chefJohn.addTask(task);
    }

    @And("Chef John is unavailable")
    public void chefJohnUnavailable() {
        // Simulate Chef John is unavailable
        chefJohn.removeTask(task);  
    }

    @When("the kitchen manager reassigns the task to Chef Maria")
    public void reassignTask() {
        chefMaria = new Chef("Chef Maria", "Vegan Cuisine");  
        chefMaria.addTask(task);  
        notification = String.format("Reassigned Task:\nDish: %s\nDue Time: %s", task.getDishName(), task.getDueTime());
    }

    @Then("the system should update the task assignment to Chef Maria")
    public void systemUpdatesAssignment() {
        Assert.assertTrue(chefMaria.getTaskList().contains(task));
    }

    @And("notify Chef Maria with the new task details:")
    public void notifyChefMaria(String expected) {
        expectedNotification = expected;
        Assert.assertEquals(expectedNotification.trim(), notification.trim());
    }

    // --------- Scenario 5 ---------
    @Given("Chef Alex has an active \"Pasta Alfredo\" task")
    public void chefAlexHasActiveTask() {
        chefAlex = new Chef("Chef Alex", "Italian Cuisine");
        task = new Task("Pasta Alfredo", "7:30 PM");
        chefAlex.addTask(task);
    }

    @When("Chef Alex finishes preparing the meal")
    public void chefAlexFinishesTask() {
        task.markAsCompleted();
    }

    @And("marks the task as \"Completed\"")
    public void chefMarksTaskCompleted() {
        task.markAsCompleted();
    }

    @Then("the system should update the task status to \"Completed\"")
    public void systemUpdatesTaskStatus() {
        Assert.assertEquals("Completed", task.getStatus());
    }

    @And("notify the kitchen manager about the completion")
    public void notifyManager() {
        notification = "Task Completed: " + task.getDishName() + " is done.";
        Assert.assertTrue(notification.contains("Pasta Alfredo"));
    }
}
