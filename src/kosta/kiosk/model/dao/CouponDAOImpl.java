package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CouponDAOImpl implements CouponDAO {

    @Override
    public List<CouponDTO> selectCouponByUserId(int userId) throws SQLException {
        String sql = "SELECT coupon_id, user_id, price, created_at "
                + "FROM coupon WHERE user_id = ? ORDER BY created_at DESC";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CouponDTO> result = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(new CouponDTO(
                        rs.getInt("coupon_id"),
                        rs.getInt("user_id"),
                        rs.getInt("price"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        } finally {
            DbManager.close(con, pstmt, rs);
        }

        return result;
    }

    @Override
    public int insertCoupon(CouponDTO couponDTO) throws SQLException {
        String sql = "INSERT INTO coupon (user_id, price) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, couponDTO.getUserId());
            pstmt.setInt(2, couponDTO.getPrice());

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
    public boolean deleteCouponByCouponId(int couponId) throws SQLException {
        String sql = "DELETE FROM coupon WHERE coupon_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponId);

            result = pstmt.executeUpdate() > 0;
        } finally {
            DbManager.close(con, pstmt, null);
        }

        return result;
    }
}