package ru.kpfu.itis;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    private static final Connection conn = DatabaseConnectionUtil.getConnection();

    public static boolean validateUser(String login, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        String hashedPassword = null;

        try {
             PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    hashedPassword = rs.getString("password");
                }
            }

            return hashedPassword != null && BCrypt.checkpw(password, hashedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addUser (String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static void logAuthAttempt(String username, boolean success) {
        String sql = "INSERT INTO auth(username, success) VALUES (?, ?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setBoolean(2, success);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}