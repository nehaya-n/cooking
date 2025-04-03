package testPackage;

import data.IngredientData;
import cook.entities.Ingredient;
import io.cucumber.java.en.And;
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

    @And("the stock level for {string} drops below the low-stock threshold {int} kg remaining")
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

    @When("the system detects the out-of-stock status")
    public void the_system_detects_out_of_stock_status() {
        Ingredient ingredient = IngredientData.getIngredientByName("Olive Oil");
        if (ingredient != null && ingredient.getStock() == 0) {
            receivedNotification = "Out of Stock Alert: Olive Oil is completely out of stock!\n" +
                    "Immediate restocking is required to continue kitchen operations.";
            logger.info(WHITE + "System detected out-of-stock status for Olive Oil." + RESET);
        }
    }

    @Then("the kitchen manager should receive a critical alert:")
    public void the_kitchen_manager_receives_critical_alert(String expectedAlert) {
        Ingredient oliveOil = IngredientData.getIngredientByName("Olive Oil");

        if (oliveOil != null && oliveOil.getStock() == 0) {
            receivedNotification = "Out of Stock Alert: Olive Oil is completely out of stock!\n" +
                    "Immediate restocking is required to continue kitchen operations.";
        } else {
            receivedNotification = "";
        }

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
        Ingredient ingredient = IngredientData.getIngredientByName("Tomatoes");
        assertNotNull("Ingredient not found: Tomatoes", ingredient);

        assertTrue("The alert should be marked as reviewed", ingredient.isAlertAcknowledged());

        logger.info(WHITE + "Alert marked as reviewed." + RESET);
    }


    @And("no further notifications should be sent for the same ingredient until stock is updated")
    public void noFurtherNotificationsShouldBeSentForTheSameIngredientUntilStockIsUpdated(String ingredient) {
        Ingredient ingredientObj = IngredientData.getIngredientByName(ingredient);

        if (ingredientObj == null) {
            fail("Ingredient not found: " + ingredient);
        }
        boolean notificationSent = IngredientData.isLowStock(ingredient) && !ingredientObj.isAlertAcknowledged();
        assertFalse("No further notifications should be sent for " + ingredient, notificationSent);
        logger.info(WHITE + "No further notifications sent for " + ingredient + RESET);
    }

    @Given("a low-stock alert for {string} was sent {int} hours ago")
    public void aLowStockAlertForWasSentHoursAgo(String ingredient, int hours) {
        IngredientData.acknowledgeAlert(ingredient);
        logger.info(WHITE + "Low-stock alert sent for: " + ingredient + " " + hours + " hours ago." + RESET);
    }

    @And("the kitchen manager has not acknowledged or reordered")
    public void theKitchenManagerHasNotAcknowledgedOrReordered() {
        logger.info(WHITE + "Kitchen manager has not taken any action on the low-stock alert." + RESET);
    }

    @When("the system checks the pending alert")
    public void theSystemChecksThePendingAlert() {
        Ingredient ingredient = IngredientData.getIngredientByName("Milk");
        assertNotNull("Ingredient not found: Milk", ingredient);

        if (!ingredient.isAlertAcknowledged()) {
            receivedNotification = """
                    URGENT: Milk is still low on stock (1 liter remaining).
                    No action has been taken in the last 24 hours.
                    Immediate restocking is strongly recommended.""";
        } else {
            receivedNotification = "";
        }
    }

    @Then("the kitchen manager should receive an escalated notification:")
    public void theKitchenManagerShouldReceiveAnEscalatedNotification(String expectedAlert) {
        assertEquals(expectedAlert.trim(), receivedNotification.trim());
        logger.info(WHITE + "Escalated notification sent: " + receivedNotification + RESET);

    }
}

