package kosta.kiosk.model.dto;

public enum Size {

	SMALL(-500), MEDIUM(0), LARGE(500);

	private final int price;

	Size(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}
}
