package kosta.kiosk.view;

import java.util.List;
import java.util.Scanner;

import kosta.kiosk.controller.ManagerController;
import kosta.kiosk.model.dto.HotIce;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;

public class ManagerView {

    private static final Scanner sc = new Scanner(System.in);

    private static final ManagerController managerController =
            new ManagerController();


    // =========================================================
    // 관리자 로그인
    // =========================================================

    public static void login() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("              관리자 로그인");
        System.out.println("=========================================");

        System.out.print("아이디 > ");
        String id = sc.nextLine();

        System.out.print("비밀번호 > ");
        String password = sc.nextLine();

        ManagerDTO manager =
                managerController.login(id, password);

        if (manager == null) {

            System.out.println();
            System.out.println("관리자 로그인에 실패했습니다.");
            System.out.println("아이디 또는 비밀번호를 확인해주세요.");

            return;
        }

        System.out.println();
        System.out.println(
                manager.getName()
                + " 관리자님, 로그인되었습니다."
        );

        managerMenu(manager);
    }


    // =========================================================
    // 관리자 메인 메뉴
    // =========================================================

    public static void managerMenu(ManagerDTO manager) {

        while (true) {

            printManagerMenu();

            int menu = readInt("선택 > ");

            switch (menu) {

                case 1:
                    menuInsert();
                    break;

                case 2:
                    menuUpdate();
                    break;

                case 3:
                    menuDelete();
                    break;

                case 4:
                    menuSelectAll();
                    break;

                case 5:
                    categorySelectAll();
                    break;

                case 6:
                    salesSelect();
                    break;

                case 0:

                    System.out.println();
                    System.out.println(
                            manager.getName()
                            + " 관리자님이 로그아웃되었습니다."
                    );

                    return;

                default:
                    System.out.println(
                            "올바른 메뉴 번호를 선택해주세요."
                    );
            }
        }
    }


    private static void printManagerMenu() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               관리자 메뉴");
        System.out.println("=========================================");
        System.out.println("1. 메뉴 등록");
        System.out.println("2. 메뉴 수정");
        System.out.println("3. 메뉴 삭제");
        System.out.println("4. 메뉴 전체 조회");
        System.out.println("5. 카테고리 조회");
        System.out.println("6. 매출 조회");
        System.out.println("0. 로그아웃");
        System.out.println("=========================================");
    }


    // =========================================================
    // 1. 메뉴 등록
    // =========================================================

    private static void menuInsert() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               메뉴 등록");
        System.out.println("=========================================");

        printCategories();

        int categoryId = readCategoryId();

        System.out.print("메뉴 이름 > ");
        String menuName = sc.nextLine().trim();

        System.out.print("메뉴 설명 > ");
        String description = sc.nextLine().trim();

        int price = readPositiveInt("가격 > ");

        HotIce hotIce = readHotIce();

        Menu menu = new Menu();

        menu.setCategoryId(categoryId);
        menu.setMenuName(menuName);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setHotIce(hotIce);

        // 신규 메뉴는 기본적으로 판매 가능
        menu.setSoldout(false);

        boolean result =
                managerController.insertMenu(menu);

        if (result) {

            System.out.println();
            System.out.println("메뉴가 등록되었습니다.");

            // 등록 후 현재 메뉴판 확인
            menuSelectAll();

        } else {

            System.out.println();
            System.out.println("메뉴 등록에 실패했습니다.");
        }
    }


    // =========================================================
    // 2. 메뉴 수정
    // =========================================================

    private static void menuUpdate() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               메뉴 수정");
        System.out.println("=========================================");

        // 먼저 현재 메뉴 출력
        menuSelectAll();

        int menuId =
                readPositiveInt("수정할 메뉴 번호(menu_id) > ");

        System.out.println();
        System.out.println("새로운 메뉴 정보를 입력해주세요.");

        printCategories();

        int categoryId = readCategoryId();

        System.out.print("메뉴 이름 > ");
        String menuName = sc.nextLine().trim();

        System.out.print("메뉴 설명 > ");
        String description = sc.nextLine().trim();

        int price = readPositiveInt("가격 > ");

        HotIce hotIce = readHotIce();

        boolean soldout = readSoldout();

        Menu menu = new Menu();

        menu.setMenuId(menuId);
        menu.setCategoryId(categoryId);
        menu.setMenuName(menuName);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setHotIce(hotIce);
        menu.setSoldout(soldout);

        boolean result =
                managerController.updateMenu(menu);

        if (result) {

            System.out.println();
            System.out.println("메뉴가 수정되었습니다.");

            // 수정 결과 바로 확인
            menuSelectAll();

        } else {

            System.out.println();
            System.out.println(
                    "메뉴 수정에 실패했습니다."
            );
        }
    }


    // =========================================================
    // 3. 메뉴 삭제
    // =========================================================

    private static void menuDelete() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               메뉴 삭제");
        System.out.println("=========================================");

        // 삭제하기 전에 전체 메뉴 확인
        menuSelectAll();

        int menuId =
                readPositiveInt("삭제할 메뉴 번호(menu_id) > ");

        System.out.print(
                "정말 삭제하시겠습니까? (Y/N) > "
        );

        String answer =
                sc.nextLine().trim();

        if (!answer.equalsIgnoreCase("Y")) {

            System.out.println(
                    "메뉴 삭제가 취소되었습니다."
            );

            return;
        }

        boolean result =
                managerController.deleteMenu(menuId);

        if (result) {

            System.out.println();
            System.out.println("메뉴가 삭제되었습니다.");

            // 삭제 후 결과 확인
            menuSelectAll();

        } else {

            System.out.println();
            System.out.println(
                    "메뉴 삭제에 실패했습니다."
            );
        }
    }


    // =========================================================
    // 4. 전체 메뉴 조회
    // =========================================================

    private static void menuSelectAll() {

        List<Menu> menuList =
                managerController.selectAllMenu();

        System.out.println();
        System.out.println(
                "============================================================"
        );
        System.out.println(
                "                         전체 메뉴"
        );
        System.out.println(
                "============================================================"
        );

        if (menuList.isEmpty()) {

            System.out.println(
                    "등록된 메뉴가 없습니다."
            );

            return;
        }

        for (Menu menu : menuList) {

            System.out.println(
                    "메뉴번호 : "
                    + menu.getMenuId()
            );

            System.out.println(
                    "카테고리 : "
                    + getCategoryName(
                            menu.getCategoryId()
                    )
            );

            System.out.println(
                    "메뉴이름 : "
                    + menu.getMenuName()
            );

            System.out.println(
                    "설명     : "
                    + menu.getDescription()
            );

            System.out.println(
                    "가격     : "
                    + menu.getPrice()
                    + "원"
            );

            System.out.println(
                    "HOT/ICE  : "
                    + menu.getHotIce()
            );

            System.out.println(
                    "판매상태 : "
                    + (
                        menu.isSoldout()
                        ? "품절"
                        : "판매중"
                    )
            );

            System.out.println(
                    "등록일   : "
                    + menu.getCreatedAt()
            );

            System.out.println(
                    "------------------------------------------------------------"
            );
        }
    }


    // =========================================================
    // 5. 카테고리 조회
    // =========================================================

    private static void categorySelectAll() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("              카테고리 목록");
        System.out.println("=========================================");

        printCategories();

        System.out.println("=========================================");
    }


    // =========================================================
    // 6. 매출 조회
    // =========================================================

    private static void salesSelect() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               매출 조회");
        System.out.println("=========================================");

        System.out.println(
                "매출 조회 기능은 Order 기능과 연결 후 구현합니다."
        );
    }


    // =========================================================
    // 카테고리 출력
    // =========================================================

    private static void printCategories() {

        System.out.println();
        System.out.println("1. 커피");
        System.out.println("2. 티");
        System.out.println("3. 에이드");
        System.out.println("4. 스무디");
        System.out.println();
    }


    private static int readCategoryId() {

        while (true) {

            int categoryId =
                    readInt("카테고리 번호 > ");

            if (categoryId >= 1
                    && categoryId <= 4) {

                return categoryId;
            }

            System.out.println(
                    "1 ~ 4 사이의 카테고리를 입력해주세요."
            );
        }
    }


    // =========================================================
    // HOT / ICE 입력
    // =========================================================

    private static HotIce readHotIce() {

        while (true) {

            System.out.println();
            System.out.println("1. HOT");
            System.out.println("2. ICE");

            int choice =
                    readInt("HOT/ICE 선택 > ");

            if (choice == 1) {
                return HotIce.HOT;
            }

            if (choice == 2) {
                return HotIce.ICE;
            }

            System.out.println(
                    "1 또는 2를 입력해주세요."
            );
        }
    }


    // =========================================================
    // 품절 상태
    // =========================================================

    private static boolean readSoldout() {

        while (true) {

            System.out.println();
            System.out.println("0. 판매중");
            System.out.println("1. 품절");

            int soldout =
                    readInt("판매 상태 > ");

            if (soldout == 0) {
                return false;
            }

            if (soldout == 1) {
                return true;
            }

            System.out.println(
                    "0 또는 1을 입력해주세요."
            );
        }
    }


    // =========================================================
    // 숫자 입력
    // =========================================================

    private static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    sc.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "숫자를 입력해주세요."
                );
            }
        }
    }


    private static int readPositiveInt(
            String message) {

        while (true) {

            int number =
                    readInt(message);

            if (number > 0) {
                return number;
            }

            System.out.println(
                    "0보다 큰 숫자를 입력해주세요."
            );
        }
    }


    // =========================================================
    // category_id → 카테고리 이름
    // =========================================================

    private static String getCategoryName(
            int categoryId) {

        switch (categoryId) {

            case 1:
                return "커피";

            case 2:
                return "티";

            case 3:
                return "에이드";

            case 4:
                return "스무디";

            default:
                return "알 수 없음";
        }
    }
}