package dao;

import dto.CouponDTO;
import kosta.kiosk.exception.DMLException;

import java.util.List;

public interface CouponDAO {
    List<CouponDTO> selectCouponByUser(int userId) throws DMLException; //쿠폰조회
    int insertCoupon(CouponDTO couponDTO) throws DMLException; //쿠폰발급
    boolean deleteCoupon(int couponId) throws DMLException; //쿠폰삭제
}
