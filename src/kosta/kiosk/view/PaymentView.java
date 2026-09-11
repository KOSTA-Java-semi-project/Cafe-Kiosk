package kosta.kiosk.view;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.model.service.UserService;
import kosta.kiosk.model.service.UserServiceImpl;
import kosta.kiosk.session.Session;

import java.sql.SQLException;
import java.util.Scanner;

public class PaymentView {

    private final UserService userService = new UserServiceImpl();
    private final CouponView couponView = new CouponView();
    private final Scanner sc = new Scanner(System.in);

    /**
     * 주문/메뉴 선택이 끝난 뒤 호출하는 결제 진입 메서드. (흐름도 3-1, 3-2)
     * @param orderAmount 쿠폰 적용 전 원래 결제 금액
     * @param quantity    주문한 잔 수 (스탬프 적립 기준)
     */
    public void pay(int orderAmount, int quantity) {
        UserDTO user = Session.getInstance().getUser(); // MainView에서 이미 로그인/비회원 여부가 정해진 상태

        int finalAmount = orderAmount;
        if (user != null) {
            finalAmount = selectCouponOrPay(orderAmount); // 3-2: 쿠폰 사용 / 바로 결제 선택
        }

        System.out.println("\n결제를 진행합니다... 최종 결제 금액: " + finalAmount + "원");
        System.out.println("결제가 완료되었습니다.");

        if (user != null) {
            earnStamp(user, quantity); // 3-2-1: 스탬프 적립 안내
        }

        Session.getInstance().logout(); // 거래 종료 -> 다음 손님을 위해 세션 초기화
    }

    // 3-2: 쿠폰 사용할지, 바로 결제할지 선택
    private int selectCouponOrPay(int orderAmount) {
        while (true) {
            System.out.println("\n1. 쿠폰 사용   2. 결제하기");
            System.out.print("선택: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                return couponView.applyCoupon(orderAmount); // 3-2-2: 쿠폰 while(true) 처리는 CouponView가 담당
            } else if (choice.equals("2")) {
                return orderAmount;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 3-2-1: 결제 완료 후 적립된 스탬프 개수만 간단히 안내
    private void earnStamp(UserDTO user, int quantity) {
        try {
            userService.processPayment(user, quantity);
            System.out.println("스탬프 " + quantity + "개가 적립되었습니다.");
        } catch (SQLException e) {
            System.out.println("스탬프 적립 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}