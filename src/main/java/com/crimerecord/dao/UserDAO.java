package com.crimerecord.dao;

import com.crimerecord.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean login(String username, String password) {
        String sql = "SELECT id FROM users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }
}
