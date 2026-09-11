package kosta.kiosk.view;

import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.model.service.UserService;
import kosta.kiosk.model.service.UserServiceImpl;
import kosta.kiosk.session.Session;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {

    private final UserService userService = new UserServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    /**
     * 시작 화면에서 호출하는 진입 메서드.
     * 전화번호를 입력받아 로그인을 시도하고, 없는 회원이면 회원가입으로 이어간다.
     */
    public void start() {
        System.out.print("전화번호를 입력하세요: ");
        String phone = sc.nextLine();

        try {
            UserDTO user = userService.login(phone);

            if (user == null) {
                user = signUp(phone);
            }

            Session.getInstance().login(user);
            System.out.println(user.getName() + "님, 환영합니다! (현재 스탬프: " + user.getStamp() + "개)");
        } catch (SQLException e) {
            System.out.println("회원 정보 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }

    private UserDTO signUp(String phone) throws SQLException {
        System.out.println("등록되지 않은 회원입니다. 회원가입을 진행합니다.");
        System.out.print("이름을 입력하세요: ");
        String name = sc.nextLine();

        UserDTO newUser = userService.signUp(name, phone);
        System.out.println("회원가입이 완료되었습니다.");
        return newUser;
    }
}