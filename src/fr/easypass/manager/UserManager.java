package fr.easypass.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;

import fr.easypass.model.User;

public class UserManager {

	private HashMap<Integer, User> users;

	public UserManager() {
		this.users = new HashMap<>();
	}

	/**
	 * Return list of Users from database
	 * 
	 * @return
	 */
	public HashMap<Integer, User> getUsers() {
		
		//Resetting the Hashmap (Prevent from caching users into)
		this.users = new HashMap<>();
		
		Connection conn = ConnectorManager.getConnection();

		try {
			
			//Not prepared statement
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from users;");

			while (rs.next()) {
				
				User user = this.createFromResultSet(rs);
				users.put(user.getId(), user);

			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public Integer insertUser(HttpServletRequest request) {

		// TODO Validation for parameters
		String username = request.getParameter("username");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		try {

			Connection conn = ConnectorManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO users(username, firstname, lastname, password, email) values(?,?,?,?,?)");

			stmt.setString(1, username);
			stmt.setString(2, firstname);
			stmt.setString(3, lastname);
			stmt.setString(4, password);
			stmt.setString(5, email);

			Integer number = stmt.executeUpdate();
			stmt.close();
			conn.close();

			return number;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Edit a User in database
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	public Integer editUser(HttpServletRequest request) {

		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isParsable(userIdParam)) {
			return 0;
		} else {

			// TODO Validation for parameters
			String username = request.getParameter("username");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String password = request.getParameter("password");
			String email = request.getParameter("email");

			try {

				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt;

				stmt = conn.prepareStatement("UPDATE users "
						+ "SET username=?, firstname=?, lastname=?, password=?, email=?" + "WHERE id=?");

				stmt.setString(1, username);
				stmt.setString(2, firstname);
				stmt.setString(3, lastname);
				stmt.setString(4, password);
				stmt.setString(5, email);

				final Integer userId = Integer.parseInt(request.getParameter("userId").toString());
				stmt.setInt(6, userId);

				Integer number = stmt.executeUpdate();
				stmt.close();
				conn.close();

				return number;

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}

		}
	}

	public Integer deleteUser(HttpServletRequest request) {

		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isParsable(userIdParam)) {

			return 0;
		} else {

			try {

				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id=?");
				
				final Integer userId = Integer.parseInt(request.getParameter("userId").toString());
				stmt.setInt(1, userId);

				Integer number = stmt.executeUpdate();
				stmt.close();
				conn.close();

				return number;

			} catch (SQLException e) {

				e.printStackTrace();
				return null;
			}
		}

	}

	/**
	 * Check if user exists into database
	 * 
	 * @param username
	 * @return
	 */
	public Boolean checkLogin(String username) {
		HashMap<Integer, User> users = this.getUsers();
		return users.containsKey(username);
	}

	/**
	 * Check if User and Password corresponds
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Boolean checkLoginWithPassword(String username, String password) {
		HashMap<Integer, User> users = this.getUsers();
		User user = users.get(username);
		return (user.getPassword().equals(password));
	}

	/**
	 * Return User object if existing into database
	 * 
	 * @param userId
	 * @return
	 */
	public User getUser(HttpServletRequest request) {
		
		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isParsable(userIdParam)) {
			return null;
		} else {

			try {

				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt;

				stmt = conn.prepareStatement("SELECT * from users WHERE id=?");

				final Integer userId = Integer.parseInt(request.getParameter("userId").toString());
				stmt.setInt(1, userId);

				ResultSet rs = stmt.executeQuery();
				
				User user = null;
				while (rs.next()) {
					user = this.createFromResultSet(rs);
				}
						
				rs.close();
				stmt.close();
				conn.close();

				return user;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

	}

	/**
	 * Return a created User object from a ResultSet
	 * 
	 * @return
	 * @param ResultSet rs
	 * @throws SQLException
	 */
	public User createFromResultSet(ResultSet rs) throws SQLException {

		User user = new User();

		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstname(rs.getString("firstname"));
		user.setLastname(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));

		return user;

	}

}
