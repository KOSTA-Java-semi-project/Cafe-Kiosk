package dao;

import dto.CouponDTO;

import java.util.List;

public interface CouponDAO {
    List<CouponDTO> couponSelectByUser(int userId) throws DMLException; //쿠폰조회
    int couponInsert(CouponDTO couponDTO) throws DMLException; //쿠폰발급
    boolean couponDelete(int couponId) throws DMLException; //쿠폰삭제
}
