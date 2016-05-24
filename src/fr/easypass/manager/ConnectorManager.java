package fr.easypass.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorManager {
	
	public static Connection getConnection(){
		
		String url = "jdbc:mysql://localhost:3306/easypass";
		String user = "root";
		String password = "";
		
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

}
