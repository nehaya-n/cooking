Feature: Notify Users of Low-Stock Ingredients
  As a kitchen manager
  I want to receive alerts when stock levels are low
  So that I can reorder before running out of ingredients

  # Scenario 1: Kitchen manager receives an alert when an ingredient reaches the low-stock threshold
  Scenario: Notify kitchen manager of low-stock ingredient
    Given the system monitors ingredient stock levels in real time
    And the stock level for "Tomatoes" drops below the low-stock threshold 5 kg remaining
    When the system detects the low-stock status
    Then the kitchen manager should receive a notification:
      """
      Low Stock Alert: Tomatoes stock is below 5 kg.
      Consider reordering to prevent shortages.
      """

  # Scenario 2: Kitchen manager receives a critical alert for an out-of-stock ingredient
  Scenario: Notify kitchen manager when an ingredient is out of stock
    Given the stock level for "Olive Oil" reaches 0 liters
    When the system detects the out-of-stock status
    Then the kitchen manager should receive a critical alert:
      """
      Out of Stock Alert: Olive Oil is completely out of stock!
      Immediate restocking is required to continue kitchen operations.
      """

  # Scenario 3: System suggests an automatic restock order when multiple ingredients are low
  Scenario: System suggests a restock order for multiple low-stock ingredients
    Given the following ingredients are below the low-stock threshold:
      | Tomatoes   | 3             |
      | Onions     | 2             |
      | Olive Oil  | 1             |
    When the system detects the low-stock status
    Then the kitchen manager should receive a restock recommendation:
      """
      Low Stock Alert:
      - Tomatoes: 3 remaining (Order Recommended)
      - Onions: 2 remaining (Order Recommended)
      - Olive Oil: 1 remaining (Order Recommended)
      """



  # Scenario 4: Kitchen manager acknowledges the low-stock notification
  Scenario: Kitchen manager acknowledges a low-stock alert
    Given the system has sent a low-stock alert for "Tomatoes"
    When the kitchen manager views the alert
    And selects "Acknowledge"
    Then the system should mark the alert as reviewed


  # Scenario 5: System escalates low-stock alerts if no action is taken
  Scenario: Escalate alert if no action is taken on a low-stock ingredient
    Given a low-stock alert for "Tomatoes" was sent 24 hours ago
    And the kitchen manager has not acknowledged or reordered
    When the system checks the pending alert
    Then the kitchen manager should receive an escalated notification:
  """
  URGENT: Tomatoes is still low on stock( 2 kg remaining) No action has been taken in the last 24 hours.
  
  """

