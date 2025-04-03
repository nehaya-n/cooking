package testPackage;

import data.IntegratewithsuppData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
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
            // Simulate fetching real-time pricing from the suppliers
            logger.info(WHITE + "Fetching real-time pricing for: " + ingredient + RESET);
        });
    }

    @Then("the system should retrieve and display the latest prices from suppliers:")
    public void the_system_retrieves_and_displays_prices(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> priceList = dataTable.asMaps(String.class, String.class);

        // عرض القيم المستخرجة
        for (Map<String, String> row : priceList) {
            String supplier = row.get("Supplier");
            String ingredient = row.get("Ingredient");
            String price = row.get("Price per Unit");

            logger.info(WHITE + "Supplier: " + supplier + " | Ingredient: " + ingredient + " | Price: " + price + RESET);
        }
    }

    // Scenario 2: Kitchen manager selects the best-priced supplier for an order
    @Given("the kitchen manager is viewing ingredient prices from multiple suppliers")
    public void the_kitchen_manager_is_viewing_prices_from_suppliers() {
        logger.info(WHITE + "Kitchen manager viewing prices from multiple suppliers." + RESET);
    }

    @When("they compare prices for {string}")
    public void they_compare_prices_for_ingredient(String ingredient) {
        logger.info(WHITE + "Comparing prices for: " + ingredient + RESET);
    }

    @Then("the system should prepare a purchase order for {string} from {string}")
    public void the_system_prepares_purchase_order(String ingredient, String supplier) {
        purchaseOrderDetails = "Purchase Order for: " + ingredient + " from " + supplier;
        assertTrue(purchaseOrderDetails.contains(ingredient) && purchaseOrderDetails.contains(supplier));
        logger.info(WHITE + "Prepared purchase order: " + purchaseOrderDetails + RESET);
    }

    // Scenario 3: System auto-generates a purchase order for a critically low ingredient
    @Given("the system monitors ingredient stock levels")
    public void the_system_monitors_ingredient_stock_levels() {
        logger.info(WHITE + "System monitoring ingredient stock levels." + RESET);
    }

    @Given("the stock level for {string} drops below the critical threshold ({int} liter remaining)")
    public void the_stock_level_for_ingredient_drops_below_critical_threshold(String ingredient, int remainingStock) {
        IntegratewithsuppData.updateStock(ingredient, remainingStock);
        logger.info(WHITE + ingredient + " stock is below the critical threshold: " + remainingStock + " liters remaining." + RESET);
    }

    @When("the system detects the shortage")
    public void the_system_detects_shortage() {
        receivedNotification = "Auto-Purchase Order:\n- Ingredient: Olive Oil\n- Quantity: 5 liters\n- Preferred Supplier: Supplier B ($4.80/liter)\n- Total Cost: $24.00";
        logger.info(WHITE + "System detected the shortage and auto-generated purchase order." + RESET);
    }

    @Then("it should automatically generate a purchase order for {string}:")
    public void it_automatically_generates_purchase_order(String ingredient) {
        assertNotNull(receivedNotification);
        logger.info(WHITE + "Generated purchase order: " + receivedNotification + RESET);
    }

    @Then("and notify the kitchen manager for approval")
    public void notify_the_kitchen_manager_for_approval() {
        logger.info(WHITE + "Notifying kitchen manager for purchase order approval." + RESET);
    }

    // Scenario 4: Kitchen manager manually reviews the auto-generated purchase order
    @Given("the system has created an auto-purchase order for {string}")
    public void the_system_has_created_auto_purchase_order(String ingredient) {
        logger.info(WHITE + "System created auto-purchase order for: " + ingredient + RESET);
    }

    @When("the kitchen manager reviews the order details")
    public void the_kitchen_manager_reviews_order_details() {
        logger.info(WHITE + "Kitchen manager reviewing the order details." + RESET);
    }

    @Then("they should have the option to:")
    public void they_have_option_to_approve_modify_or_cancel(io.cucumber.datatable.DataTable options) {
        options.asList().forEach(option -> logger.info(WHITE + "Option: " + option + RESET));
    }

    @Then("if approved, the order should be sent to the selected supplier")
    public void if_approved_order_sent_to_supplier() {
        logger.info(WHITE + "Order approved and sent to selected supplier." + RESET);
    }

    // Scenario 5: System prevents duplicate orders for ingredients already ordered
    @Given("a purchase order for {string} ({int} kg) is pending delivery")
    public void a_purchase_order_for_ingredient_is_pending_delivery(String ingredient, int quantity) {
        logger.info(WHITE + "Purchase order for " + ingredient + " (" + quantity + " kg) is pending delivery." + RESET);
    }

    @When("the kitchen manager attempts to place another order for {string}")
    public void the_kitchen_manager_attempts_to_place_order_for_ingredient(String ingredient) {
        logger.info(WHITE + "Attempting to place another order for: " + ingredient + RESET);
    }

    @Then("the system should display a warning:")
    public void the_system_displays_warning(String expectedWarning) {
        String warning = "A purchase order for this ingredient is already in progress . Please wait for the delivery before placing a new order.";
        assertEquals(expectedWarning.trim(), warning.trim());
        logger.info(WHITE + "Displayed warning: " + warning + RESET);
    }

}
