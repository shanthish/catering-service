package catering_service;

import java.sql.Date;
public class Booking {
    private int bookingId;
    private int customerId;
    private Date eventDate;
    private String eventType;
    private String location;
    private String status;
    public Booking(int bookingId, int customerId, Date eventDate, String eventType, String location, String status) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.location = location;
        this.status = status;
    }
    // Getters and setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

