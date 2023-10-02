package main.hotel.hotelalura.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory implements AutoCloseable {
	
    private Connection connection;

    public Connection getConnection() {
    	
    	String url = "jdbc:mysql://localhost:3306/hotelDB?useTimezone=true&serverTimezone=UTC";
        String user = "userHotel";
        String password = "user12345678";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}