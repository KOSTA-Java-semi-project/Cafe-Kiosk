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
    public List<Order> selectOrderByUserId(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Order> list = new ArrayList<>();

        String sql = "select * from `order` where user_id = ?";
        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("order_id"), rs.getInt("user_id"), rs.getInt("sum"), rs.getTimestamp("created_at").toLocalDateTime()));
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
        ResultSet rs= null;
        String sql = "insert into `order` (user_id, sum, created_at) values (?, ?, now())";
        int result;
        try {
            con = DbManager.getConnection();
            con.setAutoCommit(false);

            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getSum());
            result = stmt.executeUpdate();
            if (result == 0) {
                throw new SQLException("주문 등록 실패");
            }
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }
            int[] detailResults = insertOrderDetails(con, order, order.getOrderId());
            for (int r : detailResults) {
                if (r != 1) {
                    throw new SQLException("주문 상세 등록 실패");
                }
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
            }
            DbManager.close(con, stmt, rs);
        }
        return result;
    }



    @Override
    public Order selectOrderByOrderId(int orderId) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;

        String sql = "select * from `order` where order_id = ? ";
        try {
            con = DbManager.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                order = new Order(rs.getInt("order_id"),
                        rs.getInt("user_id"), rs.getInt("sum"),
                        rs.getTimestamp("created_at").toLocalDateTime());
                List<OrderDetail> orderLineList = this.selectOrderDetailsByOrderId(order.getOrderId());
                order.setOrderDetailList(orderLineList);
            }

        } finally {
            DbManager.close(con, stmt, rs);
        }
        return order;
    }

    @Override
    public List<OrderDetail> selectOrderDetailsByOrderId(int orderId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderDetail> list = new ArrayList<>();
        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement("select * from order_detail where  order_id=?");
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                String iceCode = rs.getString("ice");
                IceLevel ice = (iceCode == null) ? null : IceLevel.fromCode(iceCode);

                OrderDetail orderDetail = new OrderDetail(rs.getInt("detail_id"),
                        rs.getInt("order_id"), rs.getInt("menu_id"),
                        rs.getInt("amount"),
                        Size.fromValue(rs.getString("size")),
                        rs.getInt("shot"),
                        ice,
                        rs.getInt("syrup"));
                list.add(orderDetail);
            }
        } finally {
            DbManager.close(con, ps, rs);
        }
        return list;

    }


    private int[] insertOrderDetails(Connection con, Order order, int orderId) throws SQLException {
        PreparedStatement stmt = null;
        String sql = "insert into order_detail (order_id, menu_id, amount, size, shot, ice, syrup) values (?, ?, ?, ?, ?, ?, ?)";
        int[] result;

        try {
            stmt = con.prepareStatement(sql);

            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, orderDetail.getMenuId());
                stmt.setInt(3, orderDetail.getAmount());
                stmt.setString(4, orderDetail.getSize().getCode());
                stmt.setInt(5, orderDetail.getShot());

                if (orderDetail.getIce() != null) {
                    stmt.setString(6, orderDetail.getIce().getCode());
                } else {
                    stmt.setNull(6, Types.VARCHAR);
                }

                stmt.setInt(7, orderDetail.getSyrup());

                stmt.addBatch();
                stmt.clearParameters();
            }

            result = stmt.executeBatch();
        } finally {
            DbManager.close(null, stmt, null);
        }
        return result;
    }
}