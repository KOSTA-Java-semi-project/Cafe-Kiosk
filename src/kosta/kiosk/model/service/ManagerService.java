package kosta.kiosk.model.service;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import kosta.kiosk.model.dao.ManagerDAO;
import kosta.kiosk.model.dao.ManagerDAOImpl;
import kosta.kiosk.model.dao.MenuDAO;
import kosta.kiosk.model.dao.MenuDAOImpl;
import kosta.kiosk.model.dto.ManagerDTO;
import kosta.kiosk.model.dto.Menu;

public class ManagerService {

    private final ManagerDAO managerDAO;
    private final MenuDAO menuDAO;


    public ManagerService() {

        managerDAO = new ManagerDAOImpl();

        menuDAO = new MenuDAOImpl();
    }


    // =========================================================
    // 관리자 로그인
    // =========================================================

    public ManagerDTO login(
            String id,
            String password
    ) throws SQLException {

        if (id == null
                || id.trim().isEmpty()) {

            return null;
        }

        if (password == null
                || password.trim().isEmpty()) {

            return null;
        }

        return managerDAO.login(
                id.trim(),
                password
        );
    }


    // =========================================================
    // 전체 메뉴 조회
    // =========================================================

    public List<Menu> selectAllMenu()
            throws SQLException {

        return menuDAO.selectAllMenu();
    }


    // =========================================================
    // 메뉴 등록
    // =========================================================

    public int insertMenu(
            Menu menu
    ) throws SQLException {

        return menuDAO.insertMenu(menu);
    }


    // =========================================================
    // 메뉴 수정
    // =========================================================

    public int updateMenu(
            Menu menu
    ) throws SQLException {

        return menuDAO.updateMenuById(menu);
    }


    // =========================================================
    // 메뉴 삭제
    // =========================================================

    public void deleteMenu(
            int menuId
    ) throws SQLException {

        menuDAO.deleteMenuById(menuId);
    }


    // =========================================================
    // 일매출 조회
    // =========================================================

    public int selectDailySales(
            LocalDate date
    ) throws SQLException {

        /*
         * 예:
         *
         * date = 2026-09-14
         *
         * start:
         * 2026-09-14 00:00:00
         *
         * end:
         * 2026-09-15 00:00:00
         *
         * 따라서 9월 14일 하루 동안 발생한
         * 모든 order.sum 값을 더한다.
         */

        LocalDateTime start =
                date.atStartOfDay();

        LocalDateTime end =
                date.plusDays(1)
                    .atStartOfDay();

        return managerDAO.selectTotalSales(
                start,
                end
        );
    }


    // =========================================================
    // 주간 매출 조회
    // =========================================================

    public int selectWeeklySales(
            LocalDate date
    ) throws SQLException {

        /*
         * 사용자가 해당 주의 아무 날짜나 입력한다.
         *
         * 예:
         *
         * 입력:
         * 2026-09-16
         *
         * 해당 주의 월요일:
         * 2026-09-14
         *
         * 다음 월요일:
         * 2026-09-21
         *
         * 조회:
         *
         * 2026-09-14 00:00 이상
         * 2026-09-21 00:00 미만
         */

        LocalDate monday =
                date.with(
                        TemporalAdjusters.previousOrSame(
                                DayOfWeek.MONDAY
                        )
                );

        LocalDate nextMonday =
                monday.plusWeeks(1);

        return managerDAO.selectTotalSales(

                monday.atStartOfDay(),

                nextMonday.atStartOfDay()
        );
    }


    // =========================================================
    // 월별 매출 조회
    // =========================================================

    public int selectMonthlySales(
            int year,
            int month
    ) throws SQLException {

        /*
         * 예:
         *
         * 2026년 9월
         *
         * start:
         * 2026-09-01 00:00
         *
         * end:
         * 2026-10-01 00:00
         */

        YearMonth yearMonth =
                YearMonth.of(
                        year,
                        month
                );

        LocalDateTime start =
                yearMonth
                        .atDay(1)
                        .atStartOfDay();

        LocalDateTime end =
                yearMonth
                        .plusMonths(1)
                        .atDay(1)
                        .atStartOfDay();

        return managerDAO.selectTotalSales(
                start,
                end
        );
    }
}