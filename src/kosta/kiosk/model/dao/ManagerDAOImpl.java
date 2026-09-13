package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.HotIce;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.util.DbManager;

public class ManagerDAOImpl implements ManagerDAO {

    /*
     * 관리자 로그인
     */
    @Override
    public ManagerDTO login(String id, String password)
            throws SQLException {

        String sql =
                "SELECT manager_id, name, login_id, password "
              + "FROM manager "
              + "WHERE login_id = ? AND password = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, id);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new ManagerDTO(
                            rs.getInt("manager_id"),
                            rs.getString("name"),
                            rs.getString("login_id"),
                            rs.getString("password")
                    );
                }
            }
        }

        return null;
    }


    /*
     * 카테고리 전체 조회
     */
    @Override
    public List<Category> categorySelectAll()
            throws SQLException {

        List<Category> categoryList = new ArrayList<>();

        String sql =
                "SELECT category_id, category_name "
              + "FROM category "
              + "ORDER BY category_id";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );

                categoryList.add(category);
            }
        }

        return categoryList;
    }


    /*
     * 메뉴 전체 조회
     */
    @Override
    public List<Menu> menuSelectAll()
            throws SQLException {

        List<Menu> menuList = new ArrayList<>();

        String sql =
                "SELECT menu_id, category_id, menu_name, description, "
              + "price, hot_ice, created_at, soldout "
              + "FROM menu "
              + "ORDER BY menu_id";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Menu menu = new Menu();

                menu.setMenuId(
                        rs.getInt("menu_id")
                );

                menu.setCategoryId(
                        rs.getInt("category_id")
                );

                menu.setMenuName(
                        rs.getString("menu_name")
                );

                menu.setDescription(
                        rs.getString("description")
                );

                menu.setPrice(
                        rs.getInt("price")
                );

                String hotIce = rs.getString("hot_ice");

                if (hotIce != null) {
                    menu.setHotIce(
                            HotIce.valueOf(hotIce)
                    );
                }

                if (rs.getTimestamp("created_at") != null) {

                    menu.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );
                }

                menu.setSoldout(
                        rs.getBoolean("soldout")
                );

                menuList.add(menu);
            }
        }

        return menuList;
    }


    /*
     * 메뉴 ID로 조회
     */
    @Override
    public Menu menuSelectById(int menuId)
            throws SQLException {

        String sql =
                "SELECT menu_id, category_id, menu_name, description, "
              + "price, hot_ice, created_at, soldout "
              + "FROM menu "
              + "WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, menuId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Menu menu = new Menu();

                    menu.setMenuId(
                            rs.getInt("menu_id")
                    );

                    menu.setCategoryId(
                            rs.getInt("category_id")
                    );

                    menu.setMenuName(
                            rs.getString("menu_name")
                    );

                    menu.setDescription(
                            rs.getString("description")
                    );

                    menu.setPrice(
                            rs.getInt("price")
                    );

                    String hotIce =
                            rs.getString("hot_ice");

                    if (hotIce != null) {

                        menu.setHotIce(
                                HotIce.valueOf(hotIce)
                        );
                    }

                    if (rs.getTimestamp("created_at") != null) {

                        menu.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );
                    }

                    menu.setSoldout(
                            rs.getBoolean("soldout")
                    );

                    return menu;
                }
            }
        }

        return null;
    }


    /*
     * 메뉴 등록
     */
    @Override
    public int menuInsert(Menu menu)
            throws SQLException {

        String sql =
                "INSERT INTO menu "
              + "(category_id, menu_name, description, "
              + "price, hot_ice, soldout) "
              + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    menu.getCategoryId()
            );

            ps.setString(
                    2,
                    menu.getMenuName()
            );

            ps.setString(
                    3,
                    menu.getDescription()
            );

            ps.setInt(
                    4,
                    menu.getPrice()
            );

            if (menu.getHotIce() != null) {

                ps.setString(
                        5,
                        menu.getHotIce().name()
                );

            } else {

                ps.setNull(
                        5,
                        Types.VARCHAR
                );
            }

            ps.setBoolean(
                    6,
                    menu.isSoldout()
            );

            return ps.executeUpdate();
        }
    }


    /*
     * 메뉴 수정
     */
    @Override
    public int menuUpdate(Menu menu)
            throws SQLException {

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

            ps.setInt(
                    1,
                    menu.getCategoryId()
            );

            ps.setString(
                    2,
                    menu.getMenuName()
            );

            ps.setString(
                    3,
                    menu.getDescription()
            );

            ps.setInt(
                    4,
                    menu.getPrice()
            );

            if (menu.getHotIce() != null) {

                ps.setString(
                        5,
                        menu.getHotIce().name()
                );

            } else {

                ps.setNull(
                        5,
                        Types.VARCHAR
                );
            }

            ps.setBoolean(
                    6,
                    menu.isSoldout()
            );

            ps.setInt(
                    7,
                    menu.getMenuId()
            );

            return ps.executeUpdate();
        }
    }


    /*
     * 메뉴 삭제
     */
    @Override
    public void menuDelete(int menuId)
            throws SQLException {

        String sql =
                "DELETE FROM menu "
              + "WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    menuId
            );

            ps.executeUpdate();
        }
    }


    /*
     * 메뉴 품절 상태 변경
     */
    @Override
    public int menuSoldoutUpdate(
            int menuId,
            boolean soldout)
            throws SQLException {

        String sql =
                "UPDATE menu "
              + "SET soldout = ? "
              + "WHERE menu_id = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setBoolean(
                    1,
                    soldout
            );

            ps.setInt(
                    2,
                    menuId
            );

            return ps.executeUpdate();
        }
    }
}