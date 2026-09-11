package kosta.kiosk.view;

import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.model.service.CouponService;
import kosta.kiosk.model.service.CouponServiceImpl;
import kosta.kiosk.session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CouponView {

    private final CouponService couponService = new CouponServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    public int applyCoupon(int orderAmount) {
        UserDTO user = Session.getInstance().getUser();
        List<CouponDTO> coupons = new ArrayList<>(getCouponList(user.getUserId()));

        while (true) {
            if (orderAmount <= 0) {
                System.out.println("이미 결제 금액이 0원이 되어 더 이상 쿠폰을 사용할 수 없습니다.");
                break;
            }

            if (coupons.isEmpty()) {
                System.out.println("사용 가능한 쿠폰이 없습니다.");
                break;
            }

            printCoupons(coupons);
            System.out.println("현재 결제 예정 금액: " + orderAmount + "원");
            System.out.print("사용할 쿠폰번호를 입력하세요 (그만 사용: 0): ");

            int couponId;
            try {
                couponId = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
                continue;
            }

            if (couponId == 0) {
                break;
            }

            CouponDTO selected = findCouponById(coupons, couponId);

            if (selected == null) {
                System.out.println("존재하지 않는 쿠폰번호입니다. 다시 입력해주세요.");
                continue;
            }

            int beforeAmount = orderAmount;
            orderAmount = useCoupon(selected, orderAmount);

            if (orderAmount != beforeAmount) {
                coupons.remove(selected);
            }
        }

        return orderAmount;
    }

    private List<CouponDTO> getCouponList(int userId) {
        try {
            return couponService.getCouponList(userId);
        } catch (SQLException e) {
            System.out.println("쿠폰 조회 중 오류가 발생했습니다: " + e.getMessage());
            return List.of();
        }
    }

    private void printCoupons(List<CouponDTO> coupons) {
        System.out.println("\n보유 쿠폰 목록");
        for (CouponDTO c : coupons) {
            System.out.println("- 쿠폰번호: " + c.getCouponId() + " / 할인금액: " + c.getPrice() + "원");
        }
    }

    private CouponDTO findCouponById(List<CouponDTO> coupons, int couponId) {
        for (CouponDTO c : coupons) {
            if (c.getCouponId() == couponId) {
                return c;
            }
        }
        return null;
    }

    private int useCoupon(CouponDTO coupon, int orderAmount) {
        try {
            int actualDiscount = Math.min(coupon.getPrice(), orderAmount);
            int discountedAmount = couponService.useCoupon(coupon, orderAmount);
            System.out.println(actualDiscount + "원 할인이 적용되었습니다. (결제금액: " + discountedAmount + "원)");
            return discountedAmount;
        } catch (SQLException e) {
            System.out.println("쿠폰 사용 중 오류가 발생했습니다: " + e.getMessage());
            return orderAmount;
        }
    }
}