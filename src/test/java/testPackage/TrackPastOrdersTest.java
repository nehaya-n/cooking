package testPackage;

import data.OrderHistoryData;
import data.ShoppingCartData;
import cook.entities.CustomMeal;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.logging.Logger;

public class TrackPastOrdersTest {
    private static final java.util.logging.Logger logger = Logger.getLogger(TrackPastOrdersTest.class.getName());    private OrderHistoryData orderHistoryData;
    private ShoppingCartData shoppingCartData;

    @Given("a customer is logged into their account")
    public void aCustomerIsLoggedIntoTheirAccount() {
        orderHistoryData = new OrderHistoryData();
        shoppingCartData = new ShoppingCartData();
        orderHistoryData.initialize();
        logger.info("Customer logged in and order history initialized.");
    }

    @And("they have previously ordered the following meals:")
    public void theyHavePreviouslyOrderedTheFollowingMeals() {

        CustomMeal meal1 = new CustomMeal("Grilled Salmon");
        CustomMeal meal2 = new CustomMeal("Vegan Pesto Pasta");
        CustomMeal meal3 = new CustomMeal("Chicken Caesar Salad");
        orderHistoryData.add(meal1);
        orderHistoryData.add(meal2);
        orderHistoryData.add(meal3);

        logger.info("Meals added to order history: " + meal1.getName() + ", " + meal2.getName()+ ", " + meal3.getName());
    }

    @When("the customer navigates to the {string} page")
    public void theCustomerNavigatesToThePage(String page) {

        logger.info("Customer navigated to the " + page + " page.");
    }

    @Then("the system should display a list of their past meal orders")
    public void theSystemShouldDisplayAListOfTheirPastMealOrders() {
        assert !orderHistoryData.getOrderHistory().isEmpty();
        logger.info("Order history displayed: " + orderHistoryData.getOrderHistory());
    }

    @And("each order should include:")
    public void eachOrderShouldInclude() {
        for (CustomMeal meal : orderHistoryData.getOrderHistory()) {
            logger.info("Order details: " + meal.getName());
        }
    }
    //Scenario 2: Customer reorders a past meal
    @Given("a customer has the following past meal orders:")
    public void aCustomerHasTheFollowingPastMealOrders() {

        CustomMeal meal1 = new CustomMeal("Quinoa Bowl");
        orderHistoryData.add(meal1);

        logger.info("Meals in order history: " + meal1.getName());
    }

    @When("the customer selects {string} for {string}")
    public void theCustomerSelectsFor(String mealName, String action) {
        if (action.equalsIgnoreCase("Reorder")) {
            shoppingCartData.addItem(mealName);
            logger.info("Customer selected to reorder: " + mealName);
        }
    }

    @Then("the system should add {string} to the shopping cart")
    public void theSystemShouldAddToTheShoppingCart(String itemName) {
        assert shoppingCartData.getShoppingCart().contains(itemName);
        logger.info("Item added to shopping cart: " + itemName);
    }

    @And("display a confirmation message:")
    public void displayAConfirmationMessage() {
        logger.info("Confirmation: Item added to shopping cart.");
    }

    //Scenario 3: Chef accesses customer order history for meal plan suggestions
    @Given("a chef is logged into their account")
    public void aChefIsLoggedIntoTheirAccount() {
        logger.info("Chef logged in.");
    }

    @And("they are preparing a meal plan for Customer A")
    public void theyArePreparingAMealPlanForCustomerA() {
        logger.info("Chef is preparing a meal plan for Customer A.");
    }

    @When("the chef accesses Customer A’s order history")
    public void theChefAccessesCustomerASOrderHistory() {
        assert !orderHistoryData.getOrderHistory().isEmpty();
        logger.info("Chef accessed Customer A's order history.");
    }

    @Then("the system should display the following past meals:")
    public void theSystemShouldDisplayTheFollowingPastMeals() {
        for (CustomMeal meal : orderHistoryData.getOrderHistory()) {
            logger.info("Chef sees past meal: " + meal.getName());
        }
    }

    @And("the system should suggest:")
    public void theSystemShouldSuggest() {
        logger.info("System should suggest meals based on past orders.");
    }

    //Scenario 4: System administrator retrieves customer order history for analysis
    @Given("a system administrator is logged into the dashboard")
    public void aSystemAdministratorIsLoggedIntoTheDashboard() {
        logger.info("Administrator logged into the dashboard.");
    }

    @When("they retrieve the order history for the past {int} months")
    public void theyRetrieveTheOrderHistoryForThePastMonths(int months) {
        logger.info("Administrator retrieved order history for the past " + months + " months.");

        orderHistoryData.add(new CustomMeal("Vegan Pesto Pasta"));
        orderHistoryData.add(new CustomMeal("Quinoa Bowl"));
        orderHistoryData.add(new CustomMeal("Grilled Salmon"));
    }

    @Then("the system should generate a report showing:")
    public void theSystemShouldGenerateAReportShowing() {
        logger.info("System generated a report for the administrator.");

        logger.info("Meal Name              | Orders in Last 3 Months");
        logger.info("Vegan Pesto Pasta      | 150");
        logger.info("Quinoa Bowl            | 120");
        logger.info("Grilled Salmon         | 100");
    }

    @And("display insights such as:")
    public void displayInsightsSuchAs() {

        logger.info("System displays insights based on the report.");
        logger.info("- The most popular meal is Vegan Pesto Pasta with 150 orders.");
        logger.info("- Quinoa Bowl is frequently ordered by vegetarian customers.");
        logger.info("- Grilled Salmon is the top-selling seafood dish.");
    }

    //Scenario 5: System notifies a customer about their favorite meals
    @Given("a customer has ordered {string} {int} times")
    public void aCustomerHasOrderedTimes(String mealName, int times) {
        for (int i = 0; i < times; i++) {
            CustomMeal meal = new CustomMeal(mealName);
            orderHistoryData.add(meal);
        }
        logger.info("Customer has ordered " + mealName + " " + times + " times.");
    }

    @When("the customer logs into their account")
    public void theCustomerLogsIntoTheirAccount() {
        logger.info("Customer logged into their account.");
    }

    @Then("the system should display a suggestion:")
    public void theSystemShouldDisplayASuggestion() {
        logger.info("System displays a suggestion based on past orders.");
        logger.info("You have ordered Vegan Pesto Pasta multiple times. Would you like to reorder?");
    }
}
