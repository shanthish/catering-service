package catering_service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Customer_main {
    public void createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customer (name, contact, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());
            pstmt.executeUpdate();
        }
    }
    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
                );
            }
        }
        return null;
    }
    public List<Customer> getAllCustomers() throws SQLException {
        String query = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address")
                ));
            }
        }
        return customers;
    }
    public void updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE Customer SET name = ?, contact = ?, email = ?, address = ? WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());
            pstmt.setInt(5, customer.getCustomerId());
            pstmt.executeUpdate();
        }
    }
    public void deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        }
    }
}
