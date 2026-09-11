package kosta.kiosk.model.service;

import kosta.kiosk.model.dto.CouponDTO;

import java.sql.SQLException;
import java.util.List;

public interface CouponService {
    List<CouponDTO> getCouponList(int userId) throws SQLException;
    int useCoupon(CouponDTO coupon, int orderAmount) throws SQLException;
}
