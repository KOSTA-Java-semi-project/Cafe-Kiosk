package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {


    @Override
    public List<Category> selectAllCategory() throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM category ORDER BY category_id";
        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id0"));
                category.setCategoryName(rs.getString("category_name"));
                list.add(category);

            }

        } finally {
            DbManager.close(con, stmt, rs);
        }
        return list;
    }

    @Override
    public Category selectCategoryBycategoryId(int categoryId) throws SQLException {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Category category = null;


        String sql = "select * from category where category_id = ? ";
        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, categoryId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                category = new Category(rs.getInt("category_id"),
                        rs.getString("category_name"));
            }

        } finally {
            DbManager.close(con, stmt, rs);
        }
        return category;
    }
}