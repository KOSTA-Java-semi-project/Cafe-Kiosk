package kosta.kiosk.view;

import kosta.kiosk.controller.OrderController;
import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.OrderDetail;
import java.util.HashMap;
import java.util.Map;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 주문 첫 화면(메뉴 고르는 화면)을 담당한다.
 * 화면 구성: 대분류(카테고리) 전체 목록 + 현재 선택된 카테고리의 메뉴를 함께 보여준다.
 * (기본으로 첫 번째 카테고리의 메뉴가 함께 표시되고, "c+번호"로 다른 카테고리로 전환할 수 있다.)
 *
 * 메뉴를 하나 고르면 옵션 선택은 OrderView가 담당하고, 옵션 선택이 끝나면
 * (장바구니에 담긴 뒤) 다시 이 화면으로 돌아온다. "주문하기"를 선택하면
 * OrderView.checkout()으로 주문을 확정하고, 완료되면 이 화면을 빠져나간다.
 */
public class MenuView {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * 주문 화면 진입점. MainView 등에서 호출한다.
     */
    public static void startOrder() {
        List<Category> categoryList;
        try {
            categoryList = OrderController.getCategoryList();
        } catch (SQLException e) {
            System.out.println("카테고리 조회 실패: " + e.getMessage());
            return;
        }

        if (categoryList.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
            return;
        }

        // 화면 구성 2안: 대분류 전체 + 첫 대분류의 메뉴를 함께 노출 (기본 선택 카테고리 = 첫 번째 카테고리)
        Category currentCategory = categoryList.get(0);

        while (true) {
            List<Menu> menuList;
            try {
                menuList = OrderController.getMenuListByCategoryId(currentCategory.getCategoryId());
            } catch (SQLException e) {
                System.out.println("메뉴 조회 실패: " + e.getMessage());
                return;
            }

            printMenuMainScreen(categoryList, currentCategory, menuList);
            String input = sc.nextLine().trim();


            if (input.equals("0")) {
                return; // 이전 화면으로
            }
            if (input.equalsIgnoreCase("v")) {
                printCart();
                continue;
            }


            if (input.equalsIgnoreCase("o")) {
                boolean orderCompleted = OrderView.checkout();
                if (orderCompleted) {
                    return; // 주문 완료 -> 다음 단계(적립/결제)로 흐름이 넘어감
                }
                continue; // 장바구니가 비어있는 등 완료되지 못했으면 계속 메뉴 화면에 머무름
            }

            if (input.length() >= 2 && (input.charAt(0) == 'c' || input.charAt(0) == 'C')) {
                Category selected = findCategoryById(categoryList, input.substring(1));
                if (selected == null) {
                    System.out.println("존재하지 않는 카테고리입니다.\n");
                } else {
                    currentCategory = selected;
                }
                continue;
            }

            Menu selectedMenu = findMenuByDisplayIndex(menuList, input);
            if (selectedMenu == null) {
                System.out.println("잘못된 입력입니다.\n");
                continue;
            }
            if (selectedMenu.isSoldout()) {
                System.out.println("품절된 메뉴입니다.\n");
                continue;
            }


            // 옵션 선택(사이즈/얼음/샷/시럽/수량) -> 장바구니 담기. 끝나면 자연스럽게 이 while문(메뉴 화면)으로 복귀.
            OrderView.selectOptionsAndAddToCart(selectedMenu);
        }
    }

    private static void printCart() {
        List<OrderDetail> cart = OrderController.getCart();
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.\n");
            return;
        }

        List<Menu> allMenus;
        try {
            allMenus = OrderController.getAllMenuList();
        } catch (SQLException e) {
            System.out.println("장바구니 조회 실패: " + e.getMessage());
            return;
        }
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : allMenus) {
            menuMap.put(menu.getMenuId(), menu);
        }

        System.out.println("=========================================");
        System.out.println("               장바구니 목록");
        System.out.println("=========================================");
        int i = 1;
        for (OrderDetail detail : cart) {
            Menu menu = menuMap.get(detail.getMenuId());
            String name = (menu != null) ? menu.getMenuName() : "알 수 없는 메뉴";
            String iceText = (detail.getIce() != null) ? ", 얼음:" + detail.getIce() : "";
            System.out.println(i + ". " + name + " x " + detail.getAmount()
                    + " (사이즈:" + detail.getSize() + iceText
                    + ", 샷:" + detail.getShot() + ", 시럽:" + detail.getSyrup() + ")");
            i++;
        }

        try {
            int sum = OrderController.calculateCartSum(cart);
            System.out.println("-----------------------------------------");
            System.out.println("합계: " + sum + "원");
        } catch (SQLException e) {
            // 합계 계산 실패해도 목록은 이미 보여줬으니 무시
        }
        System.out.println("=========================================\n");
    }

    private static Category findCategoryById(List<Category> categoryList, String idText) {
        Integer categoryId = parseIntOrNull(idText);
        if (categoryId == null) {
            return null;
        }
        for (Category category : categoryList) {
            if (category.getCategoryId() == categoryId) {
                return category;
            }
        }
        return null;
    }

    private static Menu findMenuByDisplayIndex(List<Menu> menuList, String idText) {
        Integer index = parseIntOrNull(idText);
        if (index == null || index < 1 || index > menuList.size()) {
            return null;
        }
        return menuList.get(index - 1);
    }

    private static Integer parseIntOrNull(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // 대분류 전체 + 현재 카테고리의 메뉴를 함께 보여주는 메인 화면
    private static void printMenuMainScreen(List<Category> categoryList, Category currentCategory, List<Menu> menuList) {
        System.out.println("=========================================");
        System.out.println("                KOSTA CAFE");
        System.out.println("=========================================");

        System.out.print("[ 카테고리 ]  ");
        for (Category category : categoryList) {
            boolean isCurrent = category.getCategoryId() == currentCategory.getCategoryId();
            String mark = isCurrent ? "*" : " ";
            System.out.print(mark + category.getCategoryId() + "." + category.getCategoryName() + "  ");
        }
        System.out.println();
        System.out.println("-----------------------------------------");

        System.out.println("[ " + currentCategory.getCategoryName() + " 메뉴 ]");
        if (menuList.isEmpty()) {
            System.out.println("  등록된 메뉴가 없습니다.");
        } else {
            for (int i = 0; i < menuList.size(); i++) {
                Menu menu = menuList.get(i);
                String status = menu.isSoldout() ? " [품절]" : "";
                System.out.println("  " + (i + 1) + ". " + menu.getMenuName()
                        + " - " + menu.getPrice() + "원" + status);
            }
        }

        int cartCount = OrderController.getCart().size();
        System.out.println("-----------------------------------------");
        System.out.println("현재 장바구니: " + cartCount + "건");
        System.out.println("=========================================");
        System.out.println("메뉴번호: 메뉴 선택  |  c+카테고리번호: 카테고리 이동 (예: c2)");
        System.out.println("v: 장바구니 보기  |  o: 주문하기  |  0: 이전 화면으로");
        System.out.print("선택 > ");
    }
}
