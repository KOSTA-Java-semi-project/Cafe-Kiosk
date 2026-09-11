package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.util.DbManager;

public class ManagerDAOImpl implements ManagerDAO {

    @Override
    public ManagerDTO login(String id, String password) throws SQLException {

        ManagerDTO manager = null;

        String sql =
                "SELECT manager_id, name, id, password "
              + "FROM manager "
              + "WHERE id = ? AND password = ?";

        try (
            Connection con = DbManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, id);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    manager = new ManagerDTO(
                            rs.getInt("manager_id"),
                            rs.getString("name"),
                            rs.getString("id"),
                            rs.getString("password")
                    );
                }
            }
        }

        return manager;
    }
}