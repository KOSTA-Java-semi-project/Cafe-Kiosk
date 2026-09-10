package kosta.kiosk.controller;

import kosta.kiosk.model.dto.*;
import kosta.kiosk.model.service.OrderService;
import kosta.kiosk.view.OrderView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    private Scanner sc = new Scanner(System.in);
    private OrderView orderView = new OrderView();
    private OrderService orderService = new OrderService();

    public void startOrder(int userId) {
        List<OrderDetail> cart = new ArrayList<>();

        try {
            while (true) {
                // 1. 카테고리 선택
                List<Category> categoryList = orderService.getCategoryList();
                orderView.printCategoryList(categoryList);
                int categoryChoice = sc.nextInt();
                if (categoryChoice == 0) break;

                // 2. 메뉴 선택
                List<Menu> menuList = orderService.getMenuListByCategoryId(categoryChoice);
                orderView.printMenuList(menuList);
                int menuChoice = sc.nextInt();
                if (menuChoice == 0) continue;

                Menu selectedMenu = findMenuById(menuList, menuChoice);
                if (selectedMenu == null) {
                    System.out.println("존재하지 않는 메뉴입니다.");
                    continue;
                }

                // 3. 옵션 선택 후 장바구니 담기
                OrderDetail detail = selectOptions(selectedMenu);
                cart.add(detail);

                // 4. 계속 담을지 확인
                System.out.println("메뉴를 더 담으시겠습니까? (1: 예, 0: 아니오)");
                if (sc.nextInt() == 0) break;
            }
        } catch (SQLException e) {
            System.out.println("메뉴 정보를 불러오는 중 오류가 발생했습니다.");
            e.printStackTrace();
            return;
        }

        if (cart.isEmpty()) {
            System.out.println("담긴 메뉴가 없어 주문을 종료합니다.");
            return;
        }

        // 5. 결제하기(주문 확정)
        System.out.println("결제하시겠습니까? (1: 예, 0: 취소)");
        if (sc.nextInt() != 1) {
            System.out.println("주문이 취소되었습니다.");
            return;
        }

        try {
            int orderId = orderService.insertOrder(userId, cart);
            System.out.println("주문이 완료되었습니다! 주문번호: " + orderId);
        } catch (SQLException e) {
            System.out.println("주문 처리 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private OrderDetail selectOptions(Menu menu) {
        orderView.printSizeOption();
        Size size = Size.values()[sc.nextInt() - 1];

        orderView.printShotOption();
        int shot = sc.nextInt();

        IceLevel ice = null;
        if (menu.getHotIce() == Menu.HotIce.ICE) {
            orderView.printIceOption();
            ice = IceLevel.values()[sc.nextInt() - 1];
        }

        orderView.printSyrupOption();
        int syrup = sc.nextInt();

        orderView.printAmountOption();
        int amount = sc.nextInt();

        OrderDetail detail = new OrderDetail();
        detail.setMenuId(menu.getMenuId());
        detail.setSize(size);
        detail.setShot(shot);
        detail.setIce(ice);
        detail.setSyrup(syrup);
        detail.setAmount(amount);
        return detail;
    }

    private Menu findMenuById(List<Menu> menuList, int menuId) {
        for (Menu menu : menuList) {
            if (menu.getMenuId() == menuId) {
                return menu;
            }
        }
        return null;
    }
}