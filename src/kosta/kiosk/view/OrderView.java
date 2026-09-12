package kosta.kiosk.view;

import kosta.kiosk.controller.OrderController;
import kosta.kiosk.controller.UserController;
import kosta.kiosk.model.dto.HotIce;
import kosta.kiosk.model.dto.IceLevel;
import kosta.kiosk.model.dto.Menu;
import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.Size;
import kosta.kiosk.session.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * 옵션 선택(사이즈/얼음/샷/시럽/수량) 및 주문(장바구니 담기 ~ 주문 확정)을 담당한다.
 * 메뉴 목록/카테고리 화면은 MenuView가 담당하며, 옵션 선택이 끝나면 자연스럽게
 * MenuView의 메뉴 고르는 화면으로 돌아간다(메서드 종료 = 화면 복귀).
 */
public class OrderView {

private static final Scanner sc = new Scanner(System.in);

    /**
     * MenuView에서 메뉴를 하나 골랐을 때 호출된다.
     * 사이즈 -> (ICE 메뉴인 경우) 얼음량 -> 샷 추가 -> 시럽 추가 -> 수량 순서로 옵션을 물어보고
     * 장바구니에 담는다.
     */
    public static void selectOptionsAndAddToCart(Menu menu) {
        System.out.println("\n[ " + menu.getMenuName() + " ] 옵션을 선택합니다.");

        Size size = inputSize();

        IceLevel ice = null;
        if (menu.getHotIce() == HotIce.ICE) {
            ice = inputIceLevel();
        }

        int shot = inputCount(OrderView::printShotOption);
        int syrup = inputCount(OrderView::printSyrupOption);
        int amount = inputAmount();

        OrderController.addToCart(menu, size, shot, ice, syrup, amount);
        System.out.println(menu.getMenuName() + " " + amount + "잔이 장바구니에 담겼습니다.\n");
    }

    /**
     * "주문하기" 선택 시 호출된다. 장바구니를 실제 주문(Order/OrderDetail)으로 확정 짓는다.
     *
     * @return 주문이 완료되어 다음 단계(적립/결제)로 넘어가야 하면 true,
     *         장바구니가 비어 있는 등 주문을 완료하지 못해 메뉴 화면에 머물러야 하면 false
     */
    public static boolean checkout() {
        List<OrderDetail> cart = OrderController.getCart();
        if (cart == null || cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다. 메뉴를 먼저 담아주세요.\n");
            return false;
        }

        int quantity = 0;
        for (OrderDetail detail : cart) {
            quantity += detail.getAmount();
        }

        try {
            int orderAmount = OrderController.calculateCartSum(cart);

            Integer orderId = OrderController.checkout();
            if (orderId == null) {
                System.out.println("장바구니가 비어있습니다.\n");
                return false;
            }

            printOrderComplete(orderId, orderAmount);

            // 다음 단계(적립/결제 파트)로 흐름 이동
            new PaymentView().pay(orderAmount, quantity);
            return true;
        } catch (SQLException e) {
            System.out.println("주문 실패: " + e.getMessage());
            return false;
        }
    }

    private static void printOrderComplete(int orderId, int orderAmount) {
        System.out.println("=========================================");
        System.out.println("      주문이 완료되었습니다! (주문번호 " + orderId + ")");
        System.out.println("      결제 예정 금액: " + orderAmount + "원");
        System.out.println("=========================================");
    }

    // ------------------------- 옵션 입력 -------------------------

    private static Size inputSize() {
        while (true) {
            printSizeOption();
            String line = sc.nextLine().trim();
            Integer choice = parseIntOrNull(line);
            Size[] sizes = Size.values();
            if (choice != null && choice >= 1 && choice <= sizes.length) {
                return sizes[choice - 1];
            }
            System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    private static IceLevel inputIceLevel() {
        while (true) {
            printIceOption();
            String line = sc.nextLine().trim();
            Integer choice = parseIntOrNull(line);
            IceLevel[] iceLevels = IceLevel.values();
            if (choice != null && choice >= 1 && choice <= iceLevels.length) {
                return iceLevels[choice - 1];
            }
            System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
        }
    }

    private static int inputCount(Runnable printPrompt) {
        while (true) {
            printPrompt.run();
            String line = sc.nextLine().trim();
            Integer value = parseIntOrNull(line);
            if (value != null && value >= 0) {
                return value;
            }
            System.out.println("0 이상의 숫자를 입력해주세요.");
        }
    }

    private static int inputAmount() {
        while (true) {
            printAmountOption();
            String line = sc.nextLine().trim();
            Integer value = parseIntOrNull(line);
            if (value != null && value >= 1) {
                return value;
            }
            System.out.println("1 이상의 숫자를 입력해주세요.");
        }
    }

    private static Integer parseIntOrNull(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // ------------------------- 옵션 화면 출력 -------------------------

    // 사이즈 선택 화면
    private static void printSizeOption() {
        System.out.println("=========================================");
        System.out.println("               사이즈를 선택하세요");
        System.out.println("=========================================");
        Size[] sizes = Size.values();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println((i + 1) + ". " + sizes[i].name());
        }
        System.out.println("=========================================");
        System.out.print("선택 > ");
    }

    // 얼음량 선택 화면 (ICE 메뉴에만 호출)
    private static void printIceOption() {
        System.out.println("=========================================");
        System.out.println("               얼음량을 선택하세요");
        System.out.println("=========================================");
        IceLevel[] iceLevels = IceLevel.values();
        for (int i = 0; i < iceLevels.length; i++) {
            System.out.println((i + 1) + ". " + iceLevels[i].name());
        }
        System.out.println("=========================================");
        System.out.print("선택 > ");
    }

    // 샷 추가 개수 입력 화면
    private static void printShotOption() {
        System.out.println("=========================================");
        System.out.println("         추가할 샷 개수를 입력하세요 (없으면 0)");
        System.out.println("=========================================");
        System.out.print("입력 > ");
    }

    // 시럽 추가 개수 입력 화면
    private static void printSyrupOption() {
        System.out.println("=========================================");
        System.out.println("        추가할 시럽 개수를 입력하세요 (없으면 0)");
        System.out.println("=========================================");
        System.out.print("입력 > ");
    }

    // 수량 입력 화면
    private static void printAmountOption() {
        System.out.println("=========================================");
        System.out.println("                수량을 입력하세요");
        System.out.println("=========================================");
        System.out.print("입력 > ");
    }
    public static void main(String[] args) {
        MenuView.startOrder();
    }
}
