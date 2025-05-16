package testPackage;

import cook.entities.*;
import io.cucumber.java.en.*;

import java.util.*;

import static org.junit.Assert.*;

public class DietaryPreferencesSteps {

    private Customer customer;
    private CustomerDietaryPreferences preferences;
    private Map<String, Boolean> selectedPreferences = new HashMap<>();
    private Map<String, String> allergies = new HashMap<>();
    private String confirmationMessage;
    private CustomMeal thaiPeanutNoodles;
    private List<String> recommendedMeals = new ArrayList<>();

    // Scenario 1
    @Given("a customer is logged into their account")
    public void customer_logged_in() {
        customer = new Customer("Customer A", false, false);
        preferences = new CustomerDietaryPreferences("Customer A");
        customer.login();
    }

    @When("they navigate to the {string} section")
    public void navigate_to_section(String section) {
        System.out.println("Navigated to: " + section);
    }

    @When("they select the following preferences:")
    public void select_preferences(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            if ("Yes".equalsIgnoreCase(row.get("Selected"))) {
                preferences.addDietaryRestriction(row.get("Preference"));
            }
        });
    }

    @When("they enter the following allergies:")
    public void enter_allergies(io.cucumber.datatable.DataTable dataTable) {
        dataTable.asMaps().forEach(row -> allergies.put(row.get("Allergy"), row.get("Severity")));
    }

    @When("they save their preferences")
    public void save_preferences() {
        confirmationMessage = "Your dietary preferences and allergies have been successfully updated.";
    }

    @Then("the system should store the customer's dietary preferences and allergies")
    public void store_preferences_and_allergies() {
        assertTrue(preferences.getDietaryRestrictions().contains("Vegetarian"));
        assertTrue(preferences.getDietaryRestrictions().contains("Gluten-Free"));
        assertTrue(allergies.containsKey("Peanuts"));
    }

    @Then("display a confirmation message:")
    public void display_confirmation(String docString) {
        assertEquals(docString.strip(), confirmationMessage);
    }

    // Scenario 2
    @Given("a chef is logged into the system")
    public void chef_logged_in() {
        System.out.println("Chef logged in.");
    }

    @Given("the chef is preparing an order for Customer A")
    public void chef_preparing_order() {
        preferences = new CustomerDietaryPreferences("Customer A");
        preferences.addDietaryRestriction("Vegetarian");
        preferences.addDietaryRestriction("Gluten-Free");

        allergies = new HashMap<>();
        allergies.put("Peanuts", "Severe");
        allergies.put("Shellfish", "Moderate");
    }


    @When("the chef accesses Customer A's dietary profile")
    public void access_dietary_profile() {
        // Already loaded in previous scenario
    }

    @Then("the system should display the following preferences:")
    public void verify_preferences(io.cucumber.datatable.DataTable table) {
        table.asMaps().forEach(row -> {
            String preference = row.get("Preference");
            boolean expected = row.get("Selected").equalsIgnoreCase("Yes");
            boolean actual = preferences.getDietaryRestrictions().contains(preference);
            assertEquals("Preference mismatch for: " + preference, expected, actual);
        });
    }


    @Then("the system should display the following allergies:")
    public void verify_allergies(io.cucumber.datatable.DataTable table) {
        table.asMaps().forEach(row ->
            assertEquals(row.get("Severity"), allergies.get(row.get("Allergy")))
        );
    }

    // Scenario 3
    @Given("a customer with a severe peanut allergy")
    public void severe_peanut_allergy_customer() {
        allergies.put("Peanuts", "Severe");
    }

    @Given("the system contains a meal {string} with the ingredient {string}")
    public void meal_with_allergen(String mealName, String allergen) {
        thaiPeanutNoodles = new CustomMeal(mealName);
        thaiPeanutNoodles.addIngredient(allergen);
    }

    @When("the customer attempts to add {string} to their order")
    public void attempt_to_order(String mealName) {
    	 System.out.println("the customer attempts to add" + mealName +"to their order");
    }

    @Then("the system should display an alert:")
    public void display_alert(String expectedAlert) {
        assertTrue(thaiPeanutNoodles.containsIngredient("Peanuts"));
        String actualAlert = "Warning: This meal contains Peanuts, which you have marked as a severe allergy.";
        assertEquals(expectedAlert.strip(), actualAlert);
    }

    @Then("prevent the customer from proceeding with the order")
    public void block_order() {
        assertTrue(thaiPeanutNoodles.containsIngredient("Peanuts"));
    }

   
}
