Feature: Suggest Ingredient Substitutions Based on Dietary Restrictions
  As a customer
  I want the system to suggest alternative ingredients if an ingredient is unavailable or does not fit my dietary restrictions
  So that I can enjoy my meal without compromising my health

  As a chef
  I want to receive an alert when an ingredient substitution is applied
  So that I can approve or adjust the final recipe

  # Scenario 1: System suggests an alternative for an unavailable ingredient
  Scenario: Customer selects an unavailable ingredient
    Given a customer is creating a custom meal
    And the following ingredient is out of stock: "Avocado"
    When the customer selects "Avocado"
    Then the system should suggest an alternative ingredient:
      """
      Avocado is unavailable. Would you like to use Mashed Peas as a substitute?
      """


  # Scenario 2: System suggests a dietary-friendly alternative
  Scenario: Customer selects an ingredient that conflicts with their dietary restrictions
    Given a customer has the following dietary restriction:
      | Restriction  |
      | Lactose-Free |
    When the customer selects "Cheese" for their meal
    Then the system should suggest an alternative ingredient:
      """
      Cheese is not suitable for a lactose-free diet. Would you like to replace it with Vegan Cheese?
      """


  # Scenario 3: Chef receives an alert for ingredient substitution
  Scenario: Chef is notified when an ingredient substitution is applied
    Given a customer has replaced "Cheese" with "Vegan Cheese" in their order
    When the order is placed
    Then the chef should receive an alert:
      """
      Customer has requested a substitution: Cheese → Vegan Cheese
      Please review and approve the modification
      """
    And the chef can approve or modify the final recipe

  # Scenario 4: Customer reviews ingredient substitutions before checkout
  Scenario: Customer confirms substituted ingredients before placing an order
    Given a customer has requested ingredient substitutions:
      | Original Ingredient | Substituted With  |
      | Butter             | Olive Oil         |
      | Regular Milk       | Almond Milk       |
    When the customer proceeds to checkout
    Then the system should display a summary of substitutions:
      """
      The following ingredient substitutions have been made:
      - Butter → Olive Oil
      - Regular Milk → Almond Milk
      Please confirm before proceeding with your order.
      """


  # Scenario 5: System prevents substitutions that conflict with multiple dietary restrictions
  Scenario: System prevents an invalid substitution
    Given a customer has the following dietary restrictions:
      | Restriction      |
      | Vegan            |
      | Gluten-Free      |
    When the customer selects "Whole Wheat Bread"
    And the system suggests "Regular Bread" as a substitution
    Then the system should display an error message:
      """
      Regular Bread is not suitable for a gluten-free diet. Please choose a different option.
      """
    And the customer should be prompted to select an alternative

