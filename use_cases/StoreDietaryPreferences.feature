Feature: Store Dietary Preferences and Allergies
  As a customer
  I want to input my dietary preferences and allergies
  So that the system can recommend appropriate meals and prevent unwanted ingredients

  As a chef
  I want to view customer dietary preferences
  So that I can customize meals accordingly

  # Scenario 1: Customer adds dietary preferences and allergies to their profile
  Scenario: Customer inputs dietary preferences and allergies
    Given a customer is logged into their account
    When they navigate to the "Dietary Preferences" section
    And they select the following preferences:
      | Preference         | Selected |
      | Vegetarian         | Yes      |
      | Gluten-Free        | Yes      |
      | Dairy-Free         | No       |
    And they enter the following allergies:
      | Allergy     | Severity  |
      | Peanuts     | Severe    |
      | Shellfish   | Moderate  |
    And they save their preferences
    Then the system should store the customer's dietary preferences and allergies
    And display a confirmation message:
      """
      Your dietary preferences and allergies have been successfully updated.
      """

  # Scenario 2: Chef views customer dietary preferences when preparing meals
  Scenario: Chef accesses a customer's dietary preferences
    Given a chef is logged into the system
    And the chef is preparing an order for Customer A
    When the chef accesses Customer A's dietary profile
    Then the system should display the following preferences:
      | Preference         | Selected |
      | Vegetarian         | Yes      |
      | Gluten-Free        | Yes      |
      | Dairy-Free         | No       |
    And the system should display the following allergies:
      | Allergy     | Severity  |
      | Peanuts     | Severe    |
      | Shellfish   | Moderate  |

  # Scenario 3: System prevents customers from ordering meals with allergens
  Scenario: Customer tries to order a meal containing an allergen
    Given a customer with a severe peanut allergy
    And the system contains a meal "Thai Peanut Noodles" with the ingredient "Peanuts"
    When the customer attempts to add "Thai Peanut Noodles" to their order
    Then the system should display an alert:
      """
      Warning: This meal contains Peanuts, which you have marked as a severe allergy.
      """
    And prevent the customer from proceeding with the order

  

 
