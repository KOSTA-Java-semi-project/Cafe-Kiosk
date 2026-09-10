package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserDTO selectUserByPhone(String phone) throws SQLException {
        String sql = "SELECT user_id, name, phone, stamp FROM `user` WHERE phone = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        UserDTO result = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, phone);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = new UserDTO(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("stamp")
                );
            }
        } finally {
            DbManager.close(con, pstmt, rs);
        }

        return result;
    }

    @Override
    public int insertUser(UserDTO userDTO) throws SQLException {
        String sql = "INSERT INTO `user` (name, phone) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, userDTO.getName());
            pstmt.setString(2, userDTO.getPhone());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } finally {
            DbManager.close(con, pstmt, rs);
        }

        return result;
    }

    @Override
    public boolean updateUserStampByUserId(int userId, int stamp) throws SQLException {
        String sql = "UPDATE `user` SET stamp = ? WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, stamp);
            pstmt.setInt(2, userId);

            result = pstmt.executeUpdate() > 0;
        } finally {
            DbManager.close(con, pstmt, null);
        }

        return result;
    }
}