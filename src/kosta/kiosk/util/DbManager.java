package kosta.kiosk.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbManager {
	private static Properties profile = new Properties();

	static {
		try {
			profile.load(new FileInputStream("resources/dbInfo.properties"));

			Class.forName(profile.getProperty("driverName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Properties getProfile() {
		return profile;
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				profile.getProperty("url"),
				profile.getProperty("userName"),
				profile.getProperty("password"));
	}
	
	public static void close(Connection con, Statement st, ResultSet rs) {
			try {
				if(rs!=null) rs.close();
				if(st!=null) st.close();
				if(con!=null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
