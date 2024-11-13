package catering_service;
import java.sql.*;
public class Invoiceops {
    public void createInvoice(Invoice invoice) throws SQLException {
        String query = "INSERT INTO Invoice (booking_id, total_amount, payment_status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, invoice.getBookingId());
            pstmt.setDouble(2, invoice.getTotalAmount());
            pstmt.setString(3, invoice.getPaymentStatus());
            pstmt.executeUpdate();
        }
    }
    public Invoice getInvoiceById(int invoiceId) throws SQLException {
        String query = "SELECT * FROM Invoice WHERE invoice_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Invoice(
                    rs.getInt("invoice_id"),
                    rs.getInt("booking_id"),
                    rs.getDouble("total_amount"),
                    rs.getString("payment_status")
                );
            }
        }
        return null;
    }
    public void updateInvoiceStatus(int invoiceId, String paymentStatus) throws SQLException {
        String query = "UPDATE Invoice SET payment_status = ? WHERE invoice_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, paymentStatus);
            pstmt.setInt(2, invoiceId);
            pstmt.executeUpdate();
        }
    }
    public void deleteInvoice(int invoiceId) throws SQLException {
        String query = "DELETE FROM Invoice WHERE invoice_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, invoiceId);
            pstmt.executeUpdate();
        }
    }
}
