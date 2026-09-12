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
import java.util.Collections;
import java.util.List;

public class OrderController {

    private static final OrderService orderService = new OrderService();

    // OrderController.java
    public static List<Category> getCategoryList() {
        try {
            return orderService.getCategoryList();
        } catch (SQLException e) {
            System.out.println("카테고리 조회 실패: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<Menu> getMenuListByCategoryId(int categoryId) {
        try {
            return orderService.getMenuListByCategoryId(categoryId);
        } catch (SQLException e) {
            System.out.println("메뉴 조회 실패: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void addToCart(Menu menu, Size size, int shot, IceLevel ice, int syrup, int amount) {
        List<OrderDetail> carts = Session.getInstance().getCarts();
        OrderDetail detail = new OrderDetail(0, menu.getMenuId(), amount, size, shot, ice, syrup);
        carts.add(detail);
    }

  /**
   *
   * 현재 장바구니(주문 목록)를 조회한다. MenuView/OrderView에서 화면에 보여주거나
   * 수량/금액 계산 등에 사용한다. (View가 Session에 직접 접근하지 않도록 이 메서드로 노출)
   * */
    public static List<OrderDetail> getCart() {
        return Session.getInstance().getCarts();
    }

    /**
     * * 장바구니(주문 예정 목록)의 합계 금액을 계산한다. "주문하기" 확정 전에
     * * 결제 화면으로 넘길 금액을 미리 계산할 때 사용한다.
    **/

    public static int calculateCartSum(List<OrderDetail> cart) {
        try {
            return orderService.calculateSum(cart);
        } catch (SQLException e) {
            System.out.println("합계 계산 실패: " + e.getMessage());
            return 0;
        }
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

    public static List<Menu> getAllMenuList() {
        try {
            return orderService.getAllMenuList();
        } catch (SQLException e) {
            System.out.println("전체 메뉴 조회 실패: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
