package catering_service;

import java.sql.*;

public class AdminLogin {
    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // If result exists, login is successful
        }
    }
}
