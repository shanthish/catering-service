package catering_service;

import java.sql.*;
import java.util.Scanner;
public class Admin {
    private Connection connection;
    public Admin(Connection connection) {
        this.connection = connection;
    }
	// 1. View All Bookings
    public void viewAllBookings() {
        String query = "SELECT * FROM Booking";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Event Date: " + rs.getDate("event_date"));
                System.out.println("Menu Selection: " + rs.getString("menu_selection"));
                System.out.println("Payment Status: " + rs.getString("payment_status"));
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }
    }

    // 2. Manage Menu Items
    public void manageMenuItems(Scanner sc) {
        System.out.println("1. Add Item\n2. Update Item\n3. Remove Item");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                addMenuItem(sc);
                break;
            case 2:
                updateMenuItem(sc);
                break;
            case 3:
                removeMenuItem(sc);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addMenuItem(Scanner sc) {
        System.out.println("Enter Item Name:");
        String itemName = sc.next();
        System.out.println("Enter Item Type (Starter/MainCourse/Dessert):");
        String itemType = sc.next();
        System.out.println("Enter Price:");
        double price = sc.nextDouble();

        String query = "INSERT INTO MenuItem (item_name,type, price) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, itemName);
            pstmt.setString(2, itemType);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            System.out.println("Menu item added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding menu item: " + e.getMessage());
        }
    }

    private void updateMenuItem(Scanner sc) {
        System.out.println("Enter Item ID to Update:");
        int itemId = sc.nextInt();
        System.out.println("Enter New Price:");
        double newPrice = sc.nextDouble();

        String query = "UPDATE MenuItem SET price = ? WHERE item_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
            System.out.println("Menu item updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating menu item: " + e.getMessage());
        }
    }

    private void removeMenuItem(Scanner sc) {
        System.out.println("Enter Item ID to Remove:");
        int itemId = sc.nextInt();

        String query = "DELETE FROM MenuItem WHERE item_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
            System.out.println("Menu item removed successfully.");
        } catch (SQLException e) {
            System.out.println("Error removing menu item: " + e.getMessage());
        }
    }

    // 3. Manage Customer Data
    public void manageCustomerData(Scanner sc) {
        System.out.println("Enter Customer ID to view or update details:");
        int customerId = sc.nextInt();
        // View customer details
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Contact: " + rs.getString("contact"));

                System.out.println("Do you want to update the contact info? (yes/no)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("Enter new contact:");
                    String newContact = sc.next();
                    updateCustomerContact(customerId, newContact);
                }
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error managing customer data: " + e.getMessage());
        }
    }

    private void updateCustomerContact(int customerId, String newContact) {
        String query = "UPDATE Customer SET contact = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newContact);
            pstmt.setInt(2, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer contact updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating customer contact: " + e.getMessage());
        }
    }

    // 4. Manage Booking Status
    public void manageBookingStatus(Scanner sc) {
        System.out.println("Enter Booking ID to update status:");
        int bookingId = sc.nextInt();

        // Check if booking ID exists
        String checkQuery = "SELECT COUNT(*) FROM Booking WHERE booking_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Booking ID not found.");
                return; // Exit if booking ID does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error checking booking ID: " + e.getMessage());
            return;
        }

        // Prompt for new status only if booking ID exists
        System.out.println("Enter new status (Confirmed/Pending/Cancelled):");
        String status = sc.next();

        // Update booking status
        String updateQuery = "UPDATE Booking SET status = ? WHERE booking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookingId);
            pstmt.executeUpdate();
            System.out.println("Booking status updated.");
        } catch (SQLException e) {
            System.out.println("Error updating booking status: " + e.getMessage());
        }
    }


    // 5. Generate Reports
    public void generateReports() {
        String query = "SELECT * FROM Booking";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Booking Report:");
            while (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id") + ", Customer ID: " + rs.getInt("customer_id") + ", Payment Status: " + rs.getString("payment_status"));
            }
        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
    public void handlePayments(Scanner sc) {
        System.out.println("Enter Booking ID to handle payment:");
        int bookingId = sc.nextInt();

        // Check if booking exists
        String checkBookingQuery = "SELECT COUNT(*) FROM Booking WHERE booking_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkBookingQuery)) {
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Booking ID not found.");
                return; // Exit if booking ID does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error checking booking ID: " + e.getMessage());
            return;
        }

        String paymentStatus;
        while (true) {
            System.out.println("Enter payment status (Confirmed/Rejected):");
            paymentStatus = sc.next();
            if (paymentStatus.equals("Confirmed") || paymentStatus.equals("Rejected")) {
                break; // Valid input
            } else {
                System.out.println("Invalid status. Please enter either 'Confirmed' or 'Rejected'.");
            }
        }

        // Update payment status
        String updatePaymentQuery = "UPDATE Booking SET payment_status = ? WHERE booking_id = ?";
        try (PreparedStatement updateStmt = connection.prepareStatement(updatePaymentQuery)) {
            updateStmt.setString(1, paymentStatus);
            updateStmt.setInt(2, bookingId);
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Payment status updated successfully.");
            } else {
                System.out.println("Failed to update payment status.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating payment status: " + e.getMessage());
        }
    }

    public void handleBookingCancellations(Scanner sc) {
        System.out.println("Enter Booking ID to cancel:");
        int bookingId = sc.nextInt();

        // Check if booking exists
        String checkBookingQuery = "SELECT COUNT(*) FROM Booking WHERE booking_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkBookingQuery)) {
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Booking ID not found.");
                return; // Exit if booking ID does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error checking booking ID: " + e.getMessage());
            return;
        }

        // Update booking status to "Cancelled"
        String cancelBookingQuery = "UPDATE Booking SET status = 'Cancelled' WHERE booking_id = ?";
        try (PreparedStatement cancelStmt = connection.prepareStatement(cancelBookingQuery)) {
            cancelStmt.setInt(1, bookingId);
            int rowsUpdated = cancelStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("Failed to cancel booking.");
            }
        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
        }
    }

    
}
