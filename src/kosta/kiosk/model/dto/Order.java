package kosta.kiosk.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private int orderId;
    private Integer userId;
    private int sum;
    private List<OrderDetail> orderDetailList; // 주문상세 목록 (조회 시 채워짐)
    private LocalDateTime createdAt;

    public Order() {
    }

    /** 등록용: orderId, createdAt은 DB가 생성하므로 제외 */
    public Order(Integer userId, int sum, List<OrderDetail> orderDetailList) {
        this.userId = userId;
        this.sum = sum;
        this.orderDetailList = orderDetailList;
    }

    /** 조회용: DB에서 읽은 전체 필드 */
    public Order(int orderId, Integer userId, int sum, List<OrderDetail> orderDetailList, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.sum = sum;
        this.orderDetailList = orderDetailList;
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", sum=" + sum +
                ", orderDetailList=" + orderDetailList +
                ", createdAt=" + createdAt +
                '}';
    }
}
