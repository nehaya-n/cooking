Feature: Billing System
  As a customer
  I want to receive an invoice
  So that I can review my payment details

  As a system administrator
  I want to generate financial reports
  So that I can analyze revenue and track business performance

  # Scenario 1: Generate an invoice for a completed order
  Scenario: Customer receives an invoice after an order is placed
    Given the customer has completed an order with the following details:
      | Order ID  | Items                         | Total Amount |
      | #12345    | Grilled Salmon, Lemon Tart   | $35.00       |
    When the system generates an invoice
    Then the invoice should contain:
      | Invoice Number | Order ID | Items                         | Total Amount | Payment Status |
      | INV-98765      | #12345   | Grilled Salmon, Lemon Tart   | $35.00       | Pending        |
    And the invoice should be sent to the customer's registered email

  # Scenario 2: Customer pays for an invoice
  Scenario: Customer completes invoice payment
    Given the customer has an outstanding invoice with Invoice Number "INV-98765"
    When the customer makes a payment of "$35.00"
    Then the system should update the invoice status to "Paid"
    And send a payment confirmation to the customer

  # Scenario 3: System administrator generates a daily financial report
  Scenario: Generate daily financial report
    Given there are completed orders for the day:
      | Order ID | Total Amount |
      | #12340   | $50.00       |
      | #12341   | $30.00       |
      | #12342   | $25.00       |
    When the system administrator generates a daily financial report
    Then the report should include:
      | Date       | Total Revenue | Number of Orders |
      | 2025-03-03 | $105.00       | 3               |
    And the system should store the report for future analysis

  # Scenario 4: System generates a monthly revenue report
  Scenario: Generate monthly revenue report
    Given the system has recorded transactions for the month of "February 2025"
    When the system administrator requests a monthly financial report
    Then the report should include:
      | Month       | Total Revenue | Total Orders | Average Order Value |
      | February 2025 | $3,500.00   | 120         | $29.17              |
    And the system should provide options to download or export the report

  # Scenario 5: Send overdue invoice reminders
  Scenario: System sends overdue invoice reminders
    Given a customer has an unpaid invoice with Invoice Number "INV-98760"
    And the due date has passed
    When the system runs the overdue invoice check
    Then the system should send a reminder email to the customer:
      """
      Subject: Payment Reminder for Invoice INV-98760
      Dear Customer,
      Your invoice INV-98760 is overdue. Please make a payment of $40.00 at your earliest convenience.
      Thank you.
      """
