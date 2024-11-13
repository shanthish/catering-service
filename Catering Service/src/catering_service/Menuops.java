package catering_service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Menuops {
    public void addMenuItem(Menucard menuItem) throws SQLException {
        String query = "INSERT INTO MenuItem (name, price, type) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setString(3, menuItem.getType());
            pstmt.executeUpdate();
        }
    }
    public Menucard getMenuItemById(int itemId) throws SQLException {
        String query = "SELECT * FROM MenuItem WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("Starter")) {
                    return new Starter(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price"));
                } else if (type.equals("Main Course")) {
                    return new MainCourse(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price"));
                } else if (type.equals("Dessert")) {
                    return new Dessert(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price"));
                }
            }
        }
        return null;
    }
    public List<Menucard> getAllMenuItems() throws SQLException {
        String query = "SELECT * FROM MenuItem";
        List<Menucard> menuItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("Starter")) {
                    menuItems.add(new Starter(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price")));
                } else if (type.equals("Main Course")) {
                    menuItems.add(new MainCourse(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price")));
                } else if (type.equals("Dessert")) {
                    menuItems.add(new Dessert(rs.getInt("item_id"), rs.getString("name"), rs.getDouble("price")));
                }
            }
        }
        return menuItems;
    }
    public void updateMenuItem(Menucard menuItem) throws SQLException {
        String query = "UPDATE MenuItem SET name = ?, price = ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setInt(3, menuItem.getItemId());
            pstmt.executeUpdate();
        }
    }
    public void deleteMenuItem(int itemId) throws SQLException {
        String query = "DELETE FROM MenuItem WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
        }
    }
}

