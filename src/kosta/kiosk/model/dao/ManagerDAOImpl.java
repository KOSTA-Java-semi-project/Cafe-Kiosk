package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.util.DbManager;

public class ManagerDAOImpl implements ManagerDAO {

    /*
     * 관리자 로그인
     */
    @Override
    public ManagerDTO login(
            String id,
            String password
    ) throws SQLException {

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

            try (
                ResultSet rs = ps.executeQuery()
            ) {

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


    /*
     * 특정 기간의 총 매출 조회
     *
     * start 이상
     * end 미만
     */
    @Override
    public int selectTotalSales(
            LocalDateTime start,
            LocalDateTime end
    ) throws SQLException {

        String sql =
                "SELECT COALESCE(SUM(o.sum), 0) AS total_sales "
              + "FROM `order` o "
              + "WHERE o.created_at >= ? "
              + "AND o.created_at < ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setTimestamp(
                    1,
                    Timestamp.valueOf(start)
            );

            ps.setTimestamp(
                    2,
                    Timestamp.valueOf(end)
            );

            try (
                ResultSet rs = ps.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(
                            "total_sales"
                    );
                }
            }
        }

        return 0;
    }
}