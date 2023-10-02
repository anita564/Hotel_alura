package main.hotel.hotelalura.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.hotel.hotelalura.model.Booking;

public class DAOBooking {
    Connection connection;

    public DAOBooking(Connection connection) {
        this.connection = connection;
    }

    public void save(Booking booking) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO BOOKINGS "
                        + "(checkInDate, checkOutDate, priceValue, paymentMethod) "
                        + "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            try (statement) {
                statement.setString(1, booking.getCheckInDate());
                statement.setString(2, booking.getCheckOutDate());
                statement.setDouble(3, booking.getPriceValue());
                statement.setString(4, booking.getPaymentMethod());

                statement.execute();

                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    while (resultSet.next()) {
                        booking.setId(resultSet.getInt(1));
                        System.out.printf("Booking added: %s%n", booking);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Booking booking) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE BOOKINGS SET checkInDate = ?, checkOutDate = ?, priceValue = ?, paymentMethod = ? WHERE id = ?")) {
            try (statement) {
                statement.setString(1, booking.getCheckInDate());
                statement.setString(2, booking.getCheckOutDate());
                statement.setDouble(3, booking.getPriceValue());
                statement.setString(4, booking.getPaymentMethod());
                statement.setInt(5, booking.getId());
                statement.execute();
                System.out.printf("booking UPDATED: %s%n", booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM BOOKINGS WHERE id = ?")) {
            try (statement) {
                statement.setLong(1, id);
                statement.execute();
                System.out.printf("booking DELETED WITH id: %d%n", id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> search(String id) {
        List<Booking> reservations = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM BOOKINGS WHERE id LIKE ?")) {
            try (statement) {
                statement.setString(1, id + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        reservations.add(new Booking(
                                resultSet.getInt("id"),
                                resultSet.getString("checkInDate"),
                                resultSet.getString("checkOutDate"),
                                resultSet.getDouble("priceValue"),
                                resultSet.getString("paymentMethod")
                        ));
                    }
                }
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return reservations;
    }

    public List<Booking> list() {
        List<Booking> reservations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reservations.add(new Booking(
                            resultSet.getInt("id"),
                            resultSet.getString("checkInDate"),
                            resultSet.getString("checkOutDate"),
                            resultSet.getDouble("priceValue"),
                            resultSet.getString("paymentMethod")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservations;
    }


    public int getLastReservaId() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT MAX(id) FROM BOOKINGS")) {
            try (statement) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean reservationHasHost(Integer idReserva) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT COUNT(*) FROM huespedes WHERE reservation_id = ?")) {
            try (statement) {
                statement.setInt(1, idReserva);

                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
