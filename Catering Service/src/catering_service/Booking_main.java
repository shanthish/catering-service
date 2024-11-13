package catering_service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Booking_main {
    public void createBooking(Booking booking) throws SQLException {
        String query = "INSERT INTO Booking (customer_id, event_date, event_type, location, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, booking.getCustomerId());
            pstmt.setDate(2, booking.getEventDate());
            pstmt.setString(3, booking.getEventType());
            pstmt.setString(4, booking.getLocation());
            pstmt.setString(5, booking.getStatus());
            pstmt.executeUpdate();
        }
    }
    public Booking getBookingById(int bookingId) throws SQLException {
        String query = "SELECT * FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getDate("event_date"),
                    rs.getString("event_type"),
                    rs.getString("location"),
                    rs.getString("status")
                );
            }
        }
        return null;
    }
    public List<Booking> getAllBookings() throws SQLException {
        String query = "SELECT * FROM Booking";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("customer_id"),
                    rs.getDate("event_date"),
                    rs.getString("event_type"),
                    rs.getString("location"),
                    rs.getString("status")
                ));
            }
        }
        return bookings;
    }
    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String query = "UPDATE Booking SET status = ? WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);
            pstmt.executeUpdate();
        }
    }
    public void deleteBooking(int bookingId) throws SQLException {
        String query = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
        }
    }
}
