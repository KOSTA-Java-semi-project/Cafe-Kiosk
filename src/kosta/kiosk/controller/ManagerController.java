package kosta.kiosk.controller;

import java.sql.SQLException;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.service.ManagerService;

public class ManagerController {

    private ManagerService managerService;

    public ManagerController() {
        managerService = new ManagerService();
    }

    public ManagerDTO login(String id, String password) {

        try {
            return managerService.login(id, password);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}