package ru.kpfu.itis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnectionUtil {
    private DatabaseConnectionUtil() {}
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "123456"
                );
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
