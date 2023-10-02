package main.hotel.hotelalura.controller;

import main.hotel.hotelalura.dao.DAOUser;
import main.hotel.hotelalura.model.User;
import main.hotel.hotelalura.factory.ConnectionFactory;

import java.util.List;

public class ControllerUser {

    private final DAOUser userDAO;
	ConnectionFactory factory = new ConnectionFactory();

    public ControllerUser() {
        this.userDAO = new DAOUser(factory.getConnection());
    }

    public boolean login(User user) {
        return userDAO.login(user);
    }

    public void save(User user) {
        userDAO.save(user);
    }

    public List<String> listEmails() {
        return userDAO.listEmails();
    }

    public List<String> listUsers() {
        return userDAO.listUsers();
    }
}
