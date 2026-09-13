package kosta.kiosk.model.dao;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;

public interface ManagerDAO {

    /*
     * Manager 로그인
     *
     * @param id manager login ID
     * @param password manager password
     * @return ManagerDTO
     *         로그인이 성공하면 ManagerDTO 반환
     *         실패하면 null 반환
     */
    ManagerDTO login(String id, String password) throws SQLException;


    /*
     * 카테고리 전체 조회
     */
    List<Category> categorySelectAll() throws SQLException;


    /*
     * 메뉴 전체 조회
     */
    List<Menu> menuSelectAll() throws SQLException;


    /*
     * 메뉴 ID로 조회
     */
    Menu menuSelectById(int menuId) throws SQLException;


    /*
     * 메뉴 등록
     */
    int menuInsert(Menu menu) throws SQLException;


    /*
     * 메뉴 수정
     */
    int menuUpdate(Menu menu) throws SQLException;


    /*
     * 메뉴 삭제
     */
    void menuDelete(int menuId) throws SQLException;


    /*
     * 메뉴 품절 상태 변경
     */
    int menuSoldoutUpdate(int menuId, boolean soldout) throws SQLException;
}