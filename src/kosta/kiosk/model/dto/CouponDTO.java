package kosta.kiosk.model.dto;

import java.time.LocalDateTime;

public class CouponDTO {private int couponId;
    private int userId;
    private int price;
    private LocalDateTime createdAt;

    public CouponDTO(int couponId, int userId, int price, LocalDateTime createdAt) {
        this.couponId = couponId;
        this.userId = userId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public CouponDTO(int userId) {
        this.userId = userId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

