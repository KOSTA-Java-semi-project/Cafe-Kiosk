package kosta.kiosk.model.dto;

public class OrderDetail {

    private int detailId;
    private int orderId;
    private final int menuId;
    private int amount;
    private final Size size;
    private final int shot;
    private final IceLevel ice;
    private final int syrup;

    /** 장바구니 담기용: orderId/detailId 둘 다 아직 정해지지 않았으므로 제외 */
    public OrderDetail(int menuId, int amount, Size size, int shot, IceLevel ice, int syrup) {
        this.menuId = menuId;
        this.amount = amount;
        this.size = size;
        this.shot = shot;
        this.ice = ice;
        this.syrup = syrup;
    }

    /** 조회용: DB에서 읽은 전체 필드 */
    public OrderDetail(int detailId, int orderId, int menuId, int amount, Size size, int shot, IceLevel ice, int syrup) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.amount = amount;
        this.size = size;
        this.shot = shot;
        this.ice = ice;
        this.syrup = syrup;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Size getSize() {
        return size;
    }

    public int getShot() {
        return shot;
    }

    public IceLevel getIce() {
        return ice;
    }

    public int getSyrup() {
        return syrup;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "detailId=" + detailId +
                ", orderId=" + orderId +
                ", menuId=" + menuId +
                ", amount=" + amount +
                ", size=" + size +
                ", shot=" + shot +
                ", ice=" + ice +
                ", syrup=" + syrup +
                '}';
    }
}