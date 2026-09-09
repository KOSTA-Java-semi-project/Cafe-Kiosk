package kosta.kiosk.model.dao;

import kosta.kiosk.model.dto.Order;
import kosta.kiosk.model.dto.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    /**
     * 특정 회원의 주문 내역 전체 조회 (주문 이력).
     *
     * @param userId 회원 번호
     * @return 해당 회원의 주문 목록 (없으면 빈 리스트)
     */
    List<Order> orderSelectByUser(int userId) throws SQLException;

    /**
     * 주문(order) 1건과 그에 속한 주문상세(order_detail) 목록을
     * 하나의 트랜잭션으로 등록하고, DB가 생성한 주문번호(order_id)를 반환한다.
     *
     * @param order 등록할 주문. orderDetailList 에 주문상세가 채워져 있어야 한다.
     * @return 생성된 order_id
     */
    int insertOrder(Order order) throws SQLException;

    /**
     * 주문번호로 주문 1건 조회. 반환되는 Order 의 orderDetailList 까지 채워진다.
     *
     * @param orderId 주문 번호
     * @return 주문 1건, 없으면 null
     */
    Order selectOrderByOrderId(int orderId) throws SQLException;

    /**
     * 주문번호에 속한 주문상세 목록만 조회한다. (orderSelectByNo 내부에서 재사용)
     *
     * @param orderId 주문 번호
     * @return 주문상세 목록 (없으면 빈 리스트)
     */
    List<OrderDetail> selectOrderDetails(int orderId) throws SQLException;
}
