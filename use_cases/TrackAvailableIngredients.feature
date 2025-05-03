Feature: Track Available Ingredients and Suggest Restocking
  As a kitchen manager
  I want to track ingredient stock levels in real time
  So that I can prevent shortages and ensure continuous operations

  As a system
  I want to automatically suggest restocking when ingredients are low
  So that kitchen managers can take action promptly

  # Scenario 1: Kitchen manager views real-time ingredient stock levels
  Scenario: View current ingredient stock levels
    Given the kitchen manager is logged into the inventory system
    When they navigate to the "Stock Management" section
    Then they should see a real-time stock level report including:
      | Ingredient      | Current Quantity | Status   |
      | Chicken        | 20 kg            | Sufficient |
      | Olive Oil      | 2 liters         | Critical  |
      | Pasta          | 10 kg            | Sufficient |

  # Scenario 2: System detects low stock and suggests restocking
  Scenario: System identifies low-stock ingredients and recommends restocking
    Given the restocking threshold for "Tomatoes" is 6 kg
    When the stock level of "Tomatoes" drops to 5 kg
    Then the system should generate a restocking alert:
      """
      Warning: Tomato stock is below the required level (5 kg remaining).
      Recommended action: Restock at least 10 kg.
      """
    And the kitchen manager should be prompted to place a restocking order

  # Scenario 3: System detects critical stock levels and auto-generates restocking requests
  Scenario: System automatically generates a restocking request for critically low ingredients
    Given the restocking threshold for "Olive Oil" is 3 liters
    And the system has an auto-replenishment rule for critical stock
    When the stock level of "Olive Oil" drops to 2 liters
    Then the system should create an automatic purchase request:
      """
      Critical stock alert: Olive Oil is below the critical level (2 liters remaining).
      Auto-restocking request sent to Supplier XYZ for 5 liters.
      """
    And the kitchen manager should receive a confirmation of the order

  # Scenario 4: Kitchen manager manually updates stock levels after restocking
  Scenario: Kitchen manager updates stock after receiving an ingredient restock
    Given the system has recorded a restocking request for "Tomatoes" (10 kg)
    And the supplier has delivered the requested stock
    When the kitchen manager updates the inventory with the new stock amount
    Then the stock level for "Tomatoes" should be updated to reflect the new total
    And the system should display a confirmation message:
      """
      Stock updated: Tomatoes now at 15 kg.
      """

  # Scenario 5: System prevents duplicate restocking requests for recently ordered ingredients
  Scenario: System blocks duplicate restocking requests for ongoing orders
    Given the kitchen manager has placed a restocking order for "Pasta" (10 kg)
    And the order is still pending delivery
    When the kitchen manager attempts to create another restocking order for "Pasta"
    Then the system should prevent the duplicate request and display a message:
      """
      A restocking order for Pasta is already in progress (10 kg).
      Please wait for the delivery before placing a new request.
      """

