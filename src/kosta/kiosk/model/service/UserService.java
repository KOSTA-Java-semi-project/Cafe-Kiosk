package kosta.kiosk.model.service;

import kosta.kiosk.model.dao.CouponDAO;
import kosta.kiosk.model.dao.CouponDAOImpl;
import kosta.kiosk.model.dao.UserDAO;
import kosta.kiosk.model.dao.UserDAOImpl;
import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.model.dto.UserDTO;

import java.sql.SQLException;

public class UserService {

    private static final int STAMP_GOAL = 10; // 스탬프 10개 -> 쿠폰 1장

    private final UserDAO userDAO = new UserDAOImpl();
    private final CouponDAO couponDAO = new CouponDAOImpl();

    public UserDTO login(String phone) throws SQLException {
        return userDAO.selectUserByPhone(phone);
    }

    public UserDTO signUp(String name, String phone) throws SQLException {
        UserDTO newUser = new UserDTO(name, phone); // userId, stamp 둘 다 자동으로 처리됨
        int generatedId = userDAO.insertUser(newUser);

        newUser.setUserId(generatedId);
        return newUser;
    }

    public void processPayment(UserDTO user, int quantity) throws SQLException {
        int totalStamp = user.getStamp() + quantity; // 1잔당 스탬프 1개

        int couponsEarned = totalStamp / STAMP_GOAL;
        int remainStamp = totalStamp % STAMP_GOAL;

        userDAO.updateUserStampByUserId(user.getUserId(), remainStamp);
        user.setStamp(remainStamp); // 메모리에 있는 객체도 최신화

        for (int i = 0; i < couponsEarned; i++) {
            CouponDTO newCoupon = new CouponDTO(user.getUserId()); // couponId, price, createdAt 모두 DB가 채움
            couponDAO.insertCoupon(newCoupon);
        }
    }
}