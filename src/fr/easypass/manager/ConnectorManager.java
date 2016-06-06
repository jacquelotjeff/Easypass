package fr.easypass.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorManager {
	
//	public static InputStream getParameters() throws IOException {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//	    InputStream stream = classLoader.getResourceAsStream("/WebContent/WEB-INF/config/config.properties");
//	    
//        return stream;
//	}
	
	
	public static Connection getConnection() throws IOException {
//		InputStream parameters = getParameters();
//		
//		Properties p = new Properties();
//		p.load(parameters);
		
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
