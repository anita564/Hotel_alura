package main.hotel.hotelalura.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.hotel.hotelalura.model.Host;
import main.hotel.hotelalura.model.User;

public class DAOUser {
    Connection connection;

    public DAOUser(Connection connection) {
        this.connection = connection;
    }

    public boolean login(User user) {
        boolean isUserRegister = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE userName = ? AND email = ? AND password = ? ")) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            statement.execute();

            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    isUserRegister = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isUserRegister;
    }

    public void save(User user) {
        try(PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users "
                        + "(name, lastname, email, userName, password)"
                        + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            try (statement) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getUserName());
                statement.setString(5, user.getPassword());

                statement.execute();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        user.setId(resultSet.getInt(1));
                        System.out.printf("user ADDED: %s%n", user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void update(User user)  {
    	 try(PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET name = ?, lastname = ?, email = ?, username = ?, phone_number = ? WHERE id = ?")) {
            try (statement) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getUserName());
                statement.setString(5, user.getPassword());

                statement.execute();
                System.out.printf("user UPDATED: %s%n", user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = ?")) {
            try (statement) {
                statement.setLong(1, id);
                statement.execute();
                System.out.printf("user DELETED: %d%n", id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> listEmails() {
        List<String> emails = new  ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT email FROM users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    emails.add(resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }

    public List<String> listUsers() {
        List<String> users = new  ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT userName FROM users")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(resultSet.getString("userName"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
