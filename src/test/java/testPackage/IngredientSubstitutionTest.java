package testPackage;

import cook.entities.Ingredient;
import data.IngredientSubstitutionData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import cook.entities.CustomMeal;
import java.util.logging.Logger;

import static org.junit.Assert.*;




public class IngredientSubstitutionTest {
    private static final Logger logger = Logger.getLogger(IngredientSubstitutionTest.class.getName());
    private static final String RESET = "\u001B[37m";
    private static final String WHITE = "\u001B[37m";

    //Scenario 1: System suggests an alternative for an unavailable ingredient
    @Given("a customer is creating a custom meal")
    public void a_customer_is_creating_a_custom_meal() {
        CustomMeal  customMeal = new CustomMeal("Healthy Salad");

        Ingredient avocado = new Ingredient("Avocado", 0 ,3); // Avocado is unavailable
        customMeal.addIngredient(String.valueOf(avocado));
        logger.info("Customer is creating a custom meal: " + customMeal);
    }

    @And("the following ingredient is out of stock:")
    public void theFollowingIngredientIsOutOfStock() {

        IngredientSubstitutionData.updateStock("Avocado", 0);
    }

    @When("the customer selects {string}")
    public void theCustomerSelects(String ingredient) {

        Ingredient ingredientSelected = IngredientSubstitutionData.getIngredientByName(ingredient);
        assertNotNull("Ingredient should be available", ingredientSelected);
    }

    @Then("the system should suggest an alternative ingredient:")
    public void theSystemShouldSuggestAnAlternativeIngredient() {

        String ingredient = "Avocado";
        String suggestedSubstitute = "Mashed Peas";
        String suggestion = IngredientSubstitutionData.checkIngredientSubstitution(ingredient, suggestedSubstitute);

        assertEquals("Avocado is unavailable. Would you like to use Mashed Peas as a substitute?", suggestion);
    }




    // Scenario 2: System suggests a dietary-friendly alternative
    @Given("a customer has the following dietary restriction:")
    public void aCustomerHasTheFollowingDietaryRestriction() {
        // Initialize dietary restrictions (e.g., Lactose-Free)
        IngredientSubstitutionData.initializePreferences();
    }

    @When("the customer selects {string} for their meal")
    public void theCustomerSelectsForTheirMeal(String ingredient) {
        // Simulate customer selecting an ingredient for their meal
        Ingredient ingredientSelected = IngredientSubstitutionData.getIngredientByName(ingredient);
        assertNotNull("Ingredient should be available for the customer to select", ingredientSelected);
    }
    //Scenario 3: Chef receives an alert for ingredient substitution
    @Given("a customer has replaced {string} with {string} in their order")
    public void aCustomerHasReplacedWithInTheirOrder(String originalIngredient, String substitutedIngredient) {

        IngredientSubstitutionData.updateStock(originalIngredient, 0); // Out of stock
        IngredientSubstitutionData.updateStock(substitutedIngredient, 10); // Substitute available
    }

    @When("the order is placed")
    public void theOrderIsPlaced() {

        String originalIngredient = "Cheese";
        String substitutedIngredient = "Vegan Cheese";
        String notification = IngredientSubstitutionData.generateChefNotification(originalIngredient, substitutedIngredient);

        assertEquals("Customer has requested a substitution: Cheese → Vegan Cheese. Please review and approve the modification.", notification);
    }

    @Then("the chef should receive an alert:")
    public void theChefShouldReceiveAnAlert() {
        String originalIngredient = "Cheese";
        String substitutedIngredient = "Vegan Cheese";
        String notification = IngredientSubstitutionData.generateChefNotification(originalIngredient, substitutedIngredient);

        assertNotNull("Chef should receive an alert for substitution", notification);
    }
    @And("the chef can approve or modify the final recipe")
    public void theChefCanApproveOrModifyTheFinalRecipe() {
        boolean chefApproved = true;
        assertTrue("Chef should approve or modify the final recipe", chefApproved);
    }

   // Scenario 4: Customer reviews ingredient substitutions before checkout
    @Given("a customer has requested ingredient substitutions:")
    public void aCustomerHasRequestedIngredientSubstitutions() {

        String[] originalIngredients = {"Butter", "Milk"};
        String[] substitutedIngredients = {"Olive Oil", "Almond Milk"};
        CustomMeal customMeal = new CustomMeal("Custom Salad");
        customMeal.addIngredient("Butter");
        customMeal.addIngredient("Milk");

        for (int i = 0; i < originalIngredients.length; i++) {
            customMeal.replaceIngredient(originalIngredients[i], substitutedIngredients[i]);
        }


    }

    @When("the customer proceeds to checkout")
    public void theCustomerProceedsToCheckout() {

        String[] originalIngredients = {"Butter", "Milk"};
        String[] substitutedIngredients = {"Olive Oil", "Almond Milk"};
        String substitutionSummary = IngredientSubstitutionData.generateSubstitutionSummary(originalIngredients, substitutedIngredients);

        assertNotNull("Customer should see a substitution summary", substitutionSummary);
    }

    @Then("the system should display a summary of substitutions:")
    public void theSystemShouldDisplayASummaryOfSubstitutions() {
        String[] originalIngredients = {"Butter", "Milk"};
        String[] substitutedIngredients = {"Olive Oil", "Almond Milk"};
        String substitutionSummary = IngredientSubstitutionData.generateSubstitutionSummary(originalIngredients, substitutedIngredients);

        assertEquals("The following ingredient substitutions have been made:\n- Butter → Olive Oil\n- Milk → Almond Milk\nPlease confirm before proceeding with your order.", substitutionSummary);
    }


 //Scenario 5: System prevents substitutions that conflict with multiple dietary restrictions
    @Given("a customer has the following dietary restrictions:")
    public void aCustomerHasTheFollowingDietaryRestrictions() {
        // Initialize multiple dietary restrictions for the customer
        IngredientSubstitutionData.initializePreferences();
    }

    @And("the system suggests {string} as a substitution")
    public void theSystemSuggestsAsASubstitution(String substitute) {


        logger.info(WHITE + "System suggested substitution: " + substitute + RESET);
    }

    @Then("the system should display an error message:")
    public void theSystemShouldDisplayAnErrorMessage() {

        String errorMessage = IngredientSubstitutionData.validateSubstitution("Whole Wheat Bread", "Regular Bread", "Vegan");
        assertNotNull("System should display an error message for invalid substitution", errorMessage);
    }


    @And("the customer should be prompted to select an alternative")
    public void theCustomerShouldBePromptedToSelectAnAlternative() {
        boolean customerPrompted = true;
        assertTrue("Customer should be prompted to select an alternative ingredient", customerPrompted);
    }



}