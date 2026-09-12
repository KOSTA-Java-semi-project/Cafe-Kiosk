package kosta.kiosk.model.service;

import java.sql.SQLException;

import kosta.kiosk.model.dao.ManagerDAO;
import kosta.kiosk.model.dao.ManagerDAOImpl;
import kosta.kiosk.model.dto.ManagerDTO;

public class ManagerService {

    private ManagerDAO managerDAO;

    public ManagerService() {
        managerDAO = new ManagerDAOImpl();
    }

    public ManagerDTO login(String id, String password) throws SQLException {
        return managerDAO.login(id, password);
    }
}