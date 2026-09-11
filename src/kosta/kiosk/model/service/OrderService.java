package kosta.kiosk.model.service;

import kosta.kiosk.model.dao.MenuDAO;
import kosta.kiosk.model.dao.MenuDAOImpl;
import kosta.kiosk.model.dao.OrderDAO;
import kosta.kiosk.model.dao.OrderDAOImpl;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();
    private MenuDAO menuDAO = new MenuDAOImpl();

    public int insertOrder(int userId, List<OrderDetail> orderDetailList) throws SQLException {
        if (orderDetailList == null || orderDetailList.isEmpty()) {
            throw new SQLException("주문할 메뉴가 없습니다.");
        }
        int sum = calculateSum(orderDetailList);
        Order order = new Order(userId, sum, orderDetailList);
        return orderDAO.insertOrder(order);
    }

    private int calculateSum(List<OrderDetail> orderDetailList) throws SQLException {
        Map<Integer, Integer> priceMap = new HashMap<>();
        for (Menu menu : menuDAO.selectAllMenu()) {
            priceMap.put(menu.getMenuId(), menu.getPrice());
        }

        int sum = 0;
        for (OrderDetail detail : orderDetailList) {
            int price = priceMap.get(detail.getMenuId());
            sum += price * detail.getAmount();
        }
        return sum;
    }

    public List<Order> selectOrderByUserId(int userId) throws SQLException {
        return orderDAO.selectOrderByUserId(userId);
    }

    public Order selectOrderByOrderId(int orderId) throws SQLException {
        return orderDAO.selectOrderByOrderId(orderId);
    }
}