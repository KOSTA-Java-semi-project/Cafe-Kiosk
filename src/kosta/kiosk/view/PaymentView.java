package kosta.kiosk.view;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.controller.UserController;
import kosta.kiosk.controller.UserControllerImpl;
import kosta.kiosk.session.Session;

import java.util.Scanner;

public class PaymentView {

    private final UserController userController = new UserControllerImpl();
    private final CouponView couponView = new CouponView();
    private final Scanner sc = new Scanner(System.in);

    /**
     * 주문/메뉴 선택이 끝난 뒤 호출하는 결제 진입 메서드. (흐름도 3-1, 3-2)
     * DB 관련 오류 처리는 Controller가 담당한다 (추후 FailView 연동 예정).
     * @param orderAmount 쿠폰 적용 전 원래 결제 금액
     * @param quantity    주문한 잔 수 (스탬프 적립 기준)
     */
    public void pay(int orderAmount, int quantity) {
        UserDTO user = Session.getInstance().getUser(); // MainView에서 이미 로그인/비회원 여부가 정해진 상태

        int finalAmount = orderAmount;
        if (user != null) {
            finalAmount = selectCouponOrPay(orderAmount); // 3-2: 쿠폰 사용 / 바로 결제 선택
        }

        // TODO 주문 데이터 저장 로직 수행 (Session의 cart 내용을 DB에 저장, Order 담당 Controller 구현 필요)

        System.out.println("\n결제를 진행합니다... 최종 결제 금액: " + finalAmount + "원");
        System.out.println("결제가 완료되었습니다.");

        if (user != null) {
            userController.processPayment(user, quantity); // 3-2-1: 스탬프 적립
            System.out.println("스탬프 " + quantity + "개가 적립되었습니다.");
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
}