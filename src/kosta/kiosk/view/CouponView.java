package kosta.kiosk.view;

import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.controller.CouponController;
import kosta.kiosk.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CouponView {

    private final CouponController couponController = new CouponController();
    private final Scanner sc = new Scanner(System.in);

    /**
     * 결제 화면에서 호출: 보유 쿠폰을 여러 장 반복해서 사용할 수 있게 하고,
     * 최종 할인 적용된 결제 금액을 반환한다.
     * DB 관련 오류 처리는 Controller가 담당한다 (추후 FailView 연동 예정).
     */
    public int applyCoupon(int orderAmount) {
        UserDTO user = Session.getInstance().getUser();
        List<CouponDTO> coupons = new ArrayList<>(couponController.getCouponList(user.getUserId()));

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

            int couponId = 0;
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
            int actualDiscount = Math.min(selected.getPrice(), orderAmount);
            orderAmount = couponController.useCoupon(selected, orderAmount);

            if (orderAmount != beforeAmount) {
                System.out.println(actualDiscount + "원 할인이 적용되었습니다. (결제금액: " + orderAmount + "원)");
                coupons.remove(selected);
            }
            // 실패 시(금액이 그대로일 때)의 오류 메시지는 Controller/FailView가 담당
        }

        return orderAmount;
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
}