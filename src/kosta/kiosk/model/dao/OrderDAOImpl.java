package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.IceLevel;
import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.Size;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {


    @Override
    public List<Order> selectOrderByUserId(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Order> list = new ArrayList<>();

        String sql = "select * from `order` where user_id = ?";
        try {
            conn = DbManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Order(rs.getInt("order_id"), rs.getInt("user_id"), rs.getInt("sum"), rs.getTimestamp("created_at").toLocalDateTime()));
            }

        } finally {
            DbManager.close(conn, stmt, rs);
        }
        return list;
    }

    @Override
    public int insertOrder(Order order) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sql = "insert into `order` (order_id, user_id, sum, created_at) values (?, ?, ?, now())";
        int result;
        try {
            conn = DbManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setInt(3, order.getSum());
            result = stmt.executeUpdate();

        } finally {
            DbManager.close(conn, stmt, null);
        }
        return result;
    }

    @Override
    public Order selectOrderByOrderId(int orderId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Order order = null;

        String sql = "select * from `order` where order_id = ? ";
        try {
            conn = DbManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                order = new Order(rs.getInt("order_id"), rs.getInt("user_id"), rs.getInt("sum"), rs.getTimestamp("created_at").toLocalDateTime());
                List<OrderDetail> orderLineList = this.selectOrderDetails(order.getOrderId());//메소드 호출
                order.setOrderDetailList(orderLineList);

            }

        } finally {
            DbManager.close(conn, stmt, rs);
        }
        return order;
    }

    @Override
    public List<OrderDetail> selectOrderDetails(int orderId) throws SQLException {
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
                OrderDetail orderDetail = new OrderDetail(rs.getInt("detail_id"),
                        rs.getInt("order_id"), rs.getInt("menu_id"),
                        rs.getInt("amount"),
                        Size.fromValue(rs.getString("size")),
                        rs.getInt("shot"),
                        IceLevel.fromCode(rs.getString("ice")),
                        rs.getInt("syrup"));
                list.add(orderDetail);
            }
        } finally {
            DbManager.close(con, ps, rs);
        }
        return list;

    }

    @Override
    public int insertOrderDetails(OrderDetail orderDetail) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        String sql = "insert into order_detail (order_id, menu_id, amount, size, shot, ice, syrup) values (?, ?, ?, ?, ?, ?, ?)";
        int result;
        try {
            conn = DbManager.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getMenuId());
            stmt.setInt(3, orderDetail.getAmount());
            stmt.setString(4, orderDetail.getSize().getCode());
            stmt.setInt(5, orderDetail.getShot());
            stmt.setString(6, orderDetail.getIce().getCode());
            stmt.setInt(7, orderDetail.getSyrup());
            result = stmt.executeUpdate();
        } finally {
            DbManager.close(conn, stmt, null);
        }
        return result;
    }
}