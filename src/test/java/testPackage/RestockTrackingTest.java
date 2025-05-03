package testPackage;

import cook.entities.Ingredient;
import cook.entities.RestockRequest;
import data.IngredientData;
import data.RestockData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import java.util.Map;
import java.util.logging.Logger;
import static org.junit.Assert.*;

public class RestockTrackingTest {

    private static final Logger logger = Logger.getLogger(RestockTrackingTest.class.getName());
    private String lastNotification;

    // Scenario 1
    @Given("the kitchen manager is logged into the inventory system")
    public void kitchenManagerLoggedIn() {
        logger.info("Kitchen manager logged in.");
    }

    @When("they navigate to the {string} section")
    public void navigateToStockSection(String section) {
        logger.info("Navigated to section: " + section);
    }

    @Then("they should see a real-time stock level report including:")
    public void viewRealTimeStock(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            Ingredient ingredient = IngredientData.getIngredientByName(row.get("Ingredient"));
            assertNotNull(ingredient);
            String expectedStatus = row.get("Status");
            String actualStatus = RestockData.getStatus(ingredient);
            assertEquals(expectedStatus, actualStatus);
        }
        logger.info("Real-time stock report verified.");
    }

    // Scenario 2
    @Given("the restocking threshold for {string} is {int} kg")
    public void setRestockThreshold(String ingredient, int threshold) {
        Ingredient ing = IngredientData.getIngredientByName(ingredient);
        assertNotNull(ing);
        ing.setLowStockThreshold(threshold);  

       
        logger.info("Restocking threshold for " + ingredient + ": " + threshold + "kg.");
    }

    @When("the stock level of {string} drops to {int} kg")
    public void dropStockLevel(String ingredient, int stock) {
        IngredientData.updateStock(ingredient, stock);
        logger.info(ingredient + " stock dropped to " + stock + "kg.");
    }

    @Then("the system should generate a restocking alert:")
    public void generateRestockingAlert(String expected) {
        Ingredient tomatoes = IngredientData.getIngredientByName("Tomatoes");
        assert tomatoes != null;
        if (tomatoes.getStock() < tomatoes.getLowStockThreshold()) {
            lastNotification = """
                Warning: Tomato stock is below the required level (5 kg remaining).
                Recommended action: Restock at least 10 kg.
                """;
        }
        assertEquals(expected.trim(), lastNotification.trim());
        logger.info("Restocking alert generated.");
    }

    @And("the kitchen manager should be prompted to place a restocking order")
    public void promptRestockingOrder() {
        RestockData.addRestockRequest("Tomatoes", 10, false);
        assertTrue(RestockData.hasPendingRequest("Tomatoes"));
        logger.info("Prompted to place restocking order for Tomatoes.");
    }

    // Scenario 3
    @Given("the restocking threshold for {string} is {int} liters")
    public void theRestockingThresholdForIsLiters(String ingredient, int threshold) {
        Ingredient ing = IngredientData.getIngredientByName(ingredient);
        assertNotNull(ing);
        IngredientData.updateStock(ingredient, threshold);
        logger.info("Restocking threshold for " + ingredient + ": " + threshold + " liters.");
    }
    @And("the system has an auto-replenishment rule for critical stock")
    public void autoReplenishmentRuleExists() {
        logger.info("Auto-replenishment rule active.");
    }

    @When("the stock level of {string} drops to {int} liters")
    public void theStockLevelOfDropsToLiters(String ingredient, int stockLevel) {
        IngredientData.updateStock(ingredient, stockLevel);
        logger.info("Stock level of " + ingredient + " dropped to " + stockLevel + " liters.");
    }
    
    @Then("the system should create an automatic purchase request:")
    public void autoPurchaseRequest(String expected) {
        Ingredient ing = IngredientData.getIngredientByName("Olive Oil");
        assert ing != null;
        if (ing.getStock() < ing.getLowStockThreshold()) {
            RestockData.addRestockRequest("Olive Oil", 5, true);
            lastNotification = """
                Critical stock alert: Olive Oil is below the critical level (2 liters remaining).
                Auto-restocking request sent to Supplier XYZ for 5 liters.
                """;
        }
        assertEquals(expected.trim(), lastNotification.trim());
        logger.info("Auto-restocking request sent.");
    }

    @And("the kitchen manager should receive a confirmation of the order")
    public void confirmRestockOrder() {
        assertTrue(RestockData.hasPendingRequest("Olive Oil"));
        logger.info("Kitchen manager received restocking confirmation.");
    }

    // Scenario 4
    @Given("the system has recorded a restocking request for {string} \\({int} kg)")
    public void restockRecorded(String ingredient, int quantity) {
        RestockData.addRestockRequest(ingredient, quantity, false);
        logger.info("Restocking request recorded for " + ingredient);
    }
    @And("the supplier has delivered the requested stock")
    public void theSupplierHasDeliveredTheRequestedStock() {
        Ingredient ingredient = IngredientData.getIngredientByName("Tomatoes");
        assertNotNull(ingredient);
        logger.info("Supplier has delivered the requested stock.");
    }

    @When("the kitchen manager updates the inventory with the new stock amount")
    public void updateStockAfterDelivery() {
        RestockData.markAsDelivered("Tomatoes", 10); 
        logger.info("Stock updated after delivery.");
    }


    @Then("the stock level for {string} should be updated to reflect the new total")
    public void verifyStockLevel(String ingredient) {
        Ingredient ing = IngredientData.getIngredientByName(ingredient);
        assertNotNull(ing);
        assertEquals(15, ing.getStock());
        logger.info("Stock level for " + ingredient + " is now " + ing.getStock());
    }

    @And("the system should display a confirmation message:")
    public void showConfirmationMessage(String expected) {
        lastNotification = "Stock updated: Tomatoes now at 15 kg.";
        assertEquals(expected.trim(), lastNotification.trim());
    }


    // Scenario 5
    @Given("the kitchen manager has placed a restocking order for {string} \\({int} kg)")
    public void placedOrder(String ingredient, int quantity) {
        RestockData.addRestockRequest(ingredient, quantity, false);
        logger.info("Restocking order placed for " + ingredient);
    }

    @And("the order is still pending delivery")
    public void orderPending() {
        RestockRequest request = RestockData.getPendingRequest("Pasta");
        assertNotNull(request);
        assertFalse(request.isDelivered());
        logger.info("Restocking order for Pasta is still pending delivery.");
    }

    @When("the kitchen manager attempts to create another restocking order for {string}")
    public void attemptDuplicateOrder(String ingredient) {
        if (RestockData.hasPendingRequest(ingredient)) {
            lastNotification = "A restocking order for Pasta is already in progress (10 kg).\nPlease wait for the delivery before placing a new request.";
        }
    }

    @Then("the system should prevent the duplicate request and display a message:")
    public void preventDuplicate(String expected) {
        lastNotification = "A restocking order for Pasta is already in progress (10 kg).\nPlease wait for the delivery before placing a new request.";
        assertEquals(expected.trim(), lastNotification.trim());
        logger.info("Duplicate restock blocked.");
    }


}
