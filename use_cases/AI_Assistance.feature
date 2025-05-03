Feature: AI-Assisted Recipe Recommendation
  As a customer
  I want an AI assistant to recommend recipes
  So that I can easily find meals that match my dietary restrictions, available ingredients, and time constraints

  # Scenario 1: AI recommends the best recipe based on dietary restrictions, available ingredients, and time
  Scenario: AI selects the best recipe for a vegan customer
    Given a user with the following preferences:
      | Dietary Restrictions | Ingredients Available         | Time Available |
      | Vegan               | Tomatoes, basil, pasta       | 30 minutes     |
    And the following recipes are in the database:
      | Recipe Name             | Ingredients                  | Time | Dietary Restriction |
      | Spaghetti with Tomato Sauce | Tomatoes, pasta, basil, olive oil | 25 min | Vegan  |
      | Tomato Basil Soup         | Tomatoes, basil, garlic     | 40 min | Vegan  |
      | Vegan Pesto Pasta        | Basil, pasta, olive oil, garlic | 20 min | Vegan  |
    When the AI processes the user’s preferences
    Then the system should recommend "Spaghetti with Tomato Sauce"
    And provide an explanation:
      """
      Based on your preferences, I recommend "Spaghetti with Tomato Sauce" because:
      - It is vegan.
      - It uses all the available ingredients: Tomatoes, basil, and pasta.
      - It can be prepared within your available time of 30 minutes (only 25 minutes required).
      """

  # Scenario 2: AI handles a scenario where no exact match is found
  Scenario: AI suggests the closest recipe when an exact match is unavailable
    Given a user with the following preferences:
      | Dietary Restrictions | Ingredients Available | Time Available |
      | Vegan               | Mushrooms, onions    | 15 minutes     |
    And the following recipes are in the database:
      | Recipe Name             | Ingredients                  | Time  | Dietary Restriction |
      | Spaghetti with Tomato Sauce | Tomatoes, pasta, basil, olive oil | 25 min | Vegan  |
      | Tomato Basil Soup         | Tomatoes, basil, garlic     | 40 min | Vegan  |
      | Vegan Pesto Pasta        | Basil, pasta, olive oil, garlic | 20 min | Vegan  |
    When the AI processes the user’s preferences
    Then the system should return:
      """
      No exact match found for your available ingredients. However, you may consider adding pasta and basil to try "Vegan Pesto Pasta," which fits your dietary restrictions and can be prepared in 20 minutes.
      """

  # Scenario 3: AI suggests modifications when an ingredient is missing
  Scenario: AI recommends a recipe with ingredient substitution
    Given a user with the following preferences:
      | Dietary Restrictions | Ingredients Available | Time Available |
      | Vegan               | Tomatoes, pasta       | 30 minutes     |
    And the following recipes are in the database:
      | Recipe Name             | Ingredients                  | Time  | Dietary Restriction |
      | Spaghetti with Tomato Sauce | Tomatoes, pasta, basil, olive oil | 25 min | Vegan  |
    When the AI processes the user’s preferences
    And detects that "basil" is missing
    Then the system should suggest:
      """
      You can make "Spaghetti with Tomato Sauce" by substituting basil with oregano or parsley, if available. The recipe remains vegan and can be prepared in 25 minutes.
      """

  # Scenario 4: AI adapts recommendations based on available time
  Scenario: AI filters recipes based on preparation time
    Given a user with the following preferences:
      | Dietary Restrictions | Ingredients Available  | Time Available |
      | Vegan               | Tomatoes, basil, pasta | 15 minutes     |
    And the following recipes are in the database:
      | Recipe Name             | Ingredients                  | Time  | Dietary Restriction |
      | Spaghetti with Tomato Sauce | Tomatoes, pasta, basil, olive oil | 25 min | Vegan  |
      | Vegan Pesto Pasta        | Basil, pasta, olive oil, garlic | 20 min | Vegan  |
    When the AI processes the user’s preferences
    Then the system should return:
      """
      No available recipes can be prepared within 15 minutes. However, the closest option is "Vegan Pesto Pasta," which takes 20 minutes. Would you like to proceed with it or explore other options?
      """





