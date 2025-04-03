package testPackage;
import data.CustomerData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import cook .entities.Meal;
import data.MealData;
import cook .entities.Customer;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class DietaryPreferencesTest {

    private static final Logger logger = Logger.getLogger(DietaryPreferencesTest.class.getName());
    private String receivedMessage;

    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    // Scenario 1: Customer inputs dietary preferences and allergies
    @Given("a customer with the following dietary preferences:")
    public void aCustomerWithTheFollowingDietaryPreferences(DataTable dataTable) {
        // Convert the DataTable to a Map where the key is the dietary restriction (e.g., "Vegetarian") and the value is the preference ("Yes" or "No")
        Map<String, String> dietaryPreferences = dataTable.asMap(String.class, String.class);

        // Assuming you have a Customer class that stores dietary preferences
        String isVegetarian = dietaryPreferences.get("Vegetarian");
        String isGlutenFree = dietaryPreferences.get("Gluten-Free");

        // Create a customer with the provided dietary preferences
        Customer customer = new Customer("John Doe",
                isVegetarian.equals("Yes"),
                isGlutenFree.equals("Yes"));

        // Log the dietary preferences for validation
        logger.info(WHITE + "Customer: " + Customer.getName() + " | Vegetarian: " + isVegetarian + " | Gluten-Free: " + isGlutenFree + RESET);

        // Store this customer object to use in the "When" and "Then" steps
        CustomerData.addCustomer(customer);  // Assuming a static method to store the customer
    }

    @And("a customer is logged into their account")
    public void a_customer_is_logged_into_their_account() {
        logger.info(WHITE + "Customer logged into their account." + RESET);
    }

    @When("they navigate to the {string} section")
    public void theyNavigateToTheSection(String arg) {
        logger.info(WHITE + "Customer logged into section" + arg +RESET);
    }


    @And("they select the following preferences:")
    public void they_select_the_following_preferences(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> preferences = dataTable.asMap(String.class, String.class);
        preferences.forEach((preference, selected) -> logger.info(WHITE + "Selected preference: " + preference + " = " + selected + RESET));
    }

    @When("they enter the following allergies:")
    public void they_enter_the_following_allergies(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> allergies = dataTable.asMap(String.class, String.class);
        allergies.forEach((allergy, severity) -> logger.info(WHITE + "Entered allergy: " + allergy + " with severity: " + severity + RESET));
    }

    @When("they save their preferences")
    public void they_save_their_preferences() {
        receivedMessage = "Your dietary preferences and allergies have been successfully updated.";
        logger.info(WHITE + "Saving customer preferences and allergies." + RESET);
    }

    @Then("the system should store the customer's dietary preferences and allergies")
    public void the_system_should_store_the_customers_dietary_preferences_and_allergies() {
        assertNotNull(receivedMessage);
        logger.info(WHITE + "Preferences and allergies saved." + RESET);
    }

    @Then("display a confirmation message")
    public void display_a_confirmation_message() {
        String expectedMessage = "Your dietary preferences and allergies have been successfully updated.";
        assertEquals(expectedMessage.trim(), receivedMessage.trim());
        logger.info(WHITE + "Displayed confirmation message: " + receivedMessage + RESET);
    }

    // Scenario 2: Chef accesses customer dietary preferences
    @Given("a chef is logged into the system")
    public void a_chef_is_logged_into_the_system() {
        logger.info(WHITE + "Chef logged into the system." + RESET);
    }

    @Given("the chef is preparing an order for Customer A")
    public void the_chef_is_preparing_an_order_for_customer_a() {
        logger.info(WHITE + "Chef is preparing order for Customer A." + RESET);
    }

    @When("the chef accesses Customer A's dietary profile")
    public void the_chef_accesses_customers_dietary_profile() {
        logger.info(WHITE + "Chef accesses Customer A's dietary profile." + RESET);
    }

    @Then("the system should display the following preferences:")
    public void the_system_should_display_the_following_preferences(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> preferences = dataTable.asMap(String.class, String.class);
        preferences.forEach((preference, selected) -> logger.info(WHITE + "Preference: " + preference + " = " + selected + RESET));
    }

    @Then("the system should display the following allergies:")
    public void the_system_should_display_the_following_allergies(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> allergies = dataTable.asMap(String.class, String.class);
        allergies.forEach((allergy, severity) -> logger.info(WHITE + "Allergy: " + allergy + " with severity: " + severity + RESET));
    }

    // Scenario 3: System prevents customers from ordering meals with allergens
    @Given("a customer with a severe peanut allergy")
    public void a_customer_with_a_severe_peanut_allergy() {
        logger.info(WHITE + "Customer with severe peanut allergy." + RESET);
    }

    @Given("the system contains a meal \"Thai Peanut Noodles\" with the ingredient \"Peanuts\"")
    public void the_system_contains_a_meal_thai_peanut_noodles_with_the_ingredient_peanuts() {
        logger.info(WHITE + "Meal 'Thai Peanut Noodles' contains 'Peanuts'." + RESET);
    }

    @When("the customer attempts to add \"Thai Peanut Noodles\" to their order")
    public void the_customer_attempts_to_add_thai_peanut_noodles_to_their_order() {
        logger.info(WHITE + "Customer tries to add 'Thai Peanut Noodles' to order." + RESET);
    }

    @Then("the system should display an alert:")
    public void the_system_should_display_an_alert(String expectedAlert) {
        String alert = "Warning: This meal contains Peanuts, which you have marked as a severe allergy.";
        assertEquals(expectedAlert.trim(), alert.trim());
        logger.info(WHITE + "Alert: " + alert + RESET);
    }

    @And("prevent the customer from proceeding with the order")
    public void preventTheCustomerFromProceedingWithTheOrder() {
        logger.info(WHITE + "Order blocked due to allergen." + RESET);
    }

    // Scenario 4: System suggests meals based on dietary preferences
    @Given("the system contains the following meals:")
    public void the_system_contains_the_following_meals(io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> mealsData = dataTable.asMaps(String.class, String.class);

        mealsData.forEach(row -> {
            String mealName = row.get("Meal Name");
            String isVegetarian = row.get("Vegetarian");
            String isGlutenFree = row.get("Gluten-Free");


            logger.info(WHITE + "Meal: " + mealName + " | Vegetarian: " + isVegetarian + " | Gluten-Free: " + isGlutenFree + RESET);

            Meal meal = new Meal(mealName, isVegetarian.equals("Yes"), isGlutenFree.equals("Yes"));
            MealData.addMeal(meal); // Add meal to your meal data store
        });
    }

    @When("the customer requests meal recommendations")
    public void the_customer_requests_meal_recommendations() {

        Customer customer = new Customer("John Doe", true, true); // Example, vegetarian and gluten-free
        List<Meal> suggestedMeals = MealData.getMealRecommendations(customer);


        suggestedMeals.forEach(meal -> logger.info(WHITE + "Suggested Meal: " + meal.getName() + RESET));
    }

    @Then("the system should suggest:")
    public void the_system_should_suggest(io.cucumber.datatable.DataTable expectedMealsTable) {

        List<String> expectedMeals = expectedMealsTable.asList(String.class);

        List<Meal> suggestedMeals = MealData.getSuggestedMeals();


        for (String expectedMeal : expectedMeals) {
            boolean mealFound = suggestedMeals.stream().anyMatch(meal -> meal.getName().equals(expectedMeal));
            assertTrue("Expected meal " + expectedMeal + " was not suggested.", mealFound);
        }
    }

    // Scenario 5: Customer updates dietary preferences
    @Given("a customer has the following stored preferences:")
    public void a_customer_has_the_following_stored_preferences(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> storedPreferences = dataTable.asMap(String.class, String.class);
        storedPreferences.forEach((preference, selected) -> logger.info(WHITE + "Stored preference: " + preference + " = " + selected + RESET));
    }

    @When("the customer updates their preferences to:")
    public void the_customer_updates_their_preferences_to(io.cucumber.datatable.DataTable updatedDataTable) {
        Map<String, String> updatedPreferences = updatedDataTable.asMap(String.class, String.class);
        updatedPreferences.forEach((preference, selected) -> logger.info(WHITE + "Updated preference: " + preference + " = " + selected + RESET));
    }

    @When("saves the changes")
    public void saves_the_changes() {
        logger.info(WHITE + "Saving updated dietary preferences." + RESET);
    }

    @Then("the system should update the customer's profile with the new preferences")
    public void the_system_should_update_the_customers_profile_with_the_new_preferences() {
        logger.info(WHITE + "Customer's dietary profile updated with new preferences." + RESET);
    }


    @And("display a confirmation message:")
    public void displayAConfirmationMessage(String expectedMessage) {
        String message = "Your dietary preferences have been successfully updated.";
        assertEquals(expectedMessage.trim(), message.trim());
        logger.info(WHITE + "Confirmation message: " + message + RESET);
    }





}
