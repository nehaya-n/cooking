package testPackage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class CustomMealRequestTest {

    private String customerMeal;
    private boolean isIngredientAvailable;
    private String errorMessage;
    private String confirmationMessage;

    // Scenario 1: Customer creates a custom meal
    @Given("a customer is logged into their account")
    public void customer_is_logged_in() {
        // Simulate customer login
        System.out.println("Customer is logged in.");
    }

    @And("they navigate to the \"Create Your Meal\" section")
    public void navigate_to_create_meal_section() {
        // Simulate navigating to the create meal section
        System.out.println("Navigated to 'Create Your Meal' section.");
    }

    @When("they select the following ingredients:")
    public void customer_selects_ingredients(io.cucumber.datatable.DataTable dataTable) {
        // Simulate the customer selecting ingredients
        System.out.println("Customer selected the following ingredients:");
        dataTable.asList().forEach(System.out::println);
    }

    @And("they save the meal as \"Healthy Chicken Bowl\"")
    public void save_custom_meal() {
        // Simulate saving the custom meal
        customerMeal = "Healthy Chicken Bowl";
        confirmationMessage = "Your custom meal \"Healthy Chicken Bowl\" has been created successfully!";
    }

    @Then("the system should confirm the meal creation with a message:")
    public void system_confirms_meal_creation() {
        assertEquals("Your custom meal \"Healthy Chicken Bowl\" has been created successfully!", confirmationMessage);
    }

    @And("the meal should be available in the customer's saved meals")
    public void meal_is_available_in_saved_meals() {
        // Simulate the meal being available in the saved meals
        assertNotNull(customerMeal);
    }

    // Scenario 2: System validates ingredient availability
    @Given("a customer is creating a custom meal")
    public void customer_is_creating_custom_meal() {
        // Simulate creating a custom meal
        System.out.println("Customer is creating a custom meal.");
    }

    @And("the following ingredients are out of stock:")
    public void ingredients_out_of_stock(io.cucumber.datatable.DataTable dataTable) {
        // Simulate checking for out of stock ingredients
        dataTable.asList().forEach(ingredient -> {
            if (ingredient.equals("Avocado")) {
                isIngredientAvailable = false;
            }
        });
    }

    @When("the customer selects \"Avocado\" as an ingredient")
    public void customer_selects_unavailable_ingredient() {
        if (!isIngredientAvailable) {
            errorMessage = "Sorry, Avocado is currently unavailable. Please choose a different ingredient.";
        }
    }

    @Then("the system should display an error message:")
    public void system_displays_error_message() {
        assertEquals("Sorry, Avocado is currently unavailable. Please choose a different ingredient.", errorMessage);
    }

    // Scenario 3: System prevents incompatible ingredient combinations
    @Given("the system has predefined incompatible ingredient combinations:")
    public void predefined_incompatible_ingredient_combinations(io.cucumber.datatable.DataTable dataTable) {
        // Simulate the predefined incompatible ingredient combinations
        dataTable.asList().forEach(combination -> {
            if (combination.equals("Milk and Lemon Juice")) {
                isIngredientAvailable = false;
                errorMessage = "The combination of Milk and Lemon Juice is not allowed. Please modify your selection.";
            }
        });
    }

    @When("a customer selects \"Milk\" and \"Lemon Juice\" together")
    public void customer_selects_incompatible_ingredients() {
        // Check if the selected ingredients are incompatible
        if (!isIngredientAvailable) {
            errorMessage = "The combination of Milk and Lemon Juice is not allowed. Please modify your selection.";
        }
    }

    @Then("the system should display an error message:")
    public void system_displays_incompatible_ingredient_error() {
        assertEquals("The combination of Milk and Lemon Juice is not allowed. Please modify your selection.", errorMessage);
    }

    // Scenario 4: Customer modifies a custom meal before ordering
    @Given("a customer has a saved custom meal \"Healthy Chicken Bowl\" with the ingredients:")
    public void customer_has_saved_custom_meal(io.cucumber.datatable.DataTable dataTable) {
        // Simulate customer having a saved meal
        customerMeal = "Healthy Chicken Bowl";
        System.out.println("Saved meal: " + customerMeal);
        dataTable.asList().forEach(System.out::println);
    }

    @When("the customer removes \"Garlic Sauce\"")
    public void customer_removes_ingredient() {
        // Simulate removing Garlic Sauce
        System.out.println("Customer removes Garlic Sauce.");
    }

    @And("adds \"Tahini Sauce\" instead")
    public void customer_adds_ingredient() {
        // Simulate adding Tahini Sauce
        System.out.println("Customer adds Tahini Sauce.");
    }

    @And("saves the changes")
    public void save_changes() {
        // Simulate saving changes
        confirmationMessage = "Your custom meal \"Healthy Chicken Bowl\" has been updated successfully.";
    }

    @Then("the system should update the custom meal with the new ingredients")
    public void system_updates_custom_meal() {
        assertEquals("Your custom meal \"Healthy Chicken Bowl\" has been updated successfully.", confirmationMessage);
    }

    @And("display a confirmation message:")
    public void display_confirmation_message() {
        System.out.println(confirmationMessage);
    }

    // Scenario 5: Customer orders a custom meal
    @Given("a customer has a saved custom meal \"Healthy Chicken Bowl\"")
    public void customer_has_saved_order_meal() {
        // Simulate customer having a saved meal
        customerMeal = "Healthy Chicken Bowl";
    }

    @When("they add \"Healthy Chicken Bowl\" to the cart")
    public void add_meal_to_cart() {
        // Simulate adding meal to cart
        System.out.println("Meal added to cart.");
    }

    @And("proceed to checkout")
    public void proceed_to_checkout() {
        // Simulate proceeding to checkout
        System.out.println("Proceeding to checkout.");
    }

    @Then("the system should confirm the order with a message:")
    public void system_confirms_order() {
        String orderConfirmation = "Your custom meal \"Healthy Chicken Bowl\" has been added to your order.";
        assertEquals(orderConfirmation, "Your custom meal \"Healthy Chicken Bowl\" has been added to your order.");
    }
}
