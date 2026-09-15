package kosta.kiosk.bench;

import kosta.kiosk.model.dao.CouponDAO;
import kosta.kiosk.model.dao.CouponDAOImpl;
import kosta.kiosk.model.dto.CouponDTO;
import kosta.kiosk.util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CouponInsertBenchmark {

    private static final int TEST_USER_ID = 2;
    private static final int COUPONS_PER_RUN = 45;
    private static final int REPEAT = 5;

    public static void main(String[] args) throws SQLException {
        CouponDAO couponDAO = new CouponDAOImpl();
        Timestamp benchmarkStart = new Timestamp(System.currentTimeMillis());

        System.out.println("=== insertCoupon() 개별 반복 (매번 새 커넥션) ===");
        benchmarkIndividualInsert(couponDAO);

        System.out.println("\n=== insertCouponList() (con 공유, 배치 없음) ===");
        benchmarkListInsert(couponDAO);

        System.out.println("\n=== insertCouponListBatch() (con 공유, 배치 있음) ===");
        benchmarkListInsertBatch(couponDAO);

        cleanUp(benchmarkStart);
    }

    private static void benchmarkIndividualInsert(CouponDAO couponDAO) throws SQLException {
        long[] durations = new long[REPEAT];

        for (int run = 0; run < REPEAT; run++) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < COUPONS_PER_RUN; i++) {
                couponDAO.insertCoupon(new CouponDTO(TEST_USER_ID));
            }
            long end = System.currentTimeMillis();

            durations[run] = end - start;
            System.out.println("run " + (run + 1) + ": " + durations[run] + "ms");
        }

        printStats(durations);
    }

    private static void benchmarkListInsert(CouponDAO couponDAO) throws SQLException {
        long[] durations = new long[REPEAT];

        for (int run = 0; run < REPEAT; run++) {
            List<CouponDTO> coupons = buildCoupons();

            long start = System.currentTimeMillis();
            couponDAO.insertCouponList(coupons);
            long end = System.currentTimeMillis();

            durations[run] = end - start;
            System.out.println("run " + (run + 1) + ": " + durations[run] + "ms");
        }

        printStats(durations);
    }

    private static void benchmarkListInsertBatch(CouponDAO couponDAO) throws SQLException {
        long[] durations = new long[REPEAT];

        for (int run = 0; run < REPEAT; run++) {
            List<CouponDTO> coupons = buildCoupons();

            long start = System.currentTimeMillis();
            couponDAO.insertCouponListBatch(coupons);
            long end = System.currentTimeMillis();

            durations[run] = end - start;
            System.out.println("run " + (run + 1) + ": " + durations[run] + "ms");
        }

        printStats(durations);
    }

    private static List<CouponDTO> buildCoupons() {
        List<CouponDTO> coupons = new ArrayList<>();
        for (int i = 0; i < COUPONS_PER_RUN; i++) {
            coupons.add(new CouponDTO(TEST_USER_ID));
        }
        return coupons;
    }

    private static void printStats(long[] durations) {
        long sum = 0, min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (long d : durations) {
            sum += d;
            min = Math.min(min, d);
            max = Math.max(max, d);
        }
        System.out.println("avg=" + (sum / durations.length) + "ms, min=" + min + "ms, max=" + max + "ms");
    }

    private static void cleanUp(Timestamp since) throws SQLException {
        String sql = "DELETE FROM coupon WHERE user_id = ? AND created_at >= ?";
        try (Connection con = DbManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, TEST_USER_ID);
            pstmt.setTimestamp(2, since);
            int deleted = pstmt.executeUpdate();
            System.out.println("\ncleaned up " + deleted + " test coupons");
        }
    }
}
