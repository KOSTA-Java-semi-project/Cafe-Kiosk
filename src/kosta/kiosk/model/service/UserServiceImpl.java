package kosta.kiosk.model.service;

import kosta.kiosk.model.dao.CouponDAO;
import kosta.kiosk.model.dao.CouponDAOImpl;
import kosta.kiosk.model.dao.UserDAO;
import kosta.kiosk.model.dao.UserDAOImpl;
import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.model.dto.UserDTO;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private static final int STAMP_GOAL = 10;
    private final UserDAO userDAO = new UserDAOImpl();
    private final CouponDAO couponDAO = new CouponDAOImpl();

    @Override
    public UserDTO login(String phone) throws SQLException {
        return userDAO.selectUserByPhone(phone);
    }

    @Override
    public UserDTO signUp(String name, String phone) throws SQLException {
        UserDTO newUser = new UserDTO(name,phone); //UserId는 오버로딩, 스탬프는 기본값이 0이니까 제외.
        int generatedId = userDAO.insertUser(newUser);

        newUser.setUserId(generatedId);
        return newUser;
    }

    @Override
    public void processPayment(UserDTO user, int quantity) throws SQLException {
        int totalStamp = user.getStamp()+quantity;

        int couponsEarned = totalStamp / STAMP_GOAL;
        int remainStamp = totalStamp % STAMP_GOAL;

        userDAO.updateUserStampByUserId(user.getUserId(), remainStamp);
        user.setStamp(remainStamp);

        for (int i = 0; i <couponsEarned; i++) {
            CouponDTO newCoupon = new CouponDTO(user.getUserId());
            couponDAO.insertCoupon(newCoupon);
        }
        }
    }

