package fr.easypass.manager;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import fr.easypass.model.User;

public class UserManagerDB implements UserManagerInterface{
	
	private Connection connection;
	
	public UserManagerDB() 
	{
		try {
			this.connection = this.getConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		Properties prop = new Properties();
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/easypass";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = (Connection) DriverManager.getConnection(url, "root", "root");
		return connection;
	}
	
	
	@Override
	public boolean checkLogin(String login) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkLoginWithPassword(String login, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createUser(String login, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editUser(String username, String password, String firstname, String lastname, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> allUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String login) {
		// TODO Auto-generated method stub
		return null;
	}

}
