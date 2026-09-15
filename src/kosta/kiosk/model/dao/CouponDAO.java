package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.CouponDTO;
import java.sql.SQLException;
import java.util.List;

public interface CouponDAO {
    List<CouponDTO> selectCouponByUserId(int userId) throws SQLException;
    int insertCoupon(CouponDTO couponDTO) throws SQLException;
    int insertCouponList(List<CouponDTO> coupons) throws SQLException;
    int insertCouponListBatch(List<CouponDTO> coupons) throws SQLException;
    boolean deleteCouponByCouponId(int couponId) throws SQLException;
}