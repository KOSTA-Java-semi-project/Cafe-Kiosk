package kosta.kiosk.session;

import java.util.Map;

import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.UserDTO;

public class Session {
	// TODO 싱글톤 구현
	private static final Session instance = new Session();

	private String sessionId;
	private UserDTO user;

	private Map<Integer, OrderDetail> carts; // 장바구니

	private Session() {

	}

	public static Session getInstance() {
		return instance;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Map<Integer, OrderDetail> getCarts() {
		return carts;
	}

	public void setCarts(Map<Integer, OrderDetail> carts) {
		this.carts = carts;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", user=" + user + ", carts=" + carts + "]";
	}

}
