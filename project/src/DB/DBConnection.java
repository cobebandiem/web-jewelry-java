package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection createConnection() {
		Connection conn = null;
        String uRL = "jdbc:sqlserver://localhost;databaseName=JEWELRY_DEMO";
        String userName = "sa";
        String password = "123456";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(uRL, userName, password);
            if (conn != null) {
                System.out.println("Database done!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("That bai");
        }
        return conn;
	}
}
