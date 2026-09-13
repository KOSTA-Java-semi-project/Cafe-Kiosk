package kosta.kiosk.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.HotIce;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;

public class ManagerView {

    private static Scanner sc = new Scanner(System.in);


    /*
     * 관리자 로그인 정보 입력
     */
    public static ManagerDTO login() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              관리자 로그인");
        System.out.println("========================================");

        System.out.print("ID > ");
        String id = sc.nextLine();

        System.out.print("Password > ");
        String password = sc.nextLine();

        return new ManagerDTO(id, password);
    }


    /*
     * 관리자 로그인 성공
     */
    public static void loginSuccess(ManagerDTO manager) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            관리자 로그인 성공");
        System.out.println("========================================");
        System.out.println(manager.getName() + " 관리자님 환영합니다.");
        System.out.println("========================================");
    }


    /*
     * 관리자 로그인 실패
     */
    public static void loginFail() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("ID 또는 비밀번호가 올바르지 않습니다.");
        System.out.println("========================================");
    }


    /*
     * 관리자 메인 메뉴
     *
     * MainView에서
     * ManagerView.managerMenu();
     * 로 호출한다.
     */
    public static void managerMenu() {

        while (true) {

            int menu = printManagerMenu();

            switch (menu) {

                case 1:
                    System.out.println();
                    System.out.println("전체 메뉴 조회");
                    break;

                case 2:
                    System.out.println();
                    System.out.println("메뉴 상세 조회");
                    break;

                case 3:
                    System.out.println();
                    System.out.println("메뉴 등록");
                    break;

                case 4:
                    System.out.println();
                    System.out.println("메뉴 수정");
                    break;

                case 5:
                    System.out.println();
                    System.out.println("메뉴 삭제");
                    break;

                case 6:
                    System.out.println();
                    System.out.println("메뉴 품절 상태 변경");
                    break;

                case 0:
                    System.out.println();
                    System.out.println("관리자 메뉴를 종료합니다.");
                    return;

                default:
                    System.out.println();
                    System.out.println("잘못된 메뉴 번호입니다.");
            }
        }
    }


    /*
     * 관리자 메뉴 출력
     */
    public static int printManagerMenu() {

        while (true) {

            try {

                System.out.println();
                System.out.println("========================================");
                System.out.println("              관리자 메뉴");
                System.out.println("========================================");
                System.out.println("1. 전체 메뉴 조회");
                System.out.println("2. 메뉴 상세 조회");
                System.out.println("3. 메뉴 등록");
                System.out.println("4. 메뉴 수정");
                System.out.println("5. 메뉴 삭제");
                System.out.println("6. 메뉴 품절 상태 변경");
                System.out.println("0. 이전으로");
                System.out.println("========================================");

                System.out.print("선택 > ");

                return Integer.parseInt(
                        sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "메뉴 번호를 숫자로 입력해주세요."
                );
            }
        }
    }


    /*
     * 카테고리 목록 출력
     *
     * categoryList는 DAO에서 DB를 조회한 결과를
     * Controller / Service를 통해 전달받는다.
     */
    public static void printCategoryList(
            List<Category> categoryList) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              카테고리 선택");
        System.out.println("========================================");

        if (categoryList == null
                || categoryList.isEmpty()) {

            System.out.println(
                    "등록된 카테고리가 없습니다."
            );

            System.out.println(
                    "========================================"
            );

            return;
        }

        for (Category category : categoryList) {

            System.out.println(
                    category.getCategoryId()
                    + ". "
                    + category.getCategoryName()
            );
        }

        System.out.println(
                "========================================"
        );
    }


    /*
     * 전체 메뉴 출력
     */
    public static void printMenuList(
            List<Menu> menuList) {

        System.out.println();
        System.out.println(
                "=========================================================================="
        );
        System.out.println(
                "                              전체 메뉴"
        );
        System.out.println(
                "=========================================================================="
        );

        if (menuList == null
                || menuList.isEmpty()) {

            System.out.println(
                    "등록된 메뉴가 없습니다."
            );

            System.out.println(
                    "=========================================================================="
            );

            return;
        }

        for (Menu menu : menuList) {

            System.out.println(
                    "메뉴 번호 : "
                    + menu.getMenuId()
            );

            System.out.println(
                    "카테고리 번호 : "
                    + menu.getCategoryId()
            );

            System.out.println(
                    "메뉴 이름 : "
                    + menu.getMenuName()
            );

            System.out.println(
                    "설명 : "
                    + menu.getDescription()
            );

            System.out.println(
                    "가격 : "
                    + menu.getPrice()
            );

            System.out.println(
                    "HOT/ICE : "
                    + menu.getHotIce()
            );

            System.out.println(
                    "품절 상태 : "
                    + (menu.isSoldout()
                        ? "품절"
                        : "판매중")
            );

            System.out.println(
                    "--------------------------------------------------------------------------"
            );
        }
    }


    /*
     * 메뉴 상세 조회 결과 출력
     */
    public static void printMenu(
            Menu menu) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              메뉴 상세 정보");
        System.out.println("========================================");

        if (menu == null) {

            System.out.println(
                    "해당 메뉴가 존재하지 않습니다."
            );

            System.out.println(
                    "========================================"
            );

            return;
        }

        System.out.println(
                "메뉴 번호 : "
                + menu.getMenuId()
        );

        System.out.println(
                "카테고리 번호 : "
                + menu.getCategoryId()
        );

        System.out.println(
                "메뉴 이름 : "
                + menu.getMenuName()
        );

        System.out.println(
                "설명 : "
                + menu.getDescription()
        );

        System.out.println(
                "가격 : "
                + menu.getPrice()
        );

        System.out.println(
                "HOT/ICE : "
                + menu.getHotIce()
        );

        System.out.println(
                "품절 상태 : "
                + (menu.isSoldout()
                    ? "품절"
                    : "판매중")
        );

        System.out.println(
                "등록일 : "
                + menu.getCreatedAt()
        );

        System.out.println(
                "========================================"
        );
    }


    /*
     * 메뉴 ID 입력
     */
    public static int inputMenuId() {

        while (true) {

            try {

                System.out.print(
                        "메뉴 번호 > "
                );

                return Integer.parseInt(
                        sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "메뉴 번호는 숫자로 입력해주세요."
                );
            }
        }
    }


    /*
     * 메뉴 등록 정보 입력
     */
    public static Menu inputMenu(
            List<Category> categoryList) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("                메뉴 등록");
        System.out.println("========================================");

        /*
         * DB에서 가져온 실제 카테고리 목록 출력
         */
        printCategoryList(categoryList);

        int categoryId =
                inputValidCategoryId(
                        categoryList
                );

        System.out.print(
                "메뉴 이름 > "
        );

        String menuName =
                sc.nextLine();

        System.out.print(
                "메뉴 설명 > "
        );

        String description =
                sc.nextLine();

        int price =
                inputPrice();

        HotIce hotIce =
                inputHotIce();

        Menu menu =
                new Menu();

        menu.setCategoryId(
                categoryId
        );

        menu.setMenuName(
                menuName
        );

        menu.setDescription(
                description
        );

        menu.setPrice(
                price
        );

        menu.setHotIce(
                hotIce
        );

        /*
         * 신규 메뉴 기본값
         */
        menu.setSoldout(false);

        return menu;
    }


    /*
     * 메뉴 수정 정보 입력
     */
    public static Menu inputMenuUpdate(
            Menu oldMenu,
            List<Category> categoryList) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("                메뉴 수정");
        System.out.println("========================================");

        System.out.println(
                "현재 메뉴 : "
                + oldMenu.getMenuName()
        );

        printCategoryList(
                categoryList
        );

        int categoryId =
                inputValidCategoryId(
                        categoryList
                );

        System.out.print(
                "메뉴 이름 ["
                + oldMenu.getMenuName()
                + "] > "
        );

        String menuName =
                sc.nextLine();

        if (menuName.isBlank()) {

            menuName =
                    oldMenu.getMenuName();
        }


        System.out.print(
                "메뉴 설명 ["
                + oldMenu.getDescription()
                + "] > "
        );

        String description =
                sc.nextLine();

        if (description.isBlank()) {

            description =
                    oldMenu.getDescription();
        }


        int price;

        while (true) {

            System.out.print(
                    "가격 ["
                    + oldMenu.getPrice()
                    + "] > "
            );

            String input =
                    sc.nextLine();

            if (input.isBlank()) {

                price =
                        oldMenu.getPrice();

                break;
            }

            try {

                price =
                        Integer.parseInt(input);

                if (price < 0) {

                    System.out.println(
                            "가격은 0 이상이어야 합니다."
                    );

                    continue;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println(
                        "가격은 숫자로 입력해주세요."
                );
            }
        }


        HotIce hotIce =
                inputHotIce();


        Menu menu =
                new Menu();

        menu.setMenuId(
                oldMenu.getMenuId()
        );

        menu.setCategoryId(
                categoryId
        );

        menu.setMenuName(
                menuName
        );

        menu.setDescription(
                description
        );

        menu.setPrice(
                price
        );

        menu.setHotIce(
                hotIce
        );

        menu.setSoldout(
                oldMenu.isSoldout()
        );

        return menu;
    }


    /*
     * 카테고리 ID 검증
     *
     * DB에서 조회한 category_id만 입력 가능
     */
    private static int inputValidCategoryId(
            List<Category> categoryList) {

        while (true) {

            try {

                System.out.print(
                        "카테고리 번호 > "
                );

                int categoryId =
                        Integer.parseInt(
                                sc.nextLine()
                        );

                for (Category category
                        : categoryList) {

                    if (
                        category.getCategoryId()
                        == categoryId
                    ) {

                        return categoryId;
                    }
                }

                System.out.println(
                        "존재하지 않는 카테고리입니다."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "카테고리 번호를 숫자로 입력해주세요."
                );
            }
        }
    }


    /*
     * 가격 입력
     */
    private static int inputPrice() {

        while (true) {

            try {

                System.out.print(
                        "가격 > "
                );

                int price =
                        Integer.parseInt(
                                sc.nextLine()
                        );

                if (price < 0) {

                    System.out.println(
                            "가격은 0 이상이어야 합니다."
                    );

                    continue;
                }

                return price;

            } catch (NumberFormatException e) {

                System.out.println(
                        "가격은 숫자로 입력해주세요."
                );
            }
        }
    }


    /*
     * HOT / ICE 입력
     */
    private static HotIce inputHotIce() {

        while (true) {

            System.out.println(
                    "가능한 옵션 : "
                    + Arrays.toString(
                            HotIce.values()
                    )
            );

            System.out.print(
                    "HOT / ICE 선택 > "
            );

            String input =
                    sc.nextLine()
                      .trim()
                      .toUpperCase();

            try {

                return HotIce.valueOf(
                        input
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "올바른 HOT/ICE 값을 입력해주세요."
                );
            }
        }
    }


    /*
     * 삭제 확인
     */
    public static boolean confirmDelete(
            Menu menu) {

        System.out.println();

        System.out.println(
                "삭제할 메뉴 : "
                + menu.getMenuName()
        );

        System.out.print(
                "정말 삭제하시겠습니까? (Y/N) > "
        );

        String answer =
                sc.nextLine()
                  .trim();

        return answer.equalsIgnoreCase(
                "Y"
        );
    }


    /*
     * 품절 상태 입력
     */
    public static boolean inputSoldout() {

        while (true) {

            System.out.println();
            System.out.println(
                    "1. 판매중"
            );
            System.out.println(
                    "2. 품절"
            );

            System.out.print(
                    "상태 선택 > "
            );

            String input =
                    sc.nextLine();

            if (
                "1".equals(input)
            ) {

                return false;
            }

            if (
                "2".equals(input)
            ) {

                return true;
            }

            System.out.println(
                    "1 또는 2를 입력해주세요."
            );
        }
    }


    /*
     * 성공 메시지
     */
    public static void printSuccess(
            String message) {

        System.out.println();

        System.out.println(
                "[성공] "
                + message
        );
    }


    /*
     * 오류 메시지
     */
    public static void printError(
            String message) {

        System.out.println();

        System.out.println(
                "[오류] "
                + message
        );
    }
}