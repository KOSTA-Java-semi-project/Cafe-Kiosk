package kosta.kiosk.model.dto;

import java.time.LocalDateTime;

public class Menu {

    private int menuId;
    private int categoryId;
    private String menuName;
    private String description;
    private int price;
    private HotIce hotIce;
    private LocalDateTime createdAt;
    private boolean soldout;

    public Menu() {
    }

    // 기존 생성자
    public Menu(int menuId, int categoryId, String menuName,
                String description, int price, HotIce hotIce) {
        this.menuId = menuId;
        this.categoryId = categoryId;
        this.menuName = menuName;
        this.description = description;
        this.price = price;
        this.hotIce = hotIce;
    }

    // 전체 필드를 받는 생성자
    public Menu(int menuId, int categoryId, String menuName,
                String description, int price, HotIce hotIce,
                LocalDateTime createdAt, boolean soldout) {
        this(menuId, categoryId, menuName, description, price, hotIce);
        this.createdAt = createdAt;
        this.soldout = soldout;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSoldout() {
        return soldout;
    }

    public void setSoldout(boolean soldout) {
        this.soldout = soldout;
    }

    @Override
    public String toString() {
        return "Menu [menuId=" + menuId
                + ", categoryId=" + categoryId
                + ", menuName=" + menuName
                + ", description=" + description
                + ", price=" + price
                + ", hotIce=" + hotIce
                + ", createdAt=" + createdAt
                + ", soldout=" + soldout + "]";
    }
}
