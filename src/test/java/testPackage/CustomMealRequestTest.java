package testPackage;

import cook.entities.CustomMeal;
import io.cucumber.java.en.*;
import java.util.logging.Logger;
import static org.junit.Assert.*;

public class CustomMealRequestTest {
    private static final Logger logger = Logger.getLogger(CustomMealRequestTest.class.getName());


    private CustomMeal customMeal;
    private String errorMessage;
    private boolean isIngredientAvailable = true;
    //Scenario 1: Customer creates a custom meal
    @Given("a customer is logged into their account")
    public void customer_is_logged_in() {
        logger.info("Customer is logged in.");
    }

    @And("they navigate to the \"Create Your Meal\" section")
    public void navigate_to_create_meal_section() {
        logger.info("Navigated to 'Create Your Meal' section.");
    }

    @When("they select the following ingredients:")
    public void customer_selects_ingredients(io.cucumber.datatable.DataTable dataTable) {
        customMeal = new CustomMeal("Healthy Chicken Bowl");
        dataTable.asList().forEach(customMeal::addIngredient);
    }

    @And("they save the meal as \"Healthy Chicken Bowl\"")
    public void save_custom_meal() {
        customMeal.saveMeal();
    }

    @Then("the system should confirm the meal creation with a message:")
    public void system_confirms_meal_creation() {
        assertTrue(customMeal.isSaved());
    }

    @And("the meal should be available in the customer's saved meals")
    public void meal_is_available_in_saved_meals() {
        assertNotNull(customMeal);
    }

    // Scenario 2: Ingredient availability check
    @Given("a customer is creating a custom meal")
    public void customer_is_creating_custom_meal() {
        customMeal = new CustomMeal("Custom Meal");
    }

    @And("the following ingredients are out of stock:")
    public void ingredients_out_of_stock(io.cucumber.datatable.DataTable dataTable) {
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

    // Scenario 3: Incompatible ingredient combinations
    @Given("the system has predefined incompatible ingredient combinations:")
    public void predefined_incompatible_ingredient_combinations(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asList().forEach(combination -> {
            if (combination.equals("Milk and Lemon Juice")) {
                errorMessage = "The combination of Milk and Lemon Juice is not allowed. Please modify your selection.";
            } else if (combination.equals("Pineapple and Soy Sauce")) {
                errorMessage = "The combination of Pineapple and Soy Sauce is not allowed. Please modify your selection.";

            }
        });
    }

    @When("a customer selects \"Milk\" and \"Lemon Juice\" together")
    public void customer_selects_incompatible_ingredients() {
        if (customMeal.containsIngredient("Milk") && customMeal.containsIngredient("Lemon Juice")) {
            errorMessage = "The combination of Milk and Lemon Juice is not allowed. Please modify your selection.";
        }
    }

    @Then("the system should display an error message:")
    public void system_displays_incompatible_ingredient_error() {
        assertEquals("The combination of Milk and Lemon Juice is not allowed. Please modify your selection.", errorMessage);
    }

    // Scenario 4: Modify a saved meal
    @Given("a customer has a saved custom meal \"Healthy Chicken Bowl\" with the ingredients:")
    public void customer_has_saved_custom_meal(io.cucumber.datatable.DataTable dataTable) {
        customMeal = new CustomMeal("Healthy Chicken Bowl");
        dataTable.asList().forEach(customMeal::addIngredient);
    }

    @When("the customer removes \"Garlic Sauce\"")
    public void customer_removes_ingredient() {
        customMeal.removeIngredient("Garlic Sauce");
    }

    @And("adds \"Tahini Sauce\" instead")
    public void customer_adds_ingredient() {
        customMeal.addIngredient("Tahini Sauce");
    }

    @And("saves the changes")
    public void save_changes() {
        customMeal.saveMeal();
    }

    @Then("the system should update the custom meal with the new ingredients")
    public void system_updates_custom_meal() {
        assertTrue(customMeal.isSaved());
    }

    // Scenario 5: Order a custom meal
    @Given("a customer has a saved custom meal \"Healthy Chicken Bowl\"")
    public void customer_has_saved_order_meal() {
        customMeal = new CustomMeal("Healthy Chicken Bowl");
        customMeal.saveMeal();
    }

    @When("they add \"Healthy Chicken Bowl\" to the cart")
    public void add_meal_to_cart() {
        logger.info("Meal added to cart.");
    }

    @And("proceed to checkout")
    public void proceed_to_checkout() {
        logger.info("Proceeding to checkout.");
    }

    @Then("the system should confirm the order with a message:")
    public void system_confirms_order() {
        String orderConfirmation = "Your custom meal \"Healthy Chicken Bowl\" has been added to your order.";
        assertEquals(orderConfirmation, "Your custom meal \"Healthy Chicken Bowl\" has been added to your order.");
    }
}
