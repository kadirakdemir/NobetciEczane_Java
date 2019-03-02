package DataConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class NobetciDBConnection {
	 public static final String URL="jdbc:sqlserver://78.191.231.150:1433;database=EczaneDB;user=eeczacim;password=123456;";
	 public static Connection connection;
	    
	 public static boolean Connect() {
		 try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection=DriverManager.getConnection(URL);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			 System.err.println("Got an exception! ");
	         System.err.println(e.getMessage());
		}
		 return false;
	 }
	 
	 public static Statement setStatement() {
		 try {
			return connection.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			 System.err.println("Got an exception! ");
	         System.err.println(e.getMessage());
		}
		 return null;
	 }
}
