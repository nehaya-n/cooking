Feature: Integrate with Suppliers for Real-Time Pricing and Ordering
  As a kitchen manager
  I want the system to fetch real-time ingredient prices from suppliers
  So that I can make cost-effective purchasing decisions

  As a system
  I want to automatically generate purchase orders when stock levels are critically low
  So that supplies are replenished without manual intervention

  # Scenario 1: Fetch real-time ingredient prices from suppliers
  Scenario: Kitchen manager checks real-time pricing for ingredients
    Given the kitchen manager is logged into the inventory system
    When they request real-time pricing for the following ingredients:
      | Ingredient  |
      | Tomatoes    |
      | Olive Oil   |
      | Chicken     |
    Then the system should retrieve and display the latest prices from suppliers:
      | Supplier   | Ingredient  | Price per Unit |
      | Supplier A | Tomatoes    | $2.50/kg       |
      | Supplier B | Olive Oil   | $5.00/liter    |
      | Supplier C | Chicken     | $8.00/kg       |

  # Scenario 2: Kitchen manager compares supplier prices before purchasing
  Scenario: Kitchen manager selects the best-priced supplier for an order
    Given the kitchen manager is viewing ingredient prices from multiple suppliers
    When they compare prices for "Tomatoes"
      | Supplier   | Price per kg |
      | Supplier A | $2.50        |
      | Supplier B | $2.30        |
    And they choose Supplier B
    Then the system should prepare a purchase order for "Tomatoes" from Supplier B

  # Scenario 3: System automatically generates a purchase order for critically low stock
  Scenario: System auto-generates a purchase order for a critically low ingredient
    Given the system monitors ingredient stock levels
    And the stock level for "Olive Oil" drops below the critical threshold (1 liter remaining)
    When the system detects the shortage
    Then it should automatically generate a purchase order for "Olive Oil":
      """
      Auto-Purchase Order:
      - Ingredient: Olive Oil
      - Quantity: 5 liters
      - Preferred Supplier: Supplier B ($4.80/liter)
      - Total Cost: $24.00
      """
    And notify the kitchen manager for approval

  # Scenario 4: Kitchen manager reviews and approves auto-generated purchase orders
  Scenario: Kitchen manager manually reviews an auto-generated purchase order
    Given the system has created an auto-purchase order for "Chicken"
    When the kitchen manager reviews the order details
    Then they should have the option to:
      | Action       |
      | Approve      |
      | Modify       |
      | Cancel       |
    And if approved, the order should be sent to the selected supplier

  # Scenario 5: System prevents duplicate orders for ingredients already ordered
  Scenario: System prevents duplicate ingredient purchase requests
    Given a purchase order for "Tomatoes" (10 kg) is pending delivery
    When the kitchen manager attempts to place another order for "Tomatoes"
    Then the system should display a warning:
      """
      A purchase order for Tomatoes is already in progress (10 kg).
      Please wait for the delivery before placing a new order.
      """

