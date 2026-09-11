package kosta.kiosk.view;
import kosta.kiosk.controller.OrderController;
import kosta.kiosk.model.dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class OrderView {
        private static Scanner sc = new Scanner(System.in);

        public static void orderMenu() {
            while (true) {
                List<Category> categoryList;
                try {
                    categoryList = OrderController.getCategoryList();
                } catch (SQLException e) {
                    System.out.println("카테고리 조회 실패: " + e.getMessage());
                    return;
                }

                printCategoryList(categoryList);
                int categoryChoice = Integer.parseInt(sc.nextLine());
                if (categoryChoice == 0) return;

                selectMenu(categoryChoice);
            }
        }

        private static void selectMenu(int categoryId) {
            List<Menu> menuList;
            try {
                menuList = OrderController.getMenuListByCategoryId(categoryId);
            } catch (SQLException e) {
                System.out.println("메뉴 조회 실패: " + e.getMessage());
                return;
            }

            printMenuList(menuList);
            int menuChoice = Integer.parseInt(sc.nextLine());
            if (menuChoice == 0) return;

            Menu selectedMenu = menuList.stream()
                    .filter(m -> m.getMenuId() == menuChoice)
                    .findFirst()
                    .orElse(null);
            if (selectedMenu == null || selectedMenu.isSoldout()) {
                System.out.println("선택할 수 없는 메뉴입니다.");
                return;
            }

            selectOptions(selectedMenu);
        }

        private static void selectOptions(Menu menu) {
            printSizeOption();
            int sizeChoice = Integer.parseInt(sc.nextLine());
            Size size = Size.values()[sizeChoice - 1];

            printShotOption();
            int shot = Integer.parseInt(sc.nextLine());

            IceLevel ice = null;
            if (menu.getHotIce() == HotIce.ICE) {
                printIceOption();
                int iceChoice = Integer.parseInt(sc.nextLine());
                ice = IceLevel.values()[iceChoice - 1];
            }

            printSyrupOption();
            int syrup = Integer.parseInt(sc.nextLine());

            printAmountOption();
            int amount = Integer.parseInt(sc.nextLine());

            OrderController.addToCart(menu, size, shot, ice, syrup, amount);
            System.out.println(menu.getMenuName() + " " + amount + "잔 담았습니다.");
        }

        public static void checkout() {
            try {
                Integer orderId = OrderController.checkout();
                if (orderId == null) {
                    System.out.println("장바구니가 비어있습니다.");
                    return;
                }
                printOrderComplete(orderId);
            } catch (SQLException e) {
                System.out.println("주문 실패: " + e.getMessage());
            }
        }

        public static void printOrderComplete(int orderId) {
            System.out.println("=========================================");
            System.out.println("        주문이 완료되었습니다! (주문번호 " + orderId + ")");
            System.out.println("=========================================");
        }

        // 카테고리 선택 화면 출력
        public static void printCategoryList(List<Category> categoryList) {
            System.out.println("=========================================");
            System.out.println("              카테고리를 선택하세요");
            System.out.println("=========================================");
            for (Category category : categoryList) {
                System.out.println(category.getCategoryId() + ". " + category.getCategoryName());
            }
            System.out.println("0. 이전으로");
            System.out.println("=========================================");
            System.out.print("선택 > ");
        }
        // 메뉴 선택 화면 출력 (카테고리 선택 다음 단계)
        public static void printMenuList(List<Menu> menuList) {
            System.out.println("=========================================");
            System.out.println("               메뉴를 선택하세요");
            System.out.println("=========================================");
            for (Menu menu : menuList) {
                String status = menu.isSoldout() ? " [품절]" : "";
                System.out.println(menu.getMenuId() + ". " + menu.getMenuName()
                        + " - " + menu.getPrice() + "원" + status);
            }
            System.out.println("0. 이전으로");
            System.out.println("=========================================");
            System.out.print("선택 > ");
        }

        // 옵션 선택 화면 (사이즈/샷/얼음/시럽/수량을 한 화면에서 출력)
        /*public void printOptionForm(Menu menu) {
            System.out.println("=========================================");
            System.out.println("     [" + menu.getMenuName() + "] 옵션을 선택하세요");
            System.out.println("=========================================");

            System.out.println("- 사이즈");
            Size[] sizes = Size.values();
            for (int i = 0; i < sizes.length; i++) {
                System.out.println("  " + (i + 1) + ". " + sizes[i].name());
            }

            System.out.println("- 샷 추가 (개수 입력, 없으면 0)");

            if (menu.getHotIce() == Menu.HotIce.ICE) {
                System.out.println("- 얼음량");
                IceLevel[] iceLevels = IceLevel.values();
                for (int i = 0; i < iceLevels.length; i++) {
                    System.out.println("  " + (i + 1) + ". " + iceLevels[i].name());
                }
            }

            System.out.println("- 시럽 추가 (개수 입력, 없으면 0)");
            System.out.println("- 수량 (몇 잔인지 입력)");
            System.out.println("=========================================");
            System.out.println("안내된 순서대로 값을 입력해주세요.");
        }*/

        /**
         * 옵션 선택 화면 분리형
         * */
        // 사이즈 선택 화면
        public static void printSizeOption() {
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

        // 샷 추가 개수 입력 화면
        public static void printShotOption() {
            System.out.println("=========================================");
            System.out.println("         추가할 샷 개수를 입력하세요 (없으면 0)");
            System.out.println("=========================================");
            System.out.print("입력 > ");
        }

        // 얼음량 선택 화면 (ICE 메뉴에만 호출)
        public static void printIceOption() {
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

        // 시럽 추가 개수 입력 화면
        public static void printSyrupOption() {
            System.out.println("=========================================");
            System.out.println("        추가할 시럽 개수를 입력하세요 (없으면 0)");
            System.out.println("=========================================");
            System.out.print("입력 > ");
        }

        // 수량 입력 화면
        public static void printAmountOption() {
            System.out.println("=========================================");
            System.out.println("                수량을 입력하세요");
            System.out.println("=========================================");
            System.out.print("입력 > ");
        }
    }