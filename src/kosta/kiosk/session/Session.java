package kosta.kiosk.session;

import java.util.ArrayList;
import java.util.List;

import kosta.kiosk.model.dto.OrderDetail;
import kosta.kiosk.model.dto.UserDTO;

public class Session {
	private static final Session instance = new Session();

	private UserDTO user;
	private List<OrderDetail> carts=new ArrayList<>(); // 장바구니

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
	}

	public void logout() {
		this.user = null;
		this.carts = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Session [user=" + user + ", carts=" + carts + "]";
	}

}
