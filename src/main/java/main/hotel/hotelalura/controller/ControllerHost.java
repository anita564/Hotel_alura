package main.hotel.hotelalura.controller;

import main.hotel.hotelalura.dao.HostDAO;
import main.hotel.hotelalura.factory.ConnectionFactory;
import main.hotel.hotelalura.model.Host;

import java.util.List;

public class ControllerHost {

        private final HostDAO hostDAO;
    	ConnectionFactory factory = new ConnectionFactory();


        public ControllerHost() {
            this.hostDAO = new HostDAO(factory.getConnection());
        }

        public void save(Host host) {
            hostDAO.save(host);
        }

        public void update(Host host) {
            hostDAO.update(host);
        }

        public void delete(Integer id) {
            hostDAO.delete(id);
        }

        public List<Host> search(String lastName) {
            return hostDAO.search(lastName);
        }

        public List<Host> list() {
            return hostDAO.list();
        }
}
