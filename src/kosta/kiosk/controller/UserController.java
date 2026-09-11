package kosta.kiosk.controller;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.model.service.UserService;

import java.sql.SQLException;

public class UserController {

    private final UserService userService = new UserService();

    // 회원 조회(전화번호). 가입 안 된 번호면 null, DB 오류가 나도 null.
    public UserDTO login(String phone) {
        try {
            return userService.login(phone);
        } catch (SQLException e) {
            // TODO FailView 구현되면 여기서 실패 메시지 전달
            System.out.println("로그인 처리 중 오류가 발생했습니다.");
            return null;
        }
    }

    // 회원가입. 성공하면 생성된 회원 정보, 실패하면 null.
    public UserDTO signUp(String name, String phone) {
        try {
            return userService.signUp(name, phone);
        } catch (SQLException e) {
            // TODO FailView 구현되면 여기서 실패 메시지 전달
            System.out.println("회원가입 처리 중 오류가 발생했습니다.");
            return null;
        }
    }

    // 결제 처리(스탬프 적립 + 조건 충족 시 쿠폰 자동 발급).
    public void processPayment(UserDTO user, int quantity) {
        try {
            userService.processPayment(user, quantity);
        } catch (SQLException e) {
            // TODO FailView 구현되면 여기서 실패 메시지 전달
            System.out.println("스탬프 적립 중 오류가 발생했습니다.");
        }
    }
}