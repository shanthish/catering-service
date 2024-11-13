package catering_service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import catering_service.Admin;
import catering_service.Customer_tasks;
import catering_service.DBConnection;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection dbConnection = null;
		try {
			dbConnection = DBConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        Admin adminOperations = new Admin(dbConnection);
        Customer_tasks customerOperations = new Customer_tasks(dbConnection);
        boolean systemRunning = true;
        while (systemRunning) {
            System.out.println("Welcome to Catering Service System");
            System.out.println("**************************************************");
            System.out.println("1. Admin Login\n2. Customer Login\n3. Exit");
            int userType = sc.nextInt();

            switch (userType) {
                case 1:
                    adminMenu(adminOperations, sc);
                    break;
                case 2:
                    customerMenu(customerOperations, sc);
                    break;
                case 3:
                    systemRunning = false;
                    System.out.println("Exiting system. Thank you!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        sc.close();
    }

    // Admin Menu
    private static void adminMenu(Admin adminOps, Scanner sc) {
        boolean adminLoggedIn = true;

        while (adminLoggedIn) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Bookings\n2. Manage Menu Items\n3. Manage Customer Data\n4. Manage Booking Status\n5. Generate Reports\n6. Handle Payments\n7.Handle Booking Cancellations\n8. Logout");
            int adminChoice = sc.nextInt();

            switch (adminChoice) {
                case 1:
                    adminOps.viewAllBookings();
                    break;
                case 2:
                    adminOps.manageMenuItems(sc);
                    break;
                case 3:
                    adminOps.manageCustomerData(sc);
                    break;
                case 4:
                    adminOps.manageBookingStatus(sc);
                    break;
                case 5:
                    adminOps.generateReports();
                    break;
                case 6:
                    adminOps.handlePayments(sc);
                    break;
                case 7:
                    adminOps.handleBookingCancellations(sc);
                    break;
                case 8:
                    adminLoggedIn = false;
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Customer Menu
    private static void customerMenu(Customer_tasks custOps, Scanner sc) {
        boolean customerLoggedIn = true;

        while (customerLoggedIn) {
            System.out.println("Customer Menu:");
            System.out.println("1. Create Booking\n2. Select Menu Items\n3. View Booking Details\n4. Update Booking\n5. Make Payment\n6. View Invoice\n7. Cancel Booking\n8. Logout");
            int customerChoice = sc.nextInt();

            switch (customerChoice) {
                case 1:
                    custOps.createBooking(sc);
                    break;
                case 2:
                    custOps.selectMenuItems(sc);
                    break;
                case 3:
                    custOps.viewBookingDetails(sc);
                    break;
                case 4:
                    custOps.updateBooking(sc);
                    break;
                case 5:
                    custOps.makePayment(sc);
                    break;
                case 6:
                    custOps.viewInvoice(sc);
                    break;
                case 7:
                    custOps.cancelBooking(sc);
                    break;
                case 8:
                    customerLoggedIn = false;
                    System.out.println("Customer logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

