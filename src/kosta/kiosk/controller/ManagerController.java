package kosta.kiosk.controller;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.service.ManagerService;

public class ManagerController {

    private ManagerService managerService;

    public ManagerController() {
        managerService = new ManagerService();
    }


    /**
     * 관리자 로그인
     */
    public ManagerDTO login(String id, String password) {

        try {

            return managerService.login(id, password);

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }


    /**
     * 전체 메뉴 조회
     */
    public List<Menu> selectAllMenu() {

        try {

            return managerService.selectAllMenu();

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }


    /**
     * 카테고리별 메뉴 조회
     */
    public List<Menu> selectMenuListByCategoryId(int categoryId) {

        try {

            return managerService.selectMenuListByCategoryId(categoryId);

        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }


    /**
     * 메뉴 추가
     */
    public int insertMenu(Menu menu) {

        try {

            return managerService.insertMenu(menu);

        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 메뉴 수정
     */
    public int updateMenuById(Menu menu) {

        try {

            return managerService.updateMenuById(menu);

        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 메뉴 삭제
     */
    public void deleteMenuById(int menuId) {

        try {

            managerService.deleteMenuById(menuId);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}