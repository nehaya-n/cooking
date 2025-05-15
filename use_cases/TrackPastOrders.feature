Feature: Track Past Orders and Personalized Meal Plans
  As a customer
  I want to view my past meal orders
  So that I can reorder meals I liked

  As a chef
  I want to access customers’ order history
  So that I can suggest personalized meal plans

  As a system administrator
  I want to store and retrieve customer order history
  So that I can analyze trends and improve service offerings

  # Scenario 1: Customer views past meal orders
  Scenario: Customer accesses order history
    Given a customer is logged into their account      
    And they have previously ordered the following meals:
      | Order ID | Meal Name              | Order Date  | Status    |
      | 101      | Grilled Salmon         | 2025-02-15  | Delivered |
      | 102      | Vegan Pesto Pasta      | 2025-02-18  | Delivered |
      | 103      | Chicken Caesar Salad   | 2025-02-20  | Canceled  |
    When the customer navigates to the "Order History" page
    Then the system should display a list of their past meal orders
    And each order should include:
      | Order ID | Meal Name              | Order Date  | Status    |
      | 101      | Grilled Salmon         | 2025-02-15  | Delivered |
      | 102      | Vegan Pesto Pasta      | 2025-02-18  | Delivered |
      | 103      | Chicken Caesar Salad   | 2025-02-20  | Canceled  |

  # Scenario 2: Customer reorders a past meal
  Scenario: Customer reorders a previously liked meal
    Given a customer has the following past meal orders:
      | Order ID | Meal Name         | Order Date  | Status    |
      | 201      | Quinoa Bowl       | 2025-02-10  | Delivered |
    When the customer selects "Reorder" for "Quinoa Bowl"
    Then the system should add "Quinoa Bowl" to the shopping cart
   

  # Scenario 3: Chef accesses customer order history for meal plan suggestions
  Scenario: Chef retrieves a customer’s past orders for meal personalization
    Given a chef is logged into their account
    And they are preparing a meal plan for Customer A
    When the chef accesses Customer A’s order history
    Then the system should display the following past meals:
      | Meal Name           | Times Ordered |
      | Vegan Pesto Pasta   | 3            |
      | Quinoa Bowl        | 2            |
      | Grilled Salmon     | 1            |
    

  # Scenario 4: System administrator retrieves customer order history for analysis
  Scenario: System administrator reviews order trends
    Given a system administrator is logged into the dashboard
    When they retrieve the order history for the past 3 months
    Then the system should generate a report showing:
      | Meal Name              | Orders in Last 3 Months |
      | Vegan Pesto Pasta      | 150                     |
      | Quinoa Bowl            | 120                     |
      | Grilled Salmon         | 100                     |

  # Scenario 5: System notifies a customer about their favorite meals
  Scenario: System recommends reordering favorite meals
    Given a customer has ordered "Vegan Pesto Pasta" 5 times
    When the customer logs into their account
    Then the system should display a suggestion:
      """
      You have ordered Vegan Pesto Pasta multiple times. Would you like to reorder?
      """