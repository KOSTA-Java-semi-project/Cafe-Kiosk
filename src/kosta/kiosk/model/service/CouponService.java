package kosta.kiosk.model.service;

import kosta.kiosk.model.dao.CouponDAO;
import kosta.kiosk.model.dao.CouponDAOImpl;
import kosta.kiosk.model.dto.CouponDTO;

import java.sql.SQLException;
import java.util.List;

public class CouponService {

    private final CouponDAO couponDAO = new CouponDAOImpl();

    public List<CouponDTO> getCouponList(int userId) throws SQLException {
        return couponDAO.selectCouponByUserId(userId);
    }

    public int useCoupon(CouponDTO coupon, int orderAmount) throws SQLException {
        boolean isDeleted = couponDAO.deleteCouponByCouponId(coupon.getCouponId());

        if (!isDeleted) {
            throw new SQLException("이미 사용됐거나 존재하지 않는 쿠폰입니다. couponId=" + coupon.getCouponId());
        }

        return Math.max(orderAmount - coupon.getPrice(), 0);
    }
}