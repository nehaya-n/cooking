Feature: Send Reminders for Upcoming Orders and Deliveries
  As a customer
  I want to receive reminders for my upcoming meal deliveries
  So that I can be prepared to receive them

  As a chef
  I want to get notified of scheduled cooking tasks
  So that I can prepare meals on time

  # Scenario 1: Customer receives a reminder before meal delivery
  Scenario: Send meal delivery reminder to a customer
    Given a customer has an active meal order scheduled for delivery
    And the delivery time is within 1 hour
    When the system detects the upcoming delivery
    Then the customer should receive a notification:
      """
      Reminder: Your meal delivery is scheduled to arrive at 12:30 PM.
      Please be ready to receive your order. Track your delivery here: [Tracking Link]
      """

  # Scenario 2: Chef receives a notification for a scheduled cooking task
  Scenario: Notify the chef about an upcoming cooking task
    Given a chef has a scheduled cooking task for "Grilled Salmon" at "10:00 AM"
    And the task is due within 30 minutes
    When the system detects the approaching task time
    Then the chef should receive a notification:
      """
      Reminder: You have a scheduled cooking task for Grilled Salmon at 10:00 AM.
      Please start preparing the meal on time.
      """

  # Scenario 3: Customer receives a reminder for a meal subscription order
  Scenario: Notify customer about an upcoming meal subscription delivery
    Given a customer is subscribed to a weekly meal plan
    And the next delivery is scheduled for tomorrow at "1:00 PM"
    When the system detects the upcoming delivery
    Then the customer should receive a reminder 24 hours in advance:
      """
      Reminder: Your weekly meal plan delivery is scheduled for tomorrow at 1:00 PM.
      Please confirm your availability or reschedule if needed.
      """

  # Scenario 4: Chef receives a daily summary of scheduled cooking tasks
  Scenario: Send chefs a daily task summary
    Given a chef has multiple scheduled cooking tasks for the day
    When the chef logs into the system in the morning
   Then they should receive a task summary notification:
  """
  Today's Cooking Schedule:
  - 10:00 AM: Grilled Salmon (Order #1001)
  - 12:30 PM: Vegan Pasta (Order #1002)
  - 3:00 PM: Chicken Stir-Fry (Order #1003)
  Please prepare meals on time. Good luck!
  """

  # Scenario 5: Customer receives a final confirmation notification when delivery is near
  Scenario: Notify customer when delivery is 10 minutes away
    Given a meal delivery is on its way to the customer
    And the delivery is estimated to arrive within 10 minutes
    When the system detects the delivery status update
    Then the customer should receive a notification:
      """
      Your order is arriving soon! The delivery is 10 minutes away.
      Please be ready to receive your meal.
      """

