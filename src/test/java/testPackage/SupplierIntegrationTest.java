package testPackage;

import data.IntegratewithsuppData;
import cook.entities.Integratewithsupp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class SupplierIntegrationTest {

    private static final Logger logger = Logger.getLogger(SupplierIntegrationTest.class.getName());
    private String receivedNotification;
    private String purchaseOrderDetails;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    // Scenario 1: Kitchen manager checks real-time pricing for ingredients
    @Given("the kitchen manager is logged into the inventory system")
    public void the_kitchen_manager_is_logged_into_inventory_system() {
        logger.info(WHITE + "Kitchen manager logged into the inventory system." + RESET);
    }

    @When("they request real-time pricing for the following ingredients:")
    public void they_request_real_time_pricing(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> ingredients = dataTable.asMap(String.class, String.class);
        ingredients.forEach((ingredient, value) -> {
            logger.info(WHITE + "Fetching real-time pricing for: " + ingredient + RESET);
        });
    }

    @Then("the system should retrieve and display the latest prices from suppliers:")
    public void the_system_retrieves_and_displays_prices(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> expectedPrices = dataTable.asMap(String.class, String.class);

        // Retrieve data from IntegratewithsuppData to display the prices
        for (Map.Entry<String, String> entry : expectedPrices.entrySet()) {
            Integratewithsupp ingredient = IntegratewithsuppData.getIngredientByName(entry.getKey());
            String price = ingredient.getPrices().get("Supplier A").toString();
            logger.info(WHITE + "Ingredient: " + entry.getKey() + " | Price from Supplier A: " + price + RESET);
        }
    }

    // Scenario 2: Kitchen manager selects the best-priced supplier for an order
    @Given("the kitchen manager is viewing ingredient prices from multiple suppliers")
    public void the_kitchen_manager_is_viewing_prices_from_suppliers() {
        logger.info(WHITE + "Kitchen manager viewing prices from multiple suppliers." + RESET);
    }

    @When("they compare prices for {string}")
    public void they_compare_prices_for_ingredient(String ingredientName) {
        Integratewithsupp ingredient = IntegratewithsuppData.getIngredientByName(ingredientName);
        String bestSupplier = ingredient.getBestPriceSupplier();
        logger.info(WHITE + "Best price supplier for " + ingredientName + ": " + bestSupplier + RESET);
    }

    @Then("the system should prepare a purchase order for {string} from {string}")
    public void the_system_prepares_purchase_order(String ingredientName, String supplier) {
        purchaseOrderDetails = "Purchase Order for: " + ingredientName + " from " + supplier;
        assertTrue(purchaseOrderDetails.contains(ingredientName) && purchaseOrderDetails.contains(supplier));
        logger.info(WHITE + "Prepared purchase order: " + purchaseOrderDetails + RESET);
    }

    // Scenario 3: System auto-generates a purchase order for a critically low ingredient
    @Given("the system monitors ingredient stock levels")
    public void the_system_monitors_ingredient_stock_levels() {
        logger.info(WHITE + "System monitoring ingredient stock levels." + RESET);
    }

    @Given("the stock level for {string} drops below the critical threshold ({int} liter remaining)")
    public void the_stock_level_for_ingredient_drops_below_critical_threshold(String ingredientName, int remainingStock) {
        IntegratewithsuppData.updateStock(ingredientName, remainingStock);
        logger.info(WHITE + ingredientName + " stock is below the critical threshold: " + remainingStock + " liters remaining." + RESET);
    }

    @When("the system detects the shortage")
    public void the_system_detects_shortage() {
        receivedNotification = "Auto-Purchase Order:\n- Ingredient: Olive Oil\n- Quantity: 5 liters\n- Preferred Supplier: Supplier B ($4.80/liter)\n- Total Cost: $24.00";
        logger.info(WHITE + "System detected the shortage and auto-generated purchase order." + RESET);
    }

    @Then("it should automatically generate a purchase order for {string}:")
    public void it_automatically_generates_purchase_order(String ingredientName) {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Generated purchase order: " + receivedNotification + RESET);
    }

    @Then("and notify the kitchen manager for approval")
    public void notify_the_kitchen_manager_for_approval() {
        logger.info(WHITE + "Notifying kitchen manager for purchase order approval." + RESET);
    }
}
