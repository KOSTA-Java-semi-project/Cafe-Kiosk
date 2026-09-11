package kosta.kiosk.model.dao;

import java.sql.SQLException;

public interface CategoryDAO {
    List<Category> selectAllCategory() throws SQLException;
    Category selectCategoryById(int categoryId) throws SQLException;

}
