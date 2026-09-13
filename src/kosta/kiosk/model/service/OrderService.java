package kosta.kiosk.model.service;

import kosta.kiosk.model.dao.CategoryDAO;
import kosta.kiosk.model.dao.CategoryDAOImpl;
import kosta.kiosk.model.dao.MenuDAO;
import kosta.kiosk.model.dao.MenuDAOImpl;
import kosta.kiosk.model.dao.OrderDAO;
import kosta.kiosk.model.dao.OrderDAOImpl;
import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAOImpl();
    private MenuDAO menuDAO = new MenuDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    public List<Category> getCategoryList() throws SQLException {
        return categoryDAO.selectAllCategory();
    }

    public List<Menu> getMenuListByCategoryId(int categoryId) throws SQLException {
        return menuDAO.selectMenuListByCategoryId(categoryId);
    }

    public int insertOrder(Integer userId, List<OrderDetail> orderDetailList) throws SQLException {
        if (orderDetailList == null || orderDetailList.isEmpty()) {
            throw new SQLException("주문할 메뉴가 없습니다.");
        }
        int sum = calculateSum(orderDetailList);
        Order order = new Order(userId, sum, orderDetailList);
        return orderDAO.insertOrder(order);
    }

    /**
     * 주문상세 목록의 합계 금액을 계산한다.
     * insertOrder 내부뿐 아니라, 주문 확정 전 결제 화면에 넘길 금액을 미리 계산할 때도 재사용한다.
     */
    public int calculateSum(List<OrderDetail> orderDetailList) throws SQLException {
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

    public Order selectOrderByOrderId(int orderId) throws SQLException {
        return orderDAO.selectOrderByOrderId(orderId);
    }

    public List<Menu> getAllMenuList() throws SQLException {
        return menuDAO.selectAllMenu();
    }
}
