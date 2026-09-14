package kosta.kiosk.model.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;

import kosta.kiosk.model.dto.ManagerDTO;

public interface ManagerDAO {

	/*
	 * 관리자 로그인
	 */
	ManagerDTO login(String id, String password) throws SQLException;

	/*
	 * 지정된 기간의 총 매출 조회
	 *
	 * start : 시작 시간 포함 end : 종료 시간 미포함
	 */
	int selectTotalSales(LocalDateTime start, LocalDateTime end) throws SQLException;
}