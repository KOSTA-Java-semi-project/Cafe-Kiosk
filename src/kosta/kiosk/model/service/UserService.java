package kosta.kiosk.model.service;

import kosta.kiosk.model.dto.UserDTO;

import java.sql.SQLException;

public interface UserService {
    UserDTO login(String phone) throws SQLException;
    UserDTO signUp(String name, String phone) throws SQLException;
    void processPayment(UserDTO user, int quantity) throws SQLException;
}
