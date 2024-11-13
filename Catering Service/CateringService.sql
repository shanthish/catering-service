show databases;

Create database Catering_Service_Booking;
use Catering_Service_Booking;
CREATE TABLE Customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contact VARCHAR(15),
    address VARCHAR(255)
);
CREATE TABLE MenuItem (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    type ENUM('Starter', 'Main Course', 'Dessert') NOT NULL
);
CREATE TABLE Booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    event_date DATE NOT NULL,
    event_type VARCHAR(100) NOT NULL,  
    location VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id) ON DELETE CASCADE
);
CREATE TABLE Invoice (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status ENUM('Pending', 'Completed', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE
);
CREATE TABLE BookingMenuItem (
    booking_id INT,
    item_id INT,
    quantity INT NOT NULL,
    PRIMARY KEY (booking_id, item_id),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES MenuItem(item_id) ON DELETE CASCADE
);
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_status ENUM('Confirmed', 'Rejected', 'Pending') DEFAULT 'Pending',
    transaction_id VARCHAR(50),
    payment_method VARCHAR(50),
    FOREIGN KEY (booking_id) REFERENCES Booking(booking_id) ON DELETE CASCADE
);
insert into Customer(name,email,contact,address) values("Shanthish","shan@gmail.com","915064172","29/E,vidhay nagar,salem");
select* from Customer;