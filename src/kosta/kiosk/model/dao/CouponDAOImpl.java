package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.exception.DMLException;
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
    public List<CouponDTO> selectCouponByUser(int userId) throws DMLException {
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
            return result;
        } catch (SQLException e) {
            throw new DMLException("쿠폰 조회 실패", e);
        } finally {
            DbManager.close(con, pstmt, rs);
        }
    }

    @Override
    public int insertCoupon(CouponDTO couponDTO) throws DMLException {
        String sql = "INSERT INTO coupon (user_id, price) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, couponDTO.getUserId());
            pstmt.setInt(2, couponDTO.getPrice());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return 0;
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DMLException("쿠폰 발급 실패", e);
        } finally {
            DbManager.close(con, pstmt, rs);
        }
    }

    @Override
    public boolean deleteCoupon(int couponId) throws DMLException {
        String sql = "DELETE FROM coupon WHERE coupon_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, couponId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DMLException("쿠폰 사용 실패", e);
        } finally {
            DbManager.close(con, pstmt, null);
        }
    }
}