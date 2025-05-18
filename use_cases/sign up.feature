Feature: User Signup

  Scenario: Valid signup
    When I try to sign up with username "lana12", email "lana567@gmail.com", role "Customer", password "passWord1", confirm password "passWord1"
    Then I should see "Account created successfully"

  Scenario: Username is invalid
    When I try to sign up with username "lan@", email "lana1@gmail.com", role "Customer", password "passWord14", confirm password "passWord14"
    Then I should see "Invalid username (alphanumeric ≤15 chars)."

  Scenario: Email is already in use
    Given the system has the following registered users:
      | username | email             | role     | password   | confirmPassword |
      | existing | usedemail@gmail.com | Customer | passWord1 | passWord1       |
    When I try to sign up with username "lana", email "usedemail@gmail.com", role "Customer", password "passWord1", confirm password "passWord1"
    Then I should see "Email already in use."

  Scenario: Passwords do not match
    When I try to sign up with username "lana123", email "lana12@gmail.com", role "Customer", password "passWord12", confirm password "differentPass"
    Then I should see "Passwords do not match."

  Scenario: Password too short
    When I try to sign up with username "lana123", email "lana123@gmail.com", role "Customer", password "a", confirm password "a"
    Then I should see "Password ≥6 chars & at least one letter."

  Scenario: Role is invalid
    When I try to sign up with username "lana123", email "lana1234@gmail.com", role "InvalidRole", password "passWord123", confirm password "passWord123"
    Then I should see "Role must be Customer, Chef or Manager."
