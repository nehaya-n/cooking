package testPackage;
import cook.entities.InventorySystem;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;



public class LowStockNotificationTest {

    private static final Logger logger = Logger.getLogger(LowStockNotificationTest.class.getName());
    private InventorySystem inventorySystem;
    private String receivedNotification;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";


    //Senario 1:-
    @Given("the system monitors ingredient stock levels in real time")
    public void the_system_monitors_ingredient_stock_levels_in_real_time() {
        inventorySystem = new InventorySystem();

        logger.info(WHITE + "System is monitoring stock levels in real time." + RESET);
    }

    @Given("the stock level for {string} drops below the low-stock threshold ({int} kg remaining)")
    public void the_stock_level_drops_below_threshold(String ingredient, int remainingStock) {
        inventorySystem.updateStock(ingredient, remainingStock);
        logger.info(WHITE + ingredient + " stock dropped below the threshold: " + remainingStock + " kg remaining." + RESET);
    }

    @When("the system detects the low-stock status")
    public void the_system_detects_low_stock_status() {
        receivedNotification = inventorySystem.checkLowStockAlerts();
        logger.info(WHITE + "Low stock status detected." + RESET);
    }

    @Then("the kitchen manager should receive a notification:")
    public void the_kitchen_manager_receives_notification(String expectedNotification) {
        assertEquals(expectedNotification.trim(), receivedNotification.trim());
        logger.info(WHITE + "Received notification: " + receivedNotification + RESET);
    }
   //Senario 2:-
    @Given("the stock level for {string} reaches {int} liters")
    public void the_stock_level_reaches_zero(String ingredient, int stockLevel) {
        inventorySystem.updateStock(ingredient, stockLevel);
        logger.info(WHITE + ingredient + " stock reached 0 liters." + RESET);
    }

    @Then("the kitchen manager should receive a critical alert:")
    public void the_kitchen_manager_receives_critical_alert(String expectedAlert) {
        assertEquals(expectedAlert.trim(), inventorySystem.checkCriticalStockAlerts().trim());
        logger.info(WHITE + "Received critical alert: " + receivedNotification + RESET);
    }
    //Senario3:-
    @Given("the following ingredients are below the low-stock threshold:")
    public void multiple_ingredients_below_threshold(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> ingredients = dataTable.asMap(String.class, String.class);
        ingredients.forEach((ingredient, stock) -> inventorySystem.updateStock(ingredient, Integer.parseInt(stock.split(" ")[0])));
        logger.info(WHITE + "Multiple ingredients detected below threshold: " + ingredients + RESET);
    }

    @Then("the kitchen manager should receive a restock recommendation:")
    public void the_kitchen_manager_receives_restock_recommendation(String expectedRecommendation) {
        assertEquals(expectedRecommendation.trim(), inventorySystem.checkRestockRecommendation().trim());
        logger.info(WHITE + "Received restock recommendation." + RESET);
    }
    //Senario 4:-
    @Given("the system has sent a low-stock alert for {string}")
    public void the_system_has_sent_low_stock_alert(String ingredient) {
        inventorySystem.sendLowStockAlert(ingredient);
        logger.info(WHITE + "Low-stock alert sent for: " + ingredient + RESET);
    }

    @When("the kitchen manager views the alert")
    public void the_kitchen_manager_views_the_alert() {
        logger.info(WHITE + "Kitchen manager viewed the alert." + RESET);
    }

    @When("selects \"Acknowledge\"")
    public void selects_acknowledge() {
        inventorySystem.acknowledgeAlert();
        logger.info(WHITE + "Kitchen manager acknowledged the alert." + RESET);
    }

    @Then("the system should mark the alert as reviewed")
    public void the_system_marks_alert_as_reviewed() {
        assertTrue(inventorySystem.isAlertReviewed());
        logger.info(WHITE + "Alert marked as reviewed." + RESET);
    }
    //Senario 5:-
    @Given("a low-stock alert for {string} was sent {int} hours ago")
    public void a_low_stock_alert_was_sent_hours_ago(String ingredient, int hoursAgo) {
        inventorySystem.logUnacknowledgedAlert(ingredient, hoursAgo);
        logger.info(WHITE + "Low-stock alert for " + ingredient + " was sent " + hoursAgo + " hours ago." + RESET);
    }

    @Then("the kitchen manager should receive an escalated notification:")
    public void the_kitchen_manager_receives_escalated_notification(String expectedEscalation) {
        assertEquals(expectedEscalation.trim(), inventorySystem.checkEscalatedAlerts().trim());
        logger.info(WHITE + "Received escalated notification." + RESET);
    }


}

