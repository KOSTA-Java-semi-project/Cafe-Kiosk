package kosta.kiosk.model.dao;

import java.sql.SQLException;
import java.util.List;

import kosta.kiosk.model.dto.Category;

public interface CategoryDAO {
    List<Category> selectAllCategory() throws SQLException;
    Category selectCategoryBycategoryId(int categoryId) throws SQLException;
}