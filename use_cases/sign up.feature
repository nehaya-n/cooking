
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

 
  Scenario: Valid username format
    Then the username "validUser123" should be valid

  Scenario: Invalid username format
    Then the username "invalid@user!" should be invalid

  Scenario: Valid email format
    Then the email "example@mail.com" should be valid

  Scenario: Invalid email format
    Then the email "invalid-email" should be invalid

  Scenario: Valid password format
    Then the password "abc123" should be valid

  Scenario: Invalid password format
    Then the password "123" should be invalid

  Scenario: Successful login with correct credentials
    Given the system has the following registered users:
      | username | email           | role     | password | confirmPassword |
      | user1    | user1@mail.com  | Customer | secret1  | secret1         |
    When I try to login with username "user1" and password "secret1"
    Then the login result should be true

  Scenario: Failed login with wrong credentials
    When I try to login with username "user1" and password "wrongpass"
    Then the login result should be false

  Scenario: Get role of a registered user
    Given the system has the following registered users:
      | username | email             | role  | password | confirmPassword |
      | chef     | chef@kitchen.com  | Chef  | cook123  | cook123         |
    When I request the role for username "chef"
    Then the system should return the role "Chef"