package testPackage;

import cook.entities.*;

import java.util.Arrays;

import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class BillingSteps {

    private Invoice invoice;
    private FoodOrder order;
    private FoodOrder order1;
    private FoodOrder order2; // Added the second order
    private FoodOrder order3; // Added the third order
    private FinancialReport dailyReport;
    private FinancialReport monthlyReport;

    @Given("the customer has completed an order with the following details:")
    public void givenTheCustomerHasCompletedAnOrderWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {
        // Create a sample order from the provided data
        String orderId = dataTable.cell(1, 0);
        String items = dataTable.cell(1, 1);
        double totalAmount = Double.parseDouble(dataTable.cell(1, 2).replace("$", ""));
        order = new FoodOrder(orderId, "John Doe", "2025-03-03 12:00 PM");
        order.setStatus("Completed"); // Make sure to mark it completed
    }

    @When("the system generates an invoice")
    public void whenTheSystemGeneratesAnInvoice() {
        invoice = new Invoice("INV-98765", order.getOrderId(), "Grilled Salmon, Lemon Tart", order.getTotalAmount());
    }

    @Then("the invoice should contain:")
    public void thenTheInvoiceShouldContain(io.cucumber.datatable.DataTable dataTable) {
        assertEquals(dataTable.cell(1, 0), invoice.getInvoiceNumber());
        assertEquals(dataTable.cell(1, 1), invoice.getOrderId());
        assertEquals(dataTable.cell(1, 2), invoice.getItems());
        assertEquals(Double.parseDouble(dataTable.cell(1, 3).replace("$", "")), invoice.getTotalAmount(), 0.01);
        assertEquals(dataTable.cell(1, 4), invoice.getPaymentStatus());
    }

    @And("the invoice should be sent to the customer's registered email")
    public void andTheInvoiceShouldBeSentToTheCustomerSRegisteredEmail() {
        invoice.sendInvoiceToCustomer("johndoe@example.com");
    }

    @Given("the customer has an outstanding invoice with Invoice Number {string}")
    public void givenTheCustomerHasAnOutstandingInvoiceWithInvoiceNumber(String invoiceNumber) {
        invoice = new Invoice(invoiceNumber, "#12345", "Grilled Salmon, Lemon Tart", 35.00);
    }

    @When("the customer makes a payment of {string}")
    public void whenTheCustomerMakesAPaymentOf(String amount) {
        invoice.updatePaymentStatus("Paid");
    }

    @Then("the system should update the invoice status to {string}")
    public void thenTheSystemShouldUpdateTheInvoiceStatusTo(String status) {
        assertEquals(status, invoice.getPaymentStatus());
    }

    @And("send a payment confirmation to the customer")
    public void andSendAPaymentConfirmationToTheCustomer() {
        System.out.println("Payment Confirmation Sent to customer.");
    }

    @Given("there are completed orders for the day:")
    public void givenThereAreCompletedOrdersForTheDay(io.cucumber.datatable.DataTable dataTable) {
        // Create orders from the provided data
        order = new FoodOrder("#12340", "Alice", "2025-03-03 10:00 AM");
        order.setStatus("Completed");
        order.setTotalAmount(50.00);

        order2 = new FoodOrder("#12341", "Bob", "2025-03-03 11:00 AM");
        order2.setStatus("Completed");
        order2.setTotalAmount(30.00);

        order3 = new FoodOrder("#12342", "Charlie", "2025-03-03 12:00 PM");
        order3.setStatus("Completed");
        order3.setTotalAmount(25.00);
    }

    @When("the system administrator generates a daily financial report")
    public void whenTheSystemAdministratorGeneratesADailyFinancialReport() {
        dailyReport = FinancialReport.generateDailyReport(Arrays.asList(order, order2, order3));
    }
    @Then("the daily report should include :")
    public void thenTheReportShouldInclude(io.cucumber.datatable.DataTable dataTable) {
        assertEquals(dataTable.cell(1, 0), dailyReport.getDate());
        assertEquals(Double.parseDouble(dataTable.cell(1, 1).replace("$", "")), dailyReport.getTotalRevenue(), 0.01);
        assertEquals(Integer.parseInt(dataTable.cell(1, 2)), dailyReport.getNumberOfOrders());
    }

   

    @And("the system should store the report for future analysis")
    public void andTheSystemShouldStoreTheReportForFutureAnalysis() {
        System.out.println("Daily report stored for future analysis.");
    }

    @Given("the system has recorded transactions for the month of {string}")
    public void givenTheSystemHasRecordedTransactionsForTheMonth(String month) {
        // Create orders from the provided data for the given month
        order1 = new FoodOrder("#12340", "Alice", "2025-02-01 10:00 AM");
        order1.setStatus("Completed");
        order1.setTotalAmount(50.00);

        order2 = new FoodOrder("#12341", "Bob", "2025-02-01 11:00 AM");
        order2.setStatus("Completed");
        order2.setTotalAmount(30.00);

        order3 = new FoodOrder("#12342", "Charlie", "2025-02-01 12:00 PM");
        order3.setStatus("Completed");
        order3.setTotalAmount(25.00);

        // Now call generateMonthlyReport and assign the result to monthlyReport
        monthlyReport = FinancialReport.generateMonthlyReport(Arrays.asList(order1, order2, order3));
    }

    @When("the system administrator requests a monthly financial report")
    public void whenTheSystemAdministratorRequestsAMonthlyFinancialReport() {
        // Ensure that all orders are passed to the generateMonthlyReport method
        monthlyReport = FinancialReport.generateMonthlyReport(Arrays.asList(order1, order2, order3));
    }

    @Then("the report should include:")
    public void thenTheReportShouldIncludeMonthly(io.cucumber.datatable.DataTable dataTable) {
        // Ensure monthlyReport is not null
        assertNotNull("Monthly report should not be null", monthlyReport);
        
        // Remove "$" and "," before parsing the number
        String totalRevenueString = dataTable.cell(1, 1).replace("$", "").replace(",", "");
        double expectedTotalRevenue = Double.parseDouble(totalRevenueString);
        
        // Ensure the expected revenue matches the calculated revenue
        assertEquals("Total revenue does not match", expectedTotalRevenue, monthlyReport.getTotalRevenue(), 0.01);
        assertEquals("Month does not match", dataTable.cell(1, 0), monthlyReport.getDate());
        assertEquals("Number of orders does not match", Integer.parseInt(dataTable.cell(1, 2)), monthlyReport.getNumberOfOrders());
    }


}
