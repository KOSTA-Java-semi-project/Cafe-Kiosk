package kosta.kiosk.model.service;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dao.MenuDAO;
import kosta.kiosk.model.dao.MenuDAOImpl;
import kosta.kiosk.model.dto.Menu;

public class MenuService {
	
	private MenuDAO menuDAO = new MenuDAOImpl();
	
	/*
	 * 메뉴 전체 조회
	 */
	
	public List<Menu> menuSelectAll() throws SQLException {
		return menuDAO.menuSelectAll();
	}
	
	/*
	 * 메뉴 추가
	 */
	
	public int menuAdd(Menu menu) throws SQLException {
		return menuDAO.menuAdd(menu);
	}
	
	/*
	 * 메뉴 번호로 메뉴 삭제
	 */
	
	public void menuDelete(int menuId) throws SQLException {
		menuDAO.menuDelete(menuId);
	}
	
	/*
	 * 메뉴 정보 수정
	 */
	
	public int menuCorrection(Menu menu) throws SQLException {
		return menuDAO.menuCorrection(menu);
	}
}
