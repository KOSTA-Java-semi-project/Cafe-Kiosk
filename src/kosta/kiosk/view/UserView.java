package kosta.kiosk.view;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.controller.UserController;
import kosta.kiosk.session.Session;

import java.util.Scanner;

public class UserView {

    private final UserController userController = new UserController();
    private final Scanner sc = new Scanner(System.in);

    /**
     * 시작 화면에서 호출하는 진입 메서드.
     * 전화번호를 입력받아 로그인을 시도하고, 없는 회원이면 회원가입으로 이어간다.
     * DB 관련 오류 처리는 Controller가 담당한다 (추후 FailView 연동 예정).
     */
    public void start() {
        System.out.print("전화번호를 입력하세요 (- 없이 숫자만): ");
        String phone = sc.nextLine();

        UserDTO user = userController.login(phone);

        if (user == null) {
            user = signUp(phone); // 가입 거절하면 null
        }

        if (user != null) {
            Session.getInstance().login(user);
            System.out.println(user.getName() + "님, 환영합니다! (현재 스탬프: " + user.getStamp() + "개)");
        }
        // user가 계속 null이면 Session은 비어있는 채로 -> 비회원으로 진행
    }

    private UserDTO signUp(String phone) {
        System.out.println("등록되지 않은 회원입니다.");
        System.out.print("회원가입을 하시겠습니까? (1: 예 / 2: 아니오): ");
        String answer = sc.nextLine();

        if (!answer.equals("1")) {
            System.out.println("비회원으로 진행합니다.");
            return null;
        }

        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();

        UserDTO newUser = userController.signUp(name, phone);
        if (newUser != null) {
            System.out.println("회원가입이 완료되었습니다.");
        }
        return newUser;
    }
}