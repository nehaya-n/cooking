package testPackage;

import cook.entities.Customer;
import cook.entities.Chef;
import cook.entities.Admin;
import cook.entities.OrderSystem; 
import cook.entities.Order;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import static org.junit.Assert.*;

public class MealOrderSteps {

    private Customer customer;
    private Chef chef;
    private Admin admin; 
    private OrderSystem system;

    // Scenario 1: Customer accesses order history
 

    @Given("they have previously ordered the following meals:")
    public void customerHasPastOrders(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 1);
            String orderDate = data.cell(i, 2);
            String status = data.cell(i, 3);
            Order order = new Order(mealName, orderDate, status);
            customer.addPastOrders(order);
        }
    }

    @When("the customer navigates to the \"Order History\" page")
    public void customerNavigatesToOrderHistory() {
        system = new OrderSystem(); // تعديل من System إلى OrderSystem
        system.navigateTo("Order History");
    }

    @Then("the system should display a list of their past meal orders")
    public void systemDisplaysOrderHistory() {
        assertTrue(system.getPastOrders().containsAll(customer.getPastOrders()));
    }

    @Then("each order should include:")
    public void checkOrderDetails(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 1);
            String orderDate = data.cell(i, 2);
            String status = data.cell(i, 3);
            Order expectedOrder = new Order(mealName, orderDate, status);
            assertTrue(customer.getPastOrders().contains(expectedOrder));
        }
    }

    // Scenario 2: Customer reorders a previously liked meal
    @When("the customer selects \"Reorder\" for {string}")
    public void customerReordersMeal(String mealName) {
        system.addMealToCart(mealName);
    }

    @Then("the system should add {string} to the shopping cart")
    public void mealAddedToCart(String mealName) {
        assertTrue(system.cartContains(mealName));
    }

   /* @Then("display a confirmation message:")
    public void displayConfirmationMessage(String message) {
        assertEquals(message, system.getConfirmationMessage());
    }*/

    // Scenario 3: Chef accesses customer order history for meal plan suggestions
    @Given("a chef is logged into their account")
    public void chefIsLoggedIn() {
        chef = new Chef("Chef A", 5);
        chef.login();
    }

    @When("the chef accesses Customer A’s order history")
    public void chefAccessesCustomerOrderHistory() {
        chef.accessOrderHistory(customer);
    }

    @Then("the system should display the following past meals:")
    public void systemDisplaysCustomerMeals(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 0);
            assertTrue(system.getPastMealsForCustomer(customer).contains(mealName));
        }
    }

    @Then("the system should suggest:")
    public void systemSuggestsMeals(String suggestion) {
        assertTrue(system.getMealSuggestions().contains(suggestion));
    }

    // Scenario 4: System administrator retrieves customer order history for analysis
    @Given("a system administrator is logged into the dashboard")
    public void adminIsLoggedIn() {
        admin = new Admin("Admin", "admin123");
        admin.login();
    }

    @When("they retrieve the order history for the past 3 months")
    public void adminRetrievesOrderHistory() {
        admin.retrieveOrderHistory("last 3 months");
    }

    @Then("the system should generate a report showing:")
    public void systemGeneratesReport(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 0);
            int orders = Integer.parseInt(data.cell(i, 1));
            assertEquals(orders, system.getOrderHistoryReport(mealName));
        }
    }

    @Then("display insights such as:")
    public void systemDisplaysInsights(String insights) {
        assertTrue(system.getInsights().contains(insights));
    }

    // Scenario 5: System recommends reordering favorite meals
    @Given("a customer has ordered {string} {int} times")
    public void customerHasOrderedFavoriteMeal(String mealName, int timesOrdered) {
        customer.setFavoriteMeal(mealName, timesOrdered);
    }

    @When("the customer logs into their account")
    public void customerLogsIn() {
        customer.login();
    }
 
    @Then("the system should display a suggestion:")
    public void systemDisplaysSuggestion(String suggestion) {
        assertTrue(system.getRecommendation().contains(suggestion));
    }
}
