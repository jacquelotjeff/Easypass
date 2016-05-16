package fr.easypass.db;

import java.util.HashMap;

import fr.easypass.model.User;


public class UserManager {
	
	/**
	 * Return list of Users
	 * @return
	 */
	public HashMap<String, User> getUsers() {
		
		HashMap<String, User> users = new HashMap<>();
		User user = new User();
		
		user.setFirstname("Adrien");
		user.setLastname("Turcey");
		user.setEmail("adrienturcey@outlook.com");
		user.setUsername("aturcey");
		user.setPassword("admin1234");
		
		users.put(user.getUsername(), user);
		
		User user1 = new User();
        
        user1.setFirstname("Jonathan");
        user1.setLastname("Cholet");
        user1.setEmail("jonathancholet@gmail.com");
        user1.setUsername("jcholet");
        user1.setPassword("admin1234");
		
		users.put(user1.getUsername(), user1);
		
		return users;

	}
	
	/**
	 * Check if user exists into database
	 * @param username
	 * @return
	 */
	public Boolean checkLogin(String username){
		HashMap<String, User> users = this.getUsers();
		return users.containsKey(username);
	}
	
	/**
	 * Check if User and Password corresponds
	 * @param username
	 * @param password
	 * @return
	 */
	public Boolean checkLoginWithPassword(String username, String password){
		HashMap<String, User> users = this.getUsers();
		User user = users.get(username);
		return (user.getPassword().equals(password) );
	}
		
	/**
	 * Return User object if existing into data
	 * @param username
	 * @return
	 */
	public User getUser(String username){
		return this.getUsers().get(username);
	}

}
