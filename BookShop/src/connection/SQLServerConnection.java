package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLServerConnection {
	
	
	// Ham ket noi voi sql server
	public static Connection getConnection() {
		 String user_name = "sa";
		 String Pass = "a123456";
		 String URL = "jdbc:sqlserver://localhost:1433;" + "databaseName=DB;";
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(URL, user_name, Pass);
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return connection;
	}
}
