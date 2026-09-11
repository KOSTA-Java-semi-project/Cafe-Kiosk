package kosta.kiosk.view;

import kosta.kiosk.model.dto.Category;
import kosta.kiosk.model.dto.Menu;

import java.util.List;
import java.util.Map;

public class MenuView {

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

    // 전체 메뉴판 출력 (카테고리별 그룹)
    public static void printMenuBoard(Map<Category, List<Menu>> menuMap) {
        System.out.println("=========================================");
        System.out.println("                 전체 메뉴");
        System.out.println("=========================================");

        for (Map.Entry<Category, List<Menu>> entry : menuMap.entrySet()) {
            Category category = entry.getKey();
            List<Menu> menuList = entry.getValue();

            System.out.println("[ " + category.getCategoryName() + " ]");

            if (menuList.isEmpty()) {
                System.out.println("  등록된 메뉴가 없습니다.");
            } else {
                for (Menu menu : menuList) {
                    String status = menu.isSoldout() ? " [품절]" : "";
                    System.out.println("  - " + menu.getMenuName()
                            + " : " + menu.getPrice() + "원" + status);
                }
            }
            System.out.println();
        }

        System.out.println("=========================================");
        System.out.println("0. 이전으로");
        System.out.print("선택 > ");
    }
}