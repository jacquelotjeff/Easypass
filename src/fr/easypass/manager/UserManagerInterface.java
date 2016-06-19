package fr.easypass.manager;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import fr.easypass.model.User;

public interface UserManagerInterface {
	public boolean checkLogin(String login);
	public boolean checkLoginWithPassword(String login, String password);
	public boolean createUser(String login, String password);
	public boolean editUser(String username, String password, String firstname, String lastname, String email);
	public boolean deleteUser(Integer id);
	public List<User> allUsers();
	public User getUser(String login);
	public Connection getConnection()  throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
}
