Feature: Allow Customers to Create Custom Meal Requests
  As a customer
  I want to select ingredients and customize my meal
  So that I can order meals according to my taste and dietary needs

  As a system
  I want to validate ingredient combinations
  So that customers do not select incompatible or unavailable ingredients

  # Scenario 1: Customer creates a custom meal
  Scenario: Customer selects ingredients to create a custom meal
    Given a customer is logged into their account
    And they navigate to the "Create Your Meal" section
    When they select the following ingredients:
      | Ingredient      |
      | Chicken Breast  |
      | Quinoa         |
      | Broccoli       |
      | Garlic Sauce   |
    And they save the meal as "Healthy Chicken Bowl"
    Then the system should confirm the meal creation with a message:
      """
      Your custom meal "Healthy Chicken Bowl" has been created successfully!
      """
    And the meal should be available in the customer's saved meals

  # Scenario 2: System validates ingredient availability
  Scenario: Customer selects an unavailable ingredient
    Given a customer is creating a custom meal
    And the following ingredients are out of stock:
      | Ingredient  |
      | Avocado     |
    When the customer selects "Avocado" as an ingredient
    Then the system should display a custom meal error message:
      """
      Sorry, Avocado is currently unavailable. Please choose a different ingredient.
      """

  # Scenario 3: System prevents incompatible ingredient combinations
  Scenario: Customer selects incompatible ingredients
    Given the system has predefined incompatible ingredient combinations:
      | Ingredient 1   | Ingredient 2  |
      | Pineapple      | Soy Sauce     |
      | Milk          | Lemon Juice   |
    When a customer selects "Milk" and "Lemon Juice" together
    Then the system should display a custom meal error message:
      """
      The combination of Milk and Lemon Juice is not allowed. Please modify your selection.
      """

  

  # Scenario 5: Customer orders a custom meal
  Scenario: Customer places an order with a custom meal
    Given a customer has a saved custom meal "Healthy Chicken Bowl"
    When they add "Healthy Chicken Bowl" to the cart
    And proceed to checkout
    Then the system should confirm the order with a message:
      """
      Your custom meal "Healthy Chicken Bowl" has been added to your order.
      """
