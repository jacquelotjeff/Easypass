package fr.easypass.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import fr.easypass.manager.UserManager;
import fr.easypass.model.Password;
import fr.easypass.model.User;

public class PasswordManager {
	
	HashMap<Integer, Password> passwords;

    /**
     * Return list of Users
     * 
     * @return
     */
    public HashMap<String, Password> getPasswords() {
    	
		//Resetting the Hashmap (Prevent from caching users into)
		this.passwords = new HashMap<>();
		
		Connection conn = ConnectorManager.getConnection();

		try {
			
			//Not prepared statement
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from passwords;");

			while (rs.next()) {
				
				Password password = this.createFromResultSet(rs);
				passwords.put(password.getId(), password);

			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return passwords;

    }

    /**
     * Return User object if existing into data
     * 
     * @param username
     * @return
     */
    public Password getPassword(String password) {
        return this.getPasswords().get(password);
    }
}
