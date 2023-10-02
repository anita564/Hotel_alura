package main.hotel.hotelalura.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.hotel.hotelalura.model.Host;

public class HostDAO {

    Connection connection;

    public HostDAO(Connection connection) {
        this.connection = connection;
    }


    public void save(Host host) {
        try (PreparedStatement statement = connection.prepareStatement(
    "INSERT INTO HOSTS"
            + "(name, lastname, birthDay, nationality, phone_number, reservation_id)"
                + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            try (statement) {
                statement.setString(1, host.getName());
                statement.setString(2, host.getLastName());
                statement.setString(3, host.getBirthDate());
                statement.setString(4, host.getNationality());
                statement.setString(5, host.getPhone());
                statement.setInt(6, host.getRerservationId());

                statement.execute();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        host.setId(resultSet.getInt(1));
                        System.out.printf("Host ADDED: %s%n", host);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Host host) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE HOSTS SET name = ?, lastname = ?, birthDay = ?, nationality = ?, phone_number = ? WHERE id = ?")) {
            try (statement) {
                statement.setString(1, host.getName());
                statement.setString(2, host.getLastName());
                statement.setString(3, host.getBirthDate());
                statement.setString(4, host.getNationality());
                statement.setString(5, host.getPhone());
                statement.setInt(6, host.getId());
                statement.execute();
                System.out.printf("host UPDATED: %s%n", host);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM HOSTS WHERE id = ?")) {
            try (statement) {
                statement.setLong(1, id);
                statement.execute();
                System.out.printf("host DELETED: %d%n", id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Host> search(String lastName) {
        List<Host> hosts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM HOSTS WHERE lastname LIKE ?")) {
            try (statement) {
                statement.setString(1, lastName + "%");
                statement.execute();
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        hosts.add(new Host(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("lastname"),
                                resultSet.getString("birthDay"),
                                resultSet.getString("nationality"),
                                resultSet.getString("phone_number"),
                                resultSet.getInt("reservation_id")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hosts;
    }

    public List<Host> list() {
        List<Host> hosts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM hosts")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hosts.add(new Host(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("lastname"),
                            resultSet.getString("birthDay"),
                            resultSet.getString("nationality"),
                            resultSet.getString("phone_number"),
                            resultSet.getInt("reservation_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hosts;
    }
}
