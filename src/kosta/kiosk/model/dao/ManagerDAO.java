package kosta.kiosk.model.dao;

import java.sql.SQLException;

import kosta.kiosk.model.dto.ManagerDTO;

public interface ManagerDAO {

    /**
     * 관리자 로그인
     *
     * @param id 관리자 로그인 ID
     * @param password 관리자 비밀번호
     * @return 로그인 성공 시 ManagerDTO,
     *         실패 시 null
     * @throws SQLException DB 처리 중 오류 발생 시
     */
    ManagerDTO login(String id, String password) throws SQLException;
}