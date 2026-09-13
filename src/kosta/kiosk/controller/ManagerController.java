package kosta.kiosk.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.service.ManagerService;

public class ManagerController {

    private final ManagerService managerService;

    public ManagerController() {
        managerService = new ManagerService();
    }


    // 관리자 로그인
    public ManagerDTO login(String id, String password) {

        try {

            return managerService.login(id, password);

        } catch (SQLException e) {

            System.out.println("관리자 로그인 중 DB 오류가 발생했습니다.");
            System.out.println(e.getMessage());

            return null;
        }
    }


    // 전체 메뉴 조회
    public List<Menu> selectAllMenu() {

        try {

            return managerService.selectAllMenu();

        } catch (SQLException e) {

            System.out.println("메뉴 조회 중 DB 오류가 발생했습니다.");
            System.out.println(e.getMessage());

            return Collections.emptyList();
        }
    }


    // 메뉴 등록
    public boolean insertMenu(Menu menu) {

        try {

            int result = managerService.insertMenu(menu);

            return result > 0;

        } catch (SQLException e) {

            System.out.println("메뉴 등록 중 DB 오류가 발생했습니다.");
            System.out.println(e.getMessage());

            return false;
        }
    }


    // 메뉴 수정
    public boolean updateMenu(Menu menu) {

        try {

            int result = managerService.updateMenu(menu);

            return result > 0;

        } catch (SQLException e) {

            System.out.println("메뉴 수정 중 DB 오류가 발생했습니다.");
            System.out.println(e.getMessage());

            return false;
        }
    }


    // 메뉴 삭제
    public boolean deleteMenu(int menuId) {

        try {

            managerService.deleteMenu(menuId);

            return true;

        } catch (SQLException e) {

            System.out.println("메뉴 삭제 중 DB 오류가 발생했습니다.");
            System.out.println(e.getMessage());

            return false;
        }
    }
}