package dao;

import dto.UserDTO;
import kosta.kiosk.exception.DMLException;

public interface UserDAO {
    UserDTO selectUserByPhone(String phone) throws DMLException; //회원조회
    int insertUser(UserDTO userDTO) throws DMLException; //회원추가
    boolean updateUserStamp(int userId, int stamp) throws DMLException; //스탬프갱신


}
