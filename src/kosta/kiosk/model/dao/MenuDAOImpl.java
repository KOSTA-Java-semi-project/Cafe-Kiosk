package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.Menu.HotIce;
import kosta.kiosk.util.DbManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MenuDAOImpl implements MenuDAO {

    // 메뉴 전체 조회
    @Override
    public List<Menu> selectAllMenu() throws SQLException {

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

    // 메뉴 추가
    @Override
    public int insertMenu(Menu menu) throws SQLException {

        String sql =
                "INSERT INTO menu "
                + "(category_id, menu_name, description, price, "
                + "hot_ice, created_at, soldout) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
    public void deleteMenuById(int menuId) throws SQLException {

        String sql = "DELETE FROM menu WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, menuId);

            ps.executeUpdate();
        }
    }

    // 메뉴번호로 메뉴 수정
    @Override
    public int updateMenuById(Menu menu) throws SQLException {

        String sql =
                "UPDATE menu "
                + "SET category_id = ?, "
                + "menu_name = ?, "
                + "description = ?, "
                + "price = ?, "
                + "hot_ice = ?, "
                + "soldout = ? "
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