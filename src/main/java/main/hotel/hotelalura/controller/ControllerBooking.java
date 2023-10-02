package main.hotel.hotelalura.controller;

import main.hotel.hotelalura.dao.DAOBooking;
import main.hotel.hotelalura.factory.ConnectionFactory;
import main.hotel.hotelalura.model.Booking;

import java.util.List;

public class ControllerBooking {

    private final DAOBooking daoBooking;
	ConnectionFactory factory = new ConnectionFactory();

    public ControllerBooking() {
        this.daoBooking = new DAOBooking(factory.getConnection());
    }

    public void save(Booking booking) {
        daoBooking.save(booking);
    }

    public void update(Booking booking) {
        daoBooking.update(booking);
    }

    public void delete(Integer id) {
        daoBooking.delete(id);
    }

    public List<Booking> search(String id) {
        return daoBooking.search(id);
    }

    public List<Booking> list() {
        return daoBooking.list();
    }

    public int getLastReservaId() {
        return daoBooking.getLastReservaId();
    }

    public boolean reservationHasHost(Integer id) {
        return daoBooking.reservationHasHost(id);
    }

}
