package catering_service;

public class Invoice {
    private int invoiceId;
    private int bookingId;
    private double totalAmount;
    private String paymentStatus;

    public Invoice(int invoiceId, int bookingId, double totalAmount, String paymentStatus) {
        this.invoiceId = invoiceId;
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters
    public int getInvoiceId() { return invoiceId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}