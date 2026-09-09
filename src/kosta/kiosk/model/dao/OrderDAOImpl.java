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
        return List.of();
    }

    @Override
    public int insertOrder(Order order) throws SQLException {
        return 0;
    }

    @Override
    public Order selectOrderByOrderId(int orderId) throws SQLException {
        return null;
    }

    @Override
    public List<OrderDetail> selectOrderDetails(int orderId) throws SQLException {
        return List.of();
    }
}