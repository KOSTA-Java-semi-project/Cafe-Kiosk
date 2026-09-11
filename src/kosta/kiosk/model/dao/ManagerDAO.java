package kosta.kiosk.model.dao;

import java.sql.SQLException;
import kosta.kiosk.model.dto.ManagerDTO;

public interface ManagerDAO {
	
	/*
	 * Manager 로그인
	 * 
	 * @param id manager login ID
	 * @param password manager password
	 * @return ManagerDTO 만약 로그인이 성공하면 
	 * 		만약 로그인이 실패했으면 null.
	 */
	
	ManagerDTO login(String id, String password) throws SQLException;
}
