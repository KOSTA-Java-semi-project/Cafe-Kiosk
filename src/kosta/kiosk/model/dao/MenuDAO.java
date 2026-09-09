package kosta.kiosk.model.dao;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dto.Menu;

public interface MenuDAO {

    // 메뉴 전체 조회
    List<Menu> menuSelectAll() throws SQLException;

    // 메뉴 추가
    int menuAdd(Menu menu) throws SQLException;

    // 메뉴번호로 메뉴 삭제
    int menuDelete(int menuId) throws SQLException;

    // 메뉴번호로 메뉴 정보 수정
    int menuCorrection(Menu menu) throws SQLException;
}