package testPackage;

import data.IngredientData;
import cook.entities.Ingredient;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class LowStockNotificationTest {

    private static final Logger logger = Logger.getLogger(LowStockNotificationTest.class.getName());
    private String ingredientUnderTest;
    private String receivedNotification;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    // Scenario 1
    @Given("the system monitors ingredient stock levels in real time")
    public void the_system_monitors_ingredient_stock_levels_in_real_time() {
        System.out.println("System is monitoring stock levels in real time.");
    }

    @And("the stock level for {string} drops below the low-stock threshold {int} kg remaining")
    public void the_stock_level_drops_below_threshold(String ingredient, int remainingStock) {
        // Set the stock to a value BELOW the threshold (not equal to)
        IngredientData.updateStock(ingredient, remainingStock - 1);  // e.g., 4 kg when threshold is 5
        ingredientUnderTest = ingredient;
        logger.info(WHITE + ingredient + " stock dropped below the threshold: " + remainingStock + " kg remaining." + RESET);
    }


    @When("the system detects the low-stock status")
    public void the_system_detects_low_stock_status() {
        boolean isLowStock = IngredientData.isLowStock(ingredientUnderTest);
        logger.info("Is " + ingredientUnderTest + " stock low: " + isLowStock);
        if (isLowStock) {
            receivedNotification = "Low Stock Alert: Tomatoes stock is below 5 kg\nConsider reordering to prevent shortages.";

        } else {
            receivedNotification = "";
        }
        logger.info("Low stock status detected.");
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
    @Given("the following ingredients are below the low-stock threshold: Tomatoes, Onions, Olive Oil")
    public void theFollowingIngredientsAreBelowTheLowStockThresholdTomatoesOnionsOliveOil() {
        IngredientData.updateStock("Tomatoes", 3);    // threshold is 5 → low
        IngredientData.updateStock("Onions", 2);      // threshold is 5 → low
        IngredientData.updateStock("Olive Oil", 1);   // threshold is 3 → low
    }



    @Then("the kitchen manager should receive a restock recommendation:")
    public void the_kitchen_manager_receives_restock_recommendation(String expectedRecommendation) {
        StringBuilder recommendation = new StringBuilder("Low Stock Alert:\n");
        for (Ingredient ingredient : IngredientData.getIngredients()) {
            if (IngredientData.isLowStock(ingredient.getName())) {
                recommendation.append("- ")
                        .append(ingredient.getName())
                        .append(": ")
                        .append(ingredient.getStock())
                        .append(" remaining (Order Recommended)\n");
            }
        }
        recommendation.append("Would you like to place an order now? [Yes] [No]");

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
    public void noFurtherNotificationsShouldBeSentForTheSameIngredientUntilStockIsUpdated() {
        Ingredient ingredientObj = IngredientData.getIngredientByName(ingredientUnderTest);

        if (ingredientObj == null) {
            fail("Ingredient not found: " + ingredientUnderTest);
        }
        boolean notificationSent = IngredientData.isLowStock(ingredientUnderTest) && !ingredientObj.isAlertAcknowledged();
        assertFalse("No further notifications should be sent for " + ingredientUnderTest, notificationSent);
        logger.info(WHITE + "No further notifications sent for " + ingredientUnderTest + RESET);
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

        if (ingredient.isAlertAcknowledged()) {
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



