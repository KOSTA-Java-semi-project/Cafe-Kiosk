package kosta.kiosk.model.dto;

public class OrderDetail {

    private int detailId;
    private int orderId;
    private int menuId;
    private int amount;
    private Size size;
    private int shot;
    private IceLevel ice;
    private int syrup;

    public OrderDetail() {
    }

    /** 등록용: detailId는 DB가 생성하므로 제외 */
    public OrderDetail(int orderId, int menuId, int amount, Size size, int shot, IceLevel ice, int syrup) {
        this.orderId = orderId;
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

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public void setSize(Size size) {
        this.size = size;
    }

    public int getShot() {
        return shot;
    }

    public void setShot(int shot) {
        this.shot = shot;
    }

    public IceLevel getIce() {
        return ice;
    }

    public void setIce(IceLevel ice) {
        this.ice = ice;
    }

    public int getSyrup() {
        return syrup;
    }

    public void setSyrup(int syrup) {
        this.syrup = syrup;
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