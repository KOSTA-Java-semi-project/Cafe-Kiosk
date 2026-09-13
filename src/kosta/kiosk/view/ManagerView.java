package kosta.kiosk.view;

import java.util.List;
import java.util.Scanner;

import kosta.kiosk.controller.ManagerController;
import kosta.kiosk.model.dto.HotIce;
import kosta.kiosk.model.dto.Menu;

public class ManagerView {

    private static Scanner sc = new Scanner(System.in);

    // ManagerController는 static 메서드가 아니므로 객체 생성
    private static ManagerController managerController = new ManagerController();


    /**
     * 관리자 메인 메뉴
     */
    public static void managerMenu() {

        while (true) {

            printManagerMenu();

            try {

                int menu = Integer.parseInt(sc.nextLine());

                switch (menu) {

                case 1:
                    // 전체 메뉴 조회
                    selectAllMenu();
                    break;

                case 2:
                    // 카테고리별 메뉴 조회
                    selectMenuListByCategoryId();
                    break;

                case 3:
                    // 메뉴 추가
                    insertMenu();
                    break;

                case 4:
                    // 메뉴 수정
                    updateMenuById();
                    break;

                case 5:
                    // 메뉴 삭제
                    deleteMenuById();
                    break;

                case 0:
                    // MainView로 돌아가기
                    return;

                default:
                    System.out.println("잘못된 메뉴 번호입니다.");
                }

            } catch (NumberFormatException e) {

                System.out.println("숫자를 입력해주세요.");
            }
        }
    }


    /**
     * 관리자 메뉴 출력
     */
    public static void printManagerMenu() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               관리자 메뉴");
        System.out.println("=========================================");
        System.out.println("1. 전체 메뉴 조회");
        System.out.println("2. 카테고리별 메뉴 조회");
        System.out.println("3. 메뉴 추가");
        System.out.println("4. 메뉴 수정");
        System.out.println("5. 메뉴 삭제");
        System.out.println("0. 이전으로");
        System.out.println("=========================================");
        System.out.print("선택 > ");
    }


    /**
     * 전체 메뉴 조회
     */
    public static void selectAllMenu() {

        List<Menu> menuList = managerController.selectAllMenu();

        if (menuList == null || menuList.isEmpty()) {

            System.out.println("등록된 메뉴가 없습니다.");
            return;
        }

        System.out.println();
        System.out.println("=========================================");
        System.out.println("               전체 메뉴");
        System.out.println("=========================================");

        for (Menu menu : menuList) {

            System.out.println(menu);
        }
    }


    /**
     * 카테고리별 메뉴 조회
     */
    public static void selectMenuListByCategoryId() {

        try {

            System.out.print("카테고리 번호 : ");

            int categoryId = Integer.parseInt(sc.nextLine());

            List<Menu> menuList =
                    managerController.selectMenuListByCategoryId(categoryId);

            if (menuList == null || menuList.isEmpty()) {

                System.out.println("해당 카테고리에 등록된 메뉴가 없습니다.");
                return;
            }

            System.out.println();
            System.out.println("=========================================");
            System.out.println("          카테고리별 메뉴 조회");
            System.out.println("=========================================");

            for (Menu menu : menuList) {

                System.out.println(menu);
            }

        } catch (NumberFormatException e) {

            System.out.println("카테고리 번호는 숫자로 입력해주세요.");
        }
    }


    /**
     * 메뉴 추가
     */
    public static void insertMenu() {

        try {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("                메뉴 추가");
            System.out.println("=========================================");

            System.out.print("카테고리 번호 : ");
            int categoryId = Integer.parseInt(sc.nextLine());

            System.out.print("메뉴 이름 : ");
            String menuName = sc.nextLine();

            System.out.print("메뉴 설명 : ");
            String description = sc.nextLine();

            System.out.print("가격 : ");
            int price = Integer.parseInt(sc.nextLine());

            HotIce hotIce = selectHotIce();

            // menu_id는 DB에서 AUTO_INCREMENT
            Menu menu = new Menu();

            menu.setCategoryId(categoryId);
            menu.setMenuName(menuName);
            menu.setDescription(description);
            menu.setPrice(price);
            menu.setHotIce(hotIce);
            menu.setSoldout(false);

            int result = managerController.insertMenu(menu);

            if (result > 0) {

                System.out.println("메뉴가 추가되었습니다.");

            } else {

                System.out.println("메뉴 추가에 실패했습니다.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "카테고리 번호와 가격은 숫자로 입력해주세요."
            );
        }
    }


    /**
     * 메뉴 수정
     */
    public static void updateMenuById() {

        try {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("                메뉴 수정");
            System.out.println("=========================================");

            System.out.print("수정할 메뉴 번호 : ");
            int menuId = Integer.parseInt(sc.nextLine());

            System.out.print("카테고리 번호 : ");
            int categoryId = Integer.parseInt(sc.nextLine());

            System.out.print("메뉴 이름 : ");
            String menuName = sc.nextLine();

            System.out.print("메뉴 설명 : ");
            String description = sc.nextLine();

            System.out.print("가격 : ");
            int price = Integer.parseInt(sc.nextLine());

            HotIce hotIce = selectHotIce();

            Menu menu = new Menu(
                    menuId,
                    categoryId,
                    menuName,
                    description,
                    price,
                    hotIce
            );

            int result = managerController.updateMenuById(menu);

            if (result > 0) {

                System.out.println("메뉴가 수정되었습니다.");

            } else {

                System.out.println("메뉴 수정에 실패했습니다.");
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "메뉴 번호, 카테고리 번호, 가격은 숫자로 입력해주세요."
            );
        }
    }


    /**
     * 메뉴 삭제
     */
    public static void deleteMenuById() {

        try {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("                메뉴 삭제");
            System.out.println("=========================================");

            System.out.print("삭제할 메뉴 번호 : ");

            int menuId = Integer.parseInt(sc.nextLine());

            managerController.deleteMenuById(menuId);

            System.out.println("메뉴 삭제 요청이 완료되었습니다.");

        } catch (NumberFormatException e) {

            System.out.println("메뉴 번호는 숫자로 입력해주세요.");
        }
    }


    /**
     * HOT / ICE 선택
     */
    public static HotIce selectHotIce() {

        while (true) {

            System.out.println();
            System.out.println("HOT / ICE 선택");
            System.out.println("1. HOT");
            System.out.println("2. ICE");
            System.out.print("선택 > ");

            String input = sc.nextLine();

            switch (input) {

            case "1":
                return HotIce.HOT;

            case "2":
                return HotIce.ICE;

            default:
                System.out.println("1 또는 2를 입력해주세요.");
            }
        }
    }


    /**
     * 일반 메시지 출력
     */
    public static void printMessage(String message) {

        System.out.println(message);
    }


    /**
     * 오류 메시지 출력
     */
    public static void printError(String message) {

        System.out.println("[ERROR] " + message);
    }
}