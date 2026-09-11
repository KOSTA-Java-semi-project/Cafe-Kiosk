package kosta.kiosk.controller;

import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.model.service.CouponService;

import java.sql.SQLException;
import java.util.List;

public class CouponController {

    private final CouponService couponService = new CouponService();

    // 특정 회원의 보유 쿠폰 목록 조회. 오류가 나면 빈 목록 반환.
    public List<CouponDTO> getCouponList(int userId) {
        try {
            return couponService.getCouponList(userId);
        } catch (SQLException e) {
            // TODO FailView 구현되면 여기서 실패 메시지 전달
            System.out.println("쿠폰 조회 중 오류가 발생했습니다.");
            return List.of();
        }
    }

    // 쿠폰 사용(할인 적용 + 사용 처리). 오류가 나면 할인 없이 원래 금액 그대로 반환.
    public int useCoupon(CouponDTO coupon, int orderAmount) {
        try {
            return couponService.useCoupon(coupon, orderAmount);
        } catch (SQLException e) {
            // TODO FailView 구현되면 여기서 실패 메시지 전달
            System.out.println("쿠폰 사용 중 오류가 발생했습니다.");
            return orderAmount;
        }
    }
}