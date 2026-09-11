package kosta.kiosk.controller;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.IceLevel;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.Size;
import kosta.kiosk.model.dto.UserDTO;
import kosta.kiosk.model.service.OrderService;
import kosta.kiosk.session.Session;

import java.sql.SQLException;
import java.util.List;

public class OrderController {

    private static final OrderService orderService = new OrderService();

    public static List<Category> getCategoryList() throws SQLException {
        return orderService.getCategoryList();
    }

    public static List<Menu> getMenuListByCategoryId(int categoryId) throws SQLException {
        return orderService.getMenuListByCategoryId(categoryId);
    }

    public static void addToCart(Menu menu, Size size, int shot, IceLevel ice, int syrup, int amount) {
        List<OrderDetail> carts = Session.getInstance().getCarts();
        OrderDetail detail = new OrderDetail(0, menu.getMenuId(), amount, size, shot, ice, syrup);
        carts.add(detail);
    }

    public static Integer checkout() throws SQLException {
        List<OrderDetail> carts = Session.getInstance().getCarts();
        if (carts.isEmpty()) {
            return null;
        }

        UserDTO user = Session.getInstance().getUser();
        Integer userId = (user != null) ? user.getUserId() : null;

        int orderId = orderService.insertOrder(userId, carts);
        carts.clear();
        return orderId;
    }
}