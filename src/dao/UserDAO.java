package dao;

import dto.UserDTO;

public interface UserDAO {
    UserDTO userSelectByPhone(String phone) throws DMLException; //회원조회
    int userInsert(UserDTO userDTO) throws DMLException; //회원추가
    boolean userUpdateStamp(int userId, int stamp) throws DMLException; //스탬프갱신
    int userSelectStamp(int userId) throws DMLException; //스탬프조회

}
