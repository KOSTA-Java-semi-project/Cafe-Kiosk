package dto;

public class CouponDTO {private int couponId;
    private int userId;
    private int price;

    public CouponDTO(int couponId, int userId, int price) {
        this.couponId = couponId;
        this.userId = userId;
        this.price = price;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
