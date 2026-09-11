package kosta.kiosk.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.util.DbManager;

public class ManagerDAOImpl implements ManagerDAO {

    private static ManagerDAO instance = new ManagerDAOImpl();

    private ManagerDAOImpl() {
    }

    public static ManagerDAO getInstance() {
        return instance;
    }

    /**
     * Manager login
     */
    @Override
    public ManagerDTO login(String id, String password) throws SQLException {

        ManagerDTO manager = null;

        String sql =
                "SELECT managerId, name, id, password "
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

                    manager = new ManagerDTO();

                    manager.setManagerId(
                            rs.getInt("managerId"));

                    manager.setName(
                            rs.getString("name"));

                    manager.setId(
                            rs.getString("id"));

                    manager.setPassword(
                            rs.getString("password"));
                }
            }
        }

        return manager;
    }
}