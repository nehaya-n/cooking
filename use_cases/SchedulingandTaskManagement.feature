Feature: Scheduling and Task Management
  As a kitchen manager
  I want to assign tasks to chefs based on their workload and expertise
  So that I can ensure balanced workloads and efficiency

  As a chef
  I want to receive notifications about my assigned cooking tasks
  So that I can prepare meals on time

  # Scenario 1: Kitchen manager assigns tasks to chefs
  Scenario: Kitchen manager assigns cooking tasks
    Given there are multiple pending meal orders
    And the following chefs are available:
      | Chef Name      | Expertise         | Current Workload |
      | Chef Alex      | Italian Cuisine   | 2 orders         |
      | Chef Maria     | Vegan Cuisine     | 3 orders         |
      | Chef John      | Grilling          | 1 order          |
    When the kitchen manager assigns a new "Grilled Chicken" order
    Then the system should assign the task to "Chef John" based on lowest workload
    And update Chef John’s workload to 2 orders

  # Scenario 2: Chef receives a notification for a new task
  Scenario: Chef gets notified about a new assigned task
    Given Chef Alex has been assigned a new "Pasta Alfredo" task
    When the task is assigned
    Then the system should send a notification to Chef Alex with the task details:
      """
      New Task Assigned:
      Dish: Pasta Alfredo
      Due Time: 7:30 PM
      """
    And the task should appear in Chef Alex’s task list

  # Scenario 3: System prevents overloading a chef
  Scenario: System balances workload to prevent chef overload
    Given Chef Maria already has 5 active tasks
    When the kitchen manager attempts to assign a new "Vegan Burger" order to Chef Maria
    Then the system should suggest assigning the order to a different chef
    And display a warning: "Chef Maria is at full capacity. Assign to another available chef."

  # Scenario 4: Kitchen manager reassigns a task to another chef
  Scenario: Reassigning a cooking task
    Given Chef John has been assigned a "Grilled Salmon" task
    And Chef John is unavailable
    When the kitchen manager reassigns the task to Chef Maria
    Then the system should update the task assignment to Chef Maria
    And notify Chef Maria with the new task details:
      """
      Reassigned Task:
      Dish: Grilled Salmon
      Due Time: 8:00 PM
      """

  # Scenario 5: Chef completes a task and updates the system
  Scenario: Chef marks a task as completed
    Given Chef Alex has an active "Pasta Alfredo" task
    When Chef Alex finishes preparing the meal
    And marks the task as "Completed"
    Then the system should update the task status to "Completed"
    And notify the kitchen manager about the completion