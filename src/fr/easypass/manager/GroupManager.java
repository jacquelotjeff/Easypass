package fr.easypass.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


import fr.easypass.model.Group;
import fr.easypass.model.User;

public class GroupManager {
    
    private HashMap<Integer, Group> groups;
    
    public GroupManager()
    {
    }
    
    public HashMap<Integer, Group> getGroups() {
		//Resetting the Hashmap (Prevent from caching groups into)
		this.groups = new HashMap<>();
		
		Connection conn = ConnectorManager.getConnection();

		try {
			
			//Not prepared statement
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from groups;");

			while (rs.next()) {
				
				Group group = this.createFromResultSet(rs);
				groups.put(group.getId(), group);

			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groups;
    }
    
    /**
	 * Return a created Group object from a ResultSet
	 * 
	 * @return
	 * @param ResultSet rs
	 * @throws SQLException
	 */
	public Group createFromResultSet(ResultSet rs) throws SQLException {

		Group group = new Group();
		return group;

	}
    
    /**
     * Return Group object if existing into data
     * 
     * @param groupname
     * @return
     */
    public Group getGroup(String groupname) {
        return this.groups.get(groupname);
    }

}
