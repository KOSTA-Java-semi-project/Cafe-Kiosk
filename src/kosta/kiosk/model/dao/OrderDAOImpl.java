package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.IceLevel;
import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.Size;
import kosta.kiosk.util.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public List<Order> orderSelectByUser(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Order> list = new ArrayList<>();
        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(
                    "SELECT order_id, user_id, sum, created_at FROM `order` WHERE user_id = ? ORDER BY order_id DESC");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Integer uid = rs.getObject("user_id") != null ? rs.getInt("user_id") : null;

                Order order = new Order(
                        rs.getInt("order_id"),
                        uid,
                        rs.getInt("sum"),
                        null,
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                order.setOrderDetailList(selectOrderDetails(order.getOrderId()));
                list.add(order);
            }
        } finally {
            DbManager.close(con, stmt, rs);
        }
        return list;
    }

    @Override
    public int insertOrder(Order order) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        String sql = "INSERT INTO `order` (user_id, sum, created_at) VALUES (?, ?, NOW())";

        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (order.getUserId() == null) {
                stmt.setNull(1, Types.INTEGER);
            } else {
                stmt.setInt(1, order.getUserId());
            }
            stmt.setInt(2, order.getSum());

            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            int generatedOrderId = -1;
            if (generatedKeys.next()) {
                generatedOrderId = generatedKeys.getInt(1);
            }


            if (order.getOrderDetailList() != null) {
                for (OrderDetail detail : order.getOrderDetailList()) {
                    detail.setOrderId(generatedOrderId);
                    insertOrderDetail(con, detail);
                }
            }

            return generatedOrderId;

        } finally {
            DbManager.close(con, stmt, generatedKeys);
        }
    }

    private void insertOrderDetail(Connection con, OrderDetail detail) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, menu_id, amount, size, shot, ice, syrup) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, detail.getOrderId());
            stmt.setInt(2, detail.getMenuId());
            stmt.setInt(3, detail.getAmount());
            stmt.setString(4, detail.getSize().name());   // enum → String
            stmt.setInt(5, detail.getShot());
            stmt.setString(6, detail.getIce().name());    // enum → String
            stmt.setInt(7, detail.getSyrup());
            stmt.executeUpdate();
        }
    }

    @Override
    public Order selectOrderByOrderId(int orderId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;

        String sql = "SELECT order_id, user_id, sum, created_at FROM `order` WHERE order_id = ?";

        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Integer uid = rs.getObject("user_id") != null ? rs.getInt("user_id") : null;

                order = new Order(
                        rs.getInt("order_id"),
                        uid,
                        rs.getInt("sum"),
                        null,
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                order.setOrderDetailList(selectOrderDetails(order.getOrderId()));
            }
        } finally {
            DbManager.close(con, stmt, rs);
        }
        return order;
    }

    @Override
    public List<OrderDetail> selectOrderDetails(int orderId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<OrderDetail> list = new ArrayList<>();

        String sql = "SELECT detail_id, order_id, menu_id, amount, size, shot, ice, syrup "
                + "FROM order_details WHERE order_id = ?";

        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OrderDetail detail = new OrderDetail(
                        rs.getInt("detail_id"),
                        rs.getInt("order_id"),
                        rs.getInt("menu_id"),
                        rs.getInt("amount"),
                        Size.valueOf(rs.getString("size")),
                        rs.getInt("shot"),
                        IceLevel.valueOf(rs.getString("ice")),
                        rs.getInt("syrup")
                );
                list.add(detail);
            }
        } finally {
            DbManager.close(con, stmt, rs);
        }
        return list;
    }
}