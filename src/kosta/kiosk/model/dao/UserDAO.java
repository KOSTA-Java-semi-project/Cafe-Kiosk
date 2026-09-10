package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.UserDTO;
import java.sql.SQLException;

public interface UserDAO {
    UserDTO selectUserByPhone(String phone) throws SQLException;
    int insertUser(UserDTO userDTO) throws SQLException;
    boolean updateUserStampByUserId(int userId, int stamp) throws SQLException;
}