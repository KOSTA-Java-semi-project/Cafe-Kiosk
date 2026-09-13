package kosta.kiosk.model.service;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dao.ManagerDAO;
import kosta.kiosk.model.dao.ManagerDAOImpl;
import kosta.kiosk.model.dao.MenuDAO;
import kosta.kiosk.model.dao.MenuDAOImpl;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;

public class ManagerService {

    private final ManagerDAO managerDAO;
    private final MenuDAO menuDAO;

    public ManagerService() {
        managerDAO = new ManagerDAOImpl();
        menuDAO = new MenuDAOImpl();
    }

    // 관리자 로그인
    public ManagerDTO login(String id, String password) throws SQLException {

        if (id == null || id.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return managerDAO.login(id.trim(), password);
    }


    // 전체 메뉴 조회
    public List<Menu> selectAllMenu() throws SQLException {
        return menuDAO.selectAllMenu();
    }


    // 메뉴 등록
    public int insertMenu(Menu menu) throws SQLException {
        return menuDAO.insertMenu(menu);
    }


    // 메뉴 수정
    public int updateMenu(Menu menu) throws SQLException {
        return menuDAO.updateMenuById(menu);
    }


    // 메뉴 삭제
    public void deleteMenu(int menuId) throws SQLException {
        menuDAO.deleteMenuById(menuId);
    }
}