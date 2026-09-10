package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.Menu.HotIce;
import kosta.kiosk.util.DbManager;

public class MenuDAOImpl implements MenuDAO {

    // 메뉴 전체 조회
    @Override
    public List<Menu> menuSelectAll() throws SQLException {

        List<Menu> menuList = new ArrayList<>();

        String sql =
                "SELECT menu_id, category_id, menu_name, description, "
                + "price, hot_ice, created_at, soldout "
                + "FROM menu ORDER BY menu_id";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Menu menu = new Menu();

                menu.setMenuId(rs.getInt("menu_id"));
                menu.setCategoryId(rs.getInt("category_id"));
                menu.setMenuName(rs.getString("menu_name"));
                menu.setDescription(rs.getString("description"));
                menu.setPrice(rs.getInt("price"));
                menu.setHotIce(HotIce.valueOf(rs.getString("hot_ice")));

                // DB의 DATETIME을 Java의 LocalDateTime으로 변환
                Timestamp createdAt = rs.getTimestamp("created_at");
                if (createdAt != null) {
                    menu.setCreatedAt(createdAt.toLocalDateTime());
                }

                menu.setSoldout(rs.getBoolean("soldout"));

                menuList.add(menu);
            }
        }

        return menuList;
    }

    // 메뉴 추가: menu_id는 AUTO_INCREMENT라고 가정
    @Override
    public int menuAdd(Menu menu) throws SQLException {

        String sql =
                "INSERT INTO menu "
                + "(category_id, menu_name, description, price, "
                + "hot_ice, created_at, soldout) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 등록일자가 없으면 현재 시간 사용
        LocalDateTime createdAt = menu.getCreatedAt();
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, menu.getCategoryId());
            ps.setString(2, menu.getMenuName());
            ps.setString(3, menu.getDescription());
            ps.setInt(4, menu.getPrice());
            ps.setString(5, menu.getHotIce().name());
            ps.setTimestamp(6, Timestamp.valueOf(createdAt));
            ps.setBoolean(7, menu.isSoldout());

            int result = ps.executeUpdate();

            if (result > 0) {
                menu.setCreatedAt(createdAt);
            }

            return result;
        }
    }

    // 메뉴번호로 메뉴 삭제
    @Override
    public void menuDelete(int menuId) throws SQLException {

        String sql = "DELETE FROM menu WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, menuId);

            int result = ps.executeUpdate();

            if (result == 0) {
                throw new SQLException(
                        "삭제할 메뉴가 없습니다. 메뉴번호: " + menuId
                );
            }
        }
    }

    // 메뉴번호로 메뉴 정보 수정
    @Override
    public int menuCorrection(Menu menu) throws SQLException {

        String sql =
                "UPDATE menu "
                + "SET category_id = ?, menu_name = ?, description = ?, "
                + "price = ?, hot_ice = ?, soldout = ? "
                + "WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, menu.getCategoryId());
            ps.setString(2, menu.getMenuName());
            ps.setString(3, menu.getDescription());
            ps.setInt(4, menu.getPrice());
            ps.setString(5, menu.getHotIce().name());
            ps.setBoolean(6, menu.isSoldout());
            ps.setInt(7, menu.getMenuId());

            return ps.executeUpdate();
        }
    }
}