package kosta.kiosk.model.dto;

public class Menu {
	
	public enum HotIce {
		HOT, ICE
	}
	
	private int menuId;
	private String menuName;
	private String description;
	private int price;
	private HotIce hotIce;
	
	public Menu() {
		
	}

	public Menu(int menuId, String menuName, String description, int price, HotIce hotIce) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.description = description;
		this.price = price;
		this.hotIce = hotIce;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public HotIce getHotIce() {
		return hotIce;
	}

	public void setHotIce(HotIce hotIce) {
		this.hotIce = hotIce;
	}

	@Override
	public String toString() {
		return "메뉴번호 : " + menuId
				+ ", 메뉴명: " + menuName
				+ ", 가격: " + price + "원"
				+ ", 핫/아이스:  " + hotIce;	
	}
}