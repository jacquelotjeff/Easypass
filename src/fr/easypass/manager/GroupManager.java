package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.model.Group;

public class GroupManager {
    
    private Map<Integer, Group> groups;
    
    private static final String TABLE_NAME = "groups";
    private static final String TABLE_NAME_REL_USERS = "user_group";
    
    private static final String COL_ID = "id"; 
    private static final String COL_NAME = "name";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_LOGO = "logo";
    
    private static final String COL_REL_USERS_USER_ID = "user_id";
    private static final String COL_REL_USERS_ADMIN = "admin";
    private static final String COL_REL_USERS_GROUP_ID = "group_id";
    
    public GroupManager()
    {
    }
    
    public Map<Integer, Group> getGroups() throws IOException {
		//Resetting the Hashmap (Prevent from caching groups into)
		this.groups = new HashMap<Integer, Group>();
		
		Connection conn = ConnectorManager.getConnection();

		try {
			
			//Not prepared statement
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from " + GroupManager.TABLE_NAME + ";");

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
    
    public Integer insertGroup(HttpServletRequest request) {

    	// TODO Validation for parameters
		String name = request.getParameter(GroupManager.COL_NAME);
		String description = request.getParameter(GroupManager.COL_DESCRIPTION);
		String logo = request.getParameter(GroupManager.COL_LOGO);
		String[] users = request.getParameterValues("users");
		//TODO get the administrators
		String[] admins = null;

		try {
			
			//On commence par insérer les groupe dans la table en récupérant son nouvel identifiant.
			Connection conn = ConnectorManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO groups("+ GroupManager.COL_NAME + 
					"," + GroupManager.COL_DESCRIPTION + 
					"," + GroupManager.COL_LOGO + 
					") values(?,?,?)", Statement.RETURN_GENERATED_KEYS
			);

			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setString(3, logo);
			
			stmt.executeUpdate();
			
			//Récupération de son identifiant puis on met à jour les relations utilisateurs.
			 try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                this.updateGroupUsers(generatedKeys.getInt(1), users, admins);
		            }
		            else {
		                throw new SQLException("Creating group failed, no ID obtained.");
		            }
		     }
			 
			 stmt.close();
			 conn.close();
			 
			 return 1;
			 

		} catch (Exception e) {
			e.printStackTrace();

			return 0;
		}
		
	}
    
    public Integer editGroup(HttpServletRequest request) throws IOException{
    	
    	String groupIdParam = request.getParameter("groupId");
		if (!NumberUtils.isNumber(groupIdParam)) {
			
			return 0;
			
		} else {
			
			Integer groupId = Integer.parseInt(groupIdParam);
			
			String name = request.getParameter(GroupManager.COL_NAME);
			String description = request.getParameter(GroupManager.COL_DESCRIPTION);
			String logo = request.getParameter(GroupManager.COL_LOGO);
			String[] users = request.getParameterValues("users");

			String[] admins = null;

			try {
				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt;

				stmt = conn.prepareStatement("UPDATE " + GroupManager.TABLE_NAME + 
				" SET " + GroupManager.COL_NAME + "=?, " +
				GroupManager.COL_DESCRIPTION + "=?, " +
				GroupManager.COL_LOGO + "=? WHERE id=?;");

				stmt.setString(1, name);
				stmt.setString(2, description);
				stmt.setString(3, logo);
				stmt.setInt(4, groupId);

				Integer number = stmt.executeUpdate();
				stmt.close();
				conn.close();
				
				//On met à jour les données du groupes
				this.updateGroupUsers(groupId, users, admins);

				return number;

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}

		}
    	
    }
    
    public Integer deleteGroup(HttpServletRequest request) throws IOException {

		String groupIdParam = request.getParameter("groupId");
		if (!NumberUtils.isNumber(groupIdParam)) {

			return 0;
		} else {
			
			final Integer groupId = Integer.parseInt(groupIdParam);
			this.deleteGroupUsers(groupId);

			try {
				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + GroupManager.TABLE_NAME + " WHERE " + GroupManager.COL_ID + "=?");
				
				stmt.setInt(1, groupId);

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

    
    private Integer updateGroupUsers(Integer groupId, String[] users, String[] admins) throws IOException
    {	
    	//First remove all relations with the group
    	this.deleteGroupUsers(groupId);    	
    	//Adding the new relations.
    	try {

			Connection conn = ConnectorManager.getConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO user_group("+ GroupManager.COL_REL_USERS_USER_ID + 
					"," + GroupManager.COL_REL_USERS_ADMIN + 
					"," + GroupManager.COL_REL_USERS_GROUP_ID + 
					") values(?,?,?)"
			);
			
			conn.setAutoCommit(false);
			
			for (String userId : users) {
				
				stmt.setString(1, userId);
				stmt.setBoolean(2, Arrays.asList(admins).contains(userId));
				stmt.setInt(3, groupId);
				
				stmt.addBatch();
			}

			stmt.executeBatch();
			conn.commit();
			
			stmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
    	
    	return 1;
    	
    }
    
    private Integer deleteGroupUsers(Integer groupId) throws IOException{
    	
    	Integer number = 0;
    	
    	try {
			Connection conn = ConnectorManager.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(
					"DELETE FROM " + GroupManager.TABLE_NAME_REL_USERS + 
					" WHERE " + GroupManager.COL_REL_USERS_GROUP_ID + "=?"
			);
			
			stmt.setInt(1, groupId);

			number = stmt.executeUpdate();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
    	
    	return number; 

    	
    }
    
    /**
     * Return Group object if existing into data
     * 
     * @param groupname
     * @return
     * @throws IOException 
     */
    public Group getGroup(HttpServletRequest request) throws IOException {
    	
    	String groupIdParam = request.getParameter("groupId");
		if (!NumberUtils.isNumber(groupIdParam)) {
			return null;
		} else {
			try {
				Connection conn = ConnectorManager.getConnection();
				PreparedStatement stmt;

				stmt = conn.prepareStatement("SELECT * from groups WHERE id=?");

				final Integer groupId = Integer.parseInt(request.getParameter("groupId").toString());
				stmt.setInt(1, groupId);

				ResultSet rs = stmt.executeQuery();
				
				Group group = null;
				while (rs.next()) {
					group = this.createFromResultSet(rs);
				}
						
				rs.close();
				stmt.close();
				conn.close();

				return group;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

    }
    	
    /**
     * Create a Group object from resultSet
     * @param rs
     * @return
     * @throws SQLException
     */
    private Group createFromResultSet(ResultSet rs) throws SQLException{
    	
    	Group group = new Group();

		group.setId(rs.getInt(GroupManager.COL_ID));
		group.setName(rs.getString(GroupManager.COL_NAME));
		group.setDescription(rs.getString(GroupManager.COL_DESCRIPTION));
		group.setLogo(rs.getString(GroupManager.COL_LOGO));

		return group;

    }

}
