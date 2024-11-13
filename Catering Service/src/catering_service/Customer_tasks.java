package catering_service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class Customer_tasks {
    private Connection connection;

    public Customer_tasks(Connection connection) {
        this.connection = connection;
    }
    // 1. Create a Booking
    public void createBooking(Scanner sc) {
        System.out.println("Enter your Customer ID:");
        int customerId = sc.nextInt();
        System.out.println("Enter Event Date (YYYY-MM-DD):");
        String eventDate = sc.next();
        System.out.println("Enter Event Type:");
        String eventType = sc.next();
        System.out.println("Enter Event Location:");
        String location = sc.next();

        String query = "INSERT INTO Booking (customer_id, event_date, event_type, location) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            pstmt.setDate(2, Date.valueOf(eventDate));
            pstmt.setString(3, eventType);
            pstmt.setString(4, location);
            pstmt.executeUpdate();
            System.out.println("Booking created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
    }

    // 2. Select Menu Items
    public void selectMenuItems(Scanner sc) {
        System.out.println("Enter Booking ID:");
        int bookingId = sc.nextInt();

        // Check if the booking ID exists
        String bookingCheckQuery = "SELECT COUNT(*) FROM Booking WHERE booking_id = ?";
        try (PreparedStatement bookingCheckStmt = connection.prepareStatement(bookingCheckQuery)) {
            bookingCheckStmt.setInt(1, bookingId);
            ResultSet bookingRs = bookingCheckStmt.executeQuery();
            if (bookingRs.next() && bookingRs.getInt(1) == 0) {
                System.out.println("Booking ID not found.");
                return; // Exit if booking ID does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error checking booking ID: " + e.getMessage());
            return;
        }

        List<Integer> menuItems = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        boolean selecting = true;

        while (selecting) {
            System.out.println("Select Menu Item by ID (or -1 to finish):");
            int itemId = sc.nextInt();
            
            if (itemId == -1) {
                selecting = false;
            } else {
                // Check if the item ID exists
                String itemCheckQuery = "SELECT COUNT(*) FROM MenuItem WHERE item_id = ?";
                try (PreparedStatement itemCheckStmt = connection.prepareStatement(itemCheckQuery)) {
                    itemCheckStmt.setInt(1, itemId);
                    ResultSet itemRs = itemCheckStmt.executeQuery();
                    if (itemRs.next() && itemRs.getInt(1) == 0) {
                        System.out.println("Menu Item ID not found.");
                        continue; // Skip if item ID does not exist
                    }
                } catch (SQLException e) {
                    System.out.println("Error checking item ID: " + e.getMessage());
                    continue;
                }

                // Prompt for quantity (considered as the number of people)
                System.out.println("Enter quantity for this item (number of people):");
                int quantity = sc.nextInt();

                menuItems.add(itemId);
                quantities.add(quantity);
            }
        }

        // Insert each selected item with its quantity
        String query = "INSERT INTO BookingMenuItem (booking_id, item_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < menuItems.size(); i++) {
                pstmt.setInt(1, bookingId);
                pstmt.setInt(2, menuItems.get(i));
                pstmt.setInt(3, quantities.get(i));
                pstmt.executeUpdate();
            }
            System.out.println("Menu items selected successfully.");
        } catch (SQLException e) {
            System.out.println("Error selecting menu items: " + e.getMessage());
        }
    }

    // 3. View Booking Details
    public void viewBookingDetails(Scanner sc) {
        System.out.println("Enter Booking ID:");
        int bookingId = sc.nextInt();

        String query = "SELECT * FROM Booking WHERE booking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Booking ID: " + rs.getInt("booking_id"));
                System.out.println("Customer ID: " + rs.getInt("customer_id"));
                System.out.println("Event Date: " + rs.getDate("event_date"));
                System.out.println("Event Type: " + rs.getString("event_type"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Status: " + rs.getString("status"));
            } else {
                System.out.println("Booking not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing booking details: " + e.getMessage());
        }
    }

    // 4. Update Booking
    public void updateBooking(Scanner sc) {
        System.out.println("Enter Booking ID to Update:");
        int bookingId = sc.nextInt();
        System.out.println("Enter New Event Date (YYYY-MM-DD):");
        String newEventDate = sc.next();

        String query = "UPDATE Booking SET event_date = ? WHERE booking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, Date.valueOf(newEventDate));
            pstmt.setInt(2, bookingId);
            pstmt.executeUpdate();
            System.out.println("Booking updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating booking: " + e.getMessage());
        }
    }

    // 5. Make Payment
    public void makePayment(Scanner sc) {
        System.out.println("Enter Booking ID for Payment:");
        int bookingId = sc.nextInt();
        
        // Check if the booking ID exists in the Booking table
        String checkBookingQuery = "SELECT COUNT(*) FROM Booking WHERE booking_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkBookingQuery)) {
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Error: Booking ID not found.");
                return; // Exit the method if booking ID does not exist
            }
        } catch (SQLException e) {
            System.out.println("Error checking booking ID: " + e.getMessage());
            return;
        }

        System.out.println("Enter Payment Amount:");
        double amount = sc.nextDouble();
        System.out.println("Enter Payment Method (e.g., CreditCard, Cash):");
        String paymentMethod = sc.next();

        // Insert payment details into the Payments table
        String query = "INSERT INTO Payments (booking_id, amount, payment_date, payment_method, payment_status) VALUES (?, ?, CURDATE(), ?, 'Confirmed')";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, paymentMethod);
            pstmt.executeUpdate();
            System.out.println("Payment recorded successfully.");
        } catch (SQLException e) {
            System.out.println("Error processing payment: " + e.getMessage());
        }
    }

    // 6. View Invoice
    public void viewInvoice(Scanner sc) {
        System.out.println("Enter Payment Status to filter invoices (e.g., Confirmed, Pending, Cancelled):");
        String paymentStatus = sc.next();

        String query = "SELECT * FROM Invoice WHERE payment_status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, paymentStatus);
            ResultSet rs = pstmt.executeQuery();
            
            boolean hasInvoices = false;
            while (rs.next()) {
                hasInvoices = true;
                int invoiceId = rs.getInt("invoice_id");
                int bookingId = rs.getInt("booking_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("payment_status");

                // Print the invoice to the console
                System.out.println("Invoice ID: " + invoiceId);
                System.out.println("Booking ID: " + bookingId);
                System.out.println("Total Amount: " + totalAmount);
                System.out.println("Payment Status: " + status);
                System.out.println("-----------------------------------");

                // Write the invoice to a text file
                String fileName = "Invoice_" + invoiceId + ".txt";
                try (FileWriter writer = new FileWriter(fileName)) {
                    writer.write("Invoice ID: " + invoiceId + "\n");
                    writer.write("Booking ID: " + bookingId + "\n");
                    writer.write("Total Amount: " + totalAmount + "\n");
                    writer.write("Payment Status: " + status + "\n");
                    writer.write("-----------------------------------\n");
                    System.out.println("Invoice saved to file: " + fileName);
                } catch (IOException e) {
                    System.out.println("Error writing invoice to file: " + e.getMessage());
                }
            }
            
            if (!hasInvoices) {
                System.out.println("No invoices found with payment status: " + paymentStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing invoices: " + e.getMessage());
        }
    }
    // 7. Cancel Booking
    public void cancelBooking(Scanner sc) {
        System.out.println("Enter Booking ID to Cancel:");
        int bookingId = sc.nextInt();

        String query = "UPDATE Booking SET status = 'Cancelled' WHERE booking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, bookingId);
            pstmt.executeUpdate();
            System.out.println("Booking cancelled successfully.");
        } catch (SQLException e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
        }
    }
}