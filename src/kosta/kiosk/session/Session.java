package kosta.kiosk.session;

import java.util.ArrayList;
import java.util.List;

import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.UserDTO;

public class Session {
	private static final Session instance = new Session();

	private UserDTO user;
	private List<OrderDetail> carts = new ArrayList<>(); // 장바구니 (비회원도 담을 수 있도록 기본값으로 초기화)

	private Session() {
	}

	public static Session getInstance() {
		return instance;
	}

	public UserDTO getUser() {
		return user;
	}

	public List<OrderDetail> getCarts() {
		return carts;
	}

	public void login(UserDTO user) {
		this.user = user;
		this.carts = new ArrayList<>();
	}

	public void logout() {
		this.user = null;
		this.carts = new ArrayList<>(); // null 대신 빈 장바구니로 초기화 -> 다음 손님(비회원 포함) 바로 이용 가능
	}

	@Override
	public String toString() {
		return "Session [user=" + user + ", carts=" + carts + "]";
	}

}
