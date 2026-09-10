package kosta.kiosk.model.dao;

import dto.UserDTO;
import kosta.kiosk.exception.DMLException;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserDTO selectUserByPhone(String phone) throws DMLException {
        String sql = "SELECT user_id, name, phone, stamp FROM `user` WHERE phone = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phone);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return new UserDTO(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("stamp")
                );
            }
            return null; // 없는 회원이면 null
        } catch (SQLException e) {
            throw new DMLException("회원 조회 실패", e);
        } finally {
            DbManager.close(con, pstmt, rs);
        }
    }

    @Override
    public int insertUser(UserDTO userDTO) throws DMLException {
        String sql = "INSERT INTO `user` (name, phone) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userDTO.getName());
            pstmt.setString(2, userDTO.getPhone());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return 0;
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // DB가 자동 생성한 user_id 반환
            }
            return 0;
        } catch (SQLException e) {
            throw new DMLException("회원가입 실패", e);
        } finally {
            DbManager.close(con, pstmt, rs);
        }
    }

    @Override
    public boolean updateUserStamp(int userId, int stamp) throws DMLException {
        String sql = "UPDATE `user` SET stamp = ? WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, stamp);
            pstmt.setInt(2, userId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DMLException("스탬프 갱신 실패", e);
        } finally {
            DbManager.close(con, pstmt, null);
        }
    }
}