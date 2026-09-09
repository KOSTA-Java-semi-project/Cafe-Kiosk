package kosta.kiosk.model.dto;

public class Menu {
	public enum HotIce {
		HOT, ICE
	}
	
	private int menuId;
	private int categoryId;
	private String menuName;
	private String description;
	private int price;
	private HotIce hotIce;
	
	public Menu() {
		
	}

	public Menu(int menuId, int categoryId, String menuName, String description, int price, HotIce hotIce) {
		super();
		this.menuId = menuId;
		this.categoryId = categoryId;
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
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
        return "MenuDTO [menuId=" + menuId
                + ", categoryId=" + categoryId
                + ", menuName=" + menuName
                + ", price=" + price
                + ", hotIce=" + hotIce + "]";	
	}
}

