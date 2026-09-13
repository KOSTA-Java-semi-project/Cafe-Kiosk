package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.util.DbManager;

public class ManagerDAOImpl implements ManagerDAO {

    /**
     * 관리자 로그인
     *
     * manager 테이블의 id와 password가 일치하는 관리자를 조회한다.
     *
     * 로그인 성공:
     * ManagerDTO 반환
     *
     * 로그인 실패:
     * null 반환
     */
    @Override
    public ManagerDTO login(String id, String password) throws SQLException {

        String sql =
                "SELECT manager_id, name, id, password "
              + "FROM manager "
              + "WHERE id = ? AND password = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, id);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new ManagerDTO(
                            rs.getInt("manager_id"),
                            rs.getString("name"),
                            rs.getString("id"),
                            rs.getString("password")
                    );
                }
            }
        }

        return null;
    }
}