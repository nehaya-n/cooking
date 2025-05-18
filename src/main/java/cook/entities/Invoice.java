package cook.entities;

public class Invoice {
    private String invoiceNumber;
    private String orderId;
    private String items;
    private double totalAmount;
    private String paymentStatus;
    private Order order;

    public Invoice(String invoiceNumber, String orderId, String items, double totalAmount) {
        this.invoiceNumber = invoiceNumber;
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentStatus = "Pending";
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void updatePaymentStatus(String status) {
        this.paymentStatus = status;
    }

    public void sendInvoiceToCustomer(String customerEmail) {
        // Dummy method to simulate sending the invoice.
        System.out.println("Invoice sent to: " + customerEmail);
    }
 
}