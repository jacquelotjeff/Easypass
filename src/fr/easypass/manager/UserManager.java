package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.model.User;

public class UserManager {

	private Map<Integer, User> users;
	
	private static final String TABLE_NAME = "users";
    private static final String TABLE_NAME_REL_USERS = "user_group";
    
    private static final String COL_ID = "id"; 
    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_LASTNAME = "lastname";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    
    private static final String COL_REL_GROUPS_USER_ID = "user_id";

	public UserManager() {
		this.users = new HashMap<>();
	}
	
	/**
	 * Return User object if existing into database
	 * 
	 * @param userId
	 * @return
	 * @throws IOException 
	 */
	public User getUser(HttpServletRequest request) throws IOException {
		
		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isNumber(userIdParam)) {
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
	 * Return list of Users from database
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Map<Integer, User> getUsers() throws IOException {
		
		//Resetting the Hashmap (Prevent from caching users into)
		this.users = new HashMap<>();
		
		Connection conn = ConnectorManager.getConnection();

		try {
			
			//Not prepared statement
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from " + UserManager.TABLE_NAME + ";");

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
		String username = request.getParameter(UserManager.COL_USERNAME);
		String firstname = request.getParameter(UserManager.COL_FIRSTNAME);
		String lastname = request.getParameter(UserManager.COL_LASTNAME);
		String password = request.getParameter(UserManager.COL_PASSWORD);
		String email = request.getParameter(UserManager.COL_EMAIL);

		try {

			Connection conn = ConnectorManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO " + UserManager.TABLE_NAME + 
					"(" + UserManager.COL_USERNAME + 
					", " + UserManager.COL_FIRSTNAME +
					", " + UserManager.COL_LASTNAME +
					", " + UserManager.COL_PASSWORD + 
					", " + UserManager.COL_EMAIL + ") values(?,?,?,?,?)");

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
	 * @throws IOException 
	 */
	public Integer editUser(HttpServletRequest request) throws IOException {

		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isNumber(userIdParam)) {
			return 0;
		} else {
			// TODO Validation for parameters
			String username = request.getParameter(UserManager.COL_USERNAME);
			String firstname = request.getParameter(UserManager.COL_FIRSTNAME);
			String lastname = request.getParameter(UserManager.COL_LASTNAME);
			String password = request.getParameter(UserManager.COL_PASSWORD);
			String email = request.getParameter(UserManager.COL_EMAIL);

			try {
				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt;

				stmt = conn.prepareStatement(
						"UPDATE " + UserManager.TABLE_NAME+
						" SET " + UserManager.COL_USERNAME + "=?, " +
						UserManager.COL_FIRSTNAME + "=?, " + 
						UserManager.COL_LASTNAME + "=?, " +
						UserManager.COL_PASSWORD + "=?, " +
						UserManager.COL_EMAIL + "=?" + " WHERE id=?");

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

	public Integer deleteUser(HttpServletRequest request) throws IOException {

		String userIdParam = request.getParameter("userId");
		if (!NumberUtils.isNumber(userIdParam)) {

			return 0;
		} else {
			
			final Integer userId = Integer.parseInt(request.getParameter("userId").toString());
			this.deleteGroupUsers(userId);

			try {
				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"DELETE FROM " + UserManager.TABLE_NAME +
						" WHERE " + UserManager.COL_ID + "=?");
				
				stmt.setInt(1, userId);

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
	
	private Integer deleteGroupUsers(Integer userId) throws IOException{
    	
    	Integer number = 0;
    	
    	try {
			Connection conn = ConnectorManager.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(
					"DELETE FROM " + UserManager.TABLE_NAME_REL_USERS + 
					" WHERE " + UserManager.COL_REL_GROUPS_USER_ID + "=?"
			);
			
			stmt.setInt(1, userId);

			number = stmt.executeUpdate();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
    	
    	return number; 

    	
    }

	/**
	 * Check if user exists into database
	 * 
	 * @param username
	 * @return
	 * @throws IOException 
	 */
	public Boolean checkLogin(String username) throws IOException {
		Map<Integer, User> users = this.getUsers();
		return users.containsKey(username);
	}

	/**
	 * Check if User and Password corresponds
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException 
	 */
	public Boolean checkLoginWithPassword(String username, String password) throws IOException {
		//TODO request by login and check the password
		Map<Integer, User> users = this.getUsers();
		User user = users.get(username);
		return (user.getPassword().equals(password));
	}

	/**
	 * Return a created User object from a ResultSet
	 * 
	 * @return
	 * @param ResultSet rs
	 * @throws SQLException
	 */
	private User createFromResultSet(ResultSet rs) throws SQLException {

		User user = new User();

		user.setId(rs.getInt(UserManager.COL_ID));
		user.setUsername(rs.getString(UserManager.COL_USERNAME));
		user.setPassword(rs.getString(UserManager.COL_PASSWORD));
		user.setFirstname(rs.getString(UserManager.COL_FIRSTNAME));
		user.setLastname(rs.getString(UserManager.COL_LASTNAME));
		user.setEmail(rs.getString(UserManager.COL_EMAIL));

		return user;
	}

}
