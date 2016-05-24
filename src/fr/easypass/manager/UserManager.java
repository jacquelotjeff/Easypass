package fr.easypass.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import fr.easypass.model.Group;
import fr.easypass.model.User;

public class UserManager {
    
    private HashMap<Integer, User> users;
    
    public UserManager(){
        this.users = new HashMap<>();
    }

    public HashMap<Integer, User> getUsers(){
    	
    	Connection conn = ConnectorManager.getConnection();
    	
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from users;");
			
			while (rs.next()) {
				
				User user = this.createFromResultSet(rs);
				users.put(user.getId(), user);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        return users;
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
    public User getUser(Integer userId) {
        return this.users.get(userId);
    }
    
    /**
     * Return 
     * @return
     * @throws SQLException 
     */
    public User createFromResultSet(ResultSet rs) throws SQLException{
    	
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
