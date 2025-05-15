package testPackage;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;

public class DietaryPreferencesSteps {

    @Given("a customer is logged into their account")
    public void customerIsLoggedIn() {
        System.out.println("Customer is logged in");
    }

    @When("they navigate to the {string} section")
    public void navigateToSection(String section) {
        System.out.println("Navigated to: " + section);
    }

    @When("they select the following preferences:")
    public void selectDietaryPreferences(DataTable preferences) {
        System.out.println(preferences);
    }

    @When("they enter the following allergies:")
    public void enterAllergies(DataTable allergies) {
        System.out.println(allergies);
    }

    @When("they save their preferences")
    public void savePreferences() {
        System.out.println("Preferences saved");
    }

    @Then("the system should store the customer's dietary preferences and allergies")
    public void storePreferences() {
        System.out.println("Preferences and allergies stored");
    }

    @Then("display a confirmation message:")
    public void displayConfirmationMessage(String docString) {
        System.out.println("Message: " + docString);
    }

    @Given("a chef is logged into the system")
    public void chefLoggedIn() {
        System.out.println("Chef is logged in");
    }

    @Given("the chef is preparing an order for Customer A")
    public void preparingOrderForCustomerA() {
        System.out.println("Preparing order for Customer A");
    }

    @When("the chef accesses Customer A's dietary profile")
    public void accessCustomerProfile() {
        System.out.println("Accessed customer profile");
    }

    @Then("the system should display the following preferences:")
    public void displayPreferences(DataTable preferences) {
        System.out.println(preferences);
    }

    @Then("the system should display the following allergies:")
    public void displayAllergies(DataTable allergies) {
        System.out.println(allergies);
    }

    @Given("a customer with a severe peanut allergy")
    public void severePeanutAllergy() {
        System.out.println("Customer has severe peanut allergy");
    }

    @Given("the system contains a meal {string} with the ingredient {string}")
    public void mealContainsAllergen(String meal, String allergen) {
        System.out.println("Meal: " + meal + ", Allergen: " + allergen);
    }

    @When("the customer attempts to add {string} to their order")
    public void attemptToOrder(String meal) {
        System.out.println("Customer tries to order: " + meal);
    }

    @Then("the system should display an alert:")
    public void displayAllergyAlert(String docString) {
        System.out.println("Alert: " + docString);
    }

    @Then("prevent the customer from proceeding with the order")
    public void preventOrdering() {
        System.out.println("Order blocked due to allergy");
    }

    @Given("a customer with the following dietary preferences:")
    public void customerPreferences(DataTable preferences) {
        System.out.println(preferences);
    }

    @Given("the system contains the following meals:")
    public void systemMeals(DataTable meals) {
        System.out.println(meals);
    }

    @When("the customer requests meal recommendations")
    public void requestRecommendations() {
        System.out.println("Customer requested recommendations");
    }

    @Then("the system should suggest:")
    public void suggestMeals(DataTable suggestions) {
        System.out.println("Suggestions: " + suggestions);
    }

    @Given("a customer has the following stored preferences:")
    public void storedPreferences(DataTable preferences) {
        System.out.println("Stored prefs: " + preferences);
    }

    @When("the customer updates their preferences to:")
    public void updatePreferences(DataTable preferences) {
        System.out.println("Updated prefs: " + preferences);
    }

    @When("saves the changes")
    public void savesTheChanges() {
        System.out.println("Preferences changes saved");
    }

    @Then("the system should update the customer's profile with the new preferences")
    public void updateProfile() {
        System.out.println("Profile updated");
    }
    @Given("the kitchen manager is logged into the inventory system")
    public void theKitchenManagerIsLoggedIntoTheInventorySystem() {
        System.out.println("Kitchen manager logged into inventory system");
    }

    @When("they request real-time pricing for the following ingredients:")
    public void theyRequestRealTimePricing(DataTable ingredients) {
        System.out.println("Ingredients for pricing: " + ingredients);
    }

    @Then("the system should retrieve and display the latest prices from suppliers:")
    public void displayLatestPrices(DataTable supplierPrices) {
        System.out.println("Prices from suppliers: " + supplierPrices);
    }
}
   

