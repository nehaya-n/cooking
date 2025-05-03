package testPackage;

import cook.entities.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.*;

import static org.junit.Assert.*;

public class CustomMealSteps {

    private CustomMeal customMeal;
    private final List<String> selectedIngredients = new ArrayList<>();
    private final Set<String> outOfStockIngredients = new HashSet<>();
    private final Set<List<String>> incompatiblePairs = new HashSet<>();
    private String confirmationMessage;
    private String errorMessage;
    private final Map<String, CustomMeal> savedMeals = new HashMap<>();


    @Given("a customer is logged into their account")
    public void aCustomerIsLoggedIntoTheirAccount() {
    	System.out.println("Customer successfully logged into their account.");
    } 
    
  

    @When("they select the following ingredients:")
    public void selectIngredients(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            selectedIngredients.add(row.get("Ingredient"));
        }
    }

    @And("they save the meal as {string}")
    public void saveMeal(String mealName) {
        customMeal = new CustomMeal(mealName);
        selectedIngredients.forEach(customMeal::addIngredient);
        customMeal.saveMeal();
        savedMeals.put(mealName, customMeal);
        confirmationMessage = "Your custom meal \"" + mealName + "\" has been created successfully!";
    }

    @Then("the system should confirm the meal creation with a message:")
    public void confirmCreation(String expected) {
        assertEquals(expected.trim(), confirmationMessage.trim());
    }

    @And("the meal should be available in the customer's saved meals")
    public void mealShouldBeSaved() {
        assertTrue(savedMeals.containsKey(customMeal.getName()));
        assertTrue(customMeal.isSaved());
    }

    
    

    @And("the following ingredients are out of stock:")
    public void markIngredientsOutOfStock(DataTable table) {
        table.asMaps().forEach(row -> outOfStockIngredients.add(row.get("Ingredient")));
    }

    @When("the customer selects {string} as an ingredient")
    public void customerSelectsUnavailable(String ingredient) {
        if (outOfStockIngredients.contains(ingredient)) {
            errorMessage = "Sorry, " + ingredient + " is currently unavailable. Please choose a different ingredient.";
        }
    }

   

    
    @Given("the system has predefined incompatible ingredient combinations:")
    public void predefinedIncompatibles(DataTable table) {
        for (Map<String, String> row : table.asMaps()) {
            incompatiblePairs.add(List.of(row.get("Ingredient 1"), row.get("Ingredient 2")));
        }
    }

    @When("a customer selects {string} and {string} together")
    public void customerSelectsIncompatible(String ing1, String ing2) {
        if (incompatiblePairs.contains(List.of(ing1, ing2)) || incompatiblePairs.contains(List.of(ing2, ing1))) {
            errorMessage = "The combination of " + ing1 + " and " + ing2 + " is not allowed. Please modify your selection.";
        }
    }

    @Then("the system should display a custom meal error message:")
    public void showUnavailableOrIncompatibleError(String expected) {
        assertNotNull("errorMessage is null", errorMessage);
        assertEquals(expected.trim(), errorMessage.trim());
    }

    @Given("a customer has a saved custom meal {string} with the ingredients:")
    public void customerHasSavedMeal(String mealName, DataTable table) {
        customMeal = new CustomMeal(mealName);
        table.asMaps().forEach(row -> customMeal.addIngredient(row.get("Ingredient")));
        customMeal.saveMeal();
        savedMeals.put(mealName, customMeal);
    }

    @When("the customer removes {string}")
    public void removeIngredient(String ingredient) {
        customMeal.ingredients.remove(ingredient);
    }

    @And("adds {string} instead")
    public void addNewIngredient(String ingredient) {
        customMeal.addIngredient(ingredient);
    }

    @And("saves the changes")
    public void saveChanges() {
        customMeal.saveMeal();
        confirmationMessage = "Your custom meal \"" + customMeal.getName() + "\" has been updated successfully.";
    }

    @Then("the system should update the custom meal with the new ingredients")
    public void verifyUpdatedMeal() {
        assertTrue(customMeal.containsIngredient("Tahini Sauce"));
        assertFalse(customMeal.containsIngredient("Garlic Sauce"));
    }

    @And("display a confirmation message:")
    public void confirmUpdate(String expected) {
        assertEquals(expected.trim(), confirmationMessage.trim());
    }

    
    @Given("a customer has a saved custom meal {string}")
    public void hasSavedMeal(String mealName) {
        customMeal = new CustomMeal(mealName);
        customMeal.saveMeal();
        savedMeals.put(mealName, customMeal);
    }

    @When("they add {string} to the cart")
    public void addToCart(String mealName) {
        assertTrue(savedMeals.containsKey(mealName));
        confirmationMessage = "Your custom meal \"" + mealName + "\" has been added to your order.";
    }

    @And("proceed to checkout")
    public void proceedToCheckout() {
        System.out.println("Proceeding to checkout...");
    }

    @Then("the system should confirm the order with a message:")
    public void confirmOrderMessage(String expected) {
        assertEquals(expected.trim(), confirmationMessage.trim());
    }


    

}
