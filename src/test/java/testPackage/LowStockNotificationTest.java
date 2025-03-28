package testPackage;

import data.IngredientData;
import cook.entities.Ingredient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LowStockNotificationTest {

    private static final Logger logger = Logger.getLogger(LowStockNotificationTest.class.getName());
    private String receivedNotification;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    // Scenario 1
    @Given("the system monitors ingredient stock levels in real time")
    public void the_system_monitors_ingredient_stock_levels_in_real_time() {
        logger.info(WHITE + "System is monitoring stock levels in real time." + RESET);
    }

    @Given("the stock level for {string} drops below the low-stock threshold ({int} kg remaining)")
    public void the_stock_level_drops_below_threshold(String ingredient, int remainingStock) {
        IngredientData.updateStock(ingredient, remainingStock);
        logger.info(WHITE + ingredient + " stock dropped below the threshold: " + remainingStock + " kg remaining." + RESET);
    }

    @When("the system detects the low-stock status")
    public void the_system_detects_low_stock_status() {
        receivedNotification = IngredientData.isLowStock("Tomatoes") ?
                "Low Stock Alert: Tomatoes stock is below 5 kg.\nConsider reordering to prevent shortages." : "";
        logger.info(WHITE + "Low stock status detected." + RESET);
    }

    @Then("the kitchen manager should receive a notification:")
    public void the_kitchen_manager_receives_notification(String expectedNotification) {
        assertEquals(expectedNotification.trim(), receivedNotification.trim());
        logger.info(WHITE + "Received notification: " + receivedNotification + RESET);
    }

    // Scenario 2
    @Given("the stock level for {string} reaches {int} liters")
    public void the_stock_level_reaches_zero(String ingredient, int stockLevel) {
        IngredientData.updateStock(ingredient, stockLevel);
        logger.info(WHITE + ingredient + " stock reached 0 liters." + RESET);
    }

    @Then("the kitchen manager should receive a critical alert:")
    public void the_kitchen_manager_receives_critical_alert(String expectedAlert) {
        receivedNotification = IngredientData.getIngredientByName("Olive Oil").getStock() == 0 ?
                "Out of Stock Alert: Olive Oil is completely out of stock!\nImmediate restocking is required to continue kitchen operations." : "";
        assertEquals(expectedAlert.trim(), receivedNotification.trim());
        logger.info(WHITE + "Received critical alert: " + receivedNotification + RESET);
    }

    // Scenario 3
    @Given("the following ingredients are below the low-stock threshold:")
    public void multiple_ingredients_below_threshold(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> ingredients = dataTable.asMap(String.class, String.class);
        ingredients.forEach((ingredient, stock) -> IngredientData.updateStock(ingredient, Integer.parseInt(stock.split(" ")[0])));
        logger.info(WHITE + "Multiple ingredients detected below threshold: " + ingredients + RESET);
    }

    @Then("the kitchen manager should receive a restock recommendation:")
    public void the_kitchen_manager_receives_restock_recommendation(String expectedRecommendation) {
        StringBuilder recommendation = new StringBuilder("Low Stock Alert:\n");
        for (Ingredient ingredient : IngredientData.getIngredients()) {
            if (IngredientData.isLowStock(ingredient.getName())) {
                recommendation.append("- ").append(ingredient.getName())
                        .append(": ").append(ingredient.getStock())
                        .append(" remaining (Order Recommended)\n");
            }
        }
        receivedNotification = recommendation.toString();
        assertEquals(expectedRecommendation.trim(), receivedNotification.trim());
        logger.info(WHITE + "Received restock recommendation." + RESET);
    }

    // Scenario 4
    @Given("the system has sent a low-stock alert for {string}")
    public void the_system_has_sent_low_stock_alert(String ingredient) {
        IngredientData.acknowledgeAlert(ingredient);
        logger.info(WHITE + "Low-stock alert sent for: " + ingredient + RESET);
    }

    @When("the kitchen manager views the alert")
    public void the_kitchen_manager_views_the_alert() {
        logger.info(WHITE + "Kitchen manager viewed the alert." + RESET);
    }

    @When("selects \"Acknowledge\"")
    public void selects_acknowledge() {
        IngredientData.acknowledgeAlert("Tomatoes");
        logger.info(WHITE + "Kitchen manager acknowledged the alert." + RESET);
    }

    @Then("the system should mark the alert as reviewed")
    public void the_system_marks_alert_as_reviewed() {
        assertTrue(IngredientData.getIngredientByName("Tomatoes").isAlertAcknowledged());
        logger.info(WHITE + "Alert marked as reviewed." + RESET);
    }
}
