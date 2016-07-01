package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.easypass.model.Group;

public class GroupManager {

    private Map<Integer, Group> groups;

    public static final String TABLE = "groups";
    public static final String TABLE_REL_USERS = "user_group";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_LOGO = "logo";

    public static final String COL_FOREIGN = "group_id";
    
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());

    public GroupManager() {
        //Constructors do nothing (no initialing necessary)
    }

    /**
     * Return Group object if existing into data
     * 
     * @param groupname
     * @return
     * @throws IOException
     */
    public Group getGroup(Integer groupId) throws IOException {

        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("SELECT * from groups WHERE id=?");
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
            log.log(Level.SEVERE, "SQL error requesting", e);
            return null;
        }

    }

    public Map<Integer, Group> getGroups() throws IOException {
        // Resetting the Hashmap (Prevent from caching groups into)
        this.groups = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();

        try {

            // Not prepared statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + GroupManager.TABLE + ";");

            while (rs.next()) {

                Group group = this.createFromResultSet(rs);
                groups.put(group.getId(), group);

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL error requesting", e);
        }

        return groups;
    }

    public Integer insertGroup(String name, String description, String logo, List<String> users, List<String> admins) {

        try {

            // On commence par insérer les groupe dans la table en récupérant
            // son nouvel identifiant.
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO groups(" + GroupManager.COL_NAME + ","
                    + GroupManager.COL_DESCRIPTION + "," + GroupManager.COL_LOGO + ") values(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, logo);

            stmt.executeUpdate();            

            // Récupération de son identifiant puis on met à jour les relations
            // utilisateurs.
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                
                if (generatedKeys.next()) {
                    this.addUsers(generatedKeys.getInt(1), users, admins);
                } else {
                    throw new SQLException("Creating group failed, no ID obtained.");
                }
                
            } finally {
                stmt.close();
            }
            
            conn.close();

            return 1;

        } catch (Exception e) {
            log.log(Level.SEVERE, "SQL error requesting", e);

            return 0;
        }

    }

    public Integer editGroup(Integer groupId, String name, String description, String logo) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("UPDATE " + GroupManager.TABLE + " SET " + GroupManager.COL_NAME + "=?, "
                    + GroupManager.COL_DESCRIPTION + "=?, " + GroupManager.COL_LOGO + "=? WHERE id=?;");

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, logo);
            stmt.setInt(4, groupId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL error requesting", e);
            return 0;
        }

    }

    public Integer deleteGroup(Integer groupId) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM " + GroupManager.TABLE + " WHERE " + GroupManager.COL_ID + "=?");

            stmt.setInt(1, groupId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {

            log.log(Level.SEVERE, "SQL error requesting", e);
            return 0;
        }

    }

    public Integer addUsers(Integer groupId, List<String> users, List<String> admins) throws IOException {
        // Adding the new relations.
        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_group("
                    + UserManager.COL_FOREIGN + "," + UserManager.COL_ADMIN + ","
                    + GroupManager.COL_FOREIGN + ") values(?,?,?)");

            conn.setAutoCommit(false);

            for (String userId : users) {

                stmt.setString(1, userId);
                stmt.setBoolean(2, admins.contains(userId));
                stmt.setInt(3, groupId);

                stmt.addBatch();
            }

            stmt.executeBatch();
            conn.commit();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            log.log(Level.SEVERE, "SQL error requesting", e);
            return 0;
        }

        return 1;

    }

    public Integer addUser(Integer groupId, Integer userId) throws IOException {
        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_group("
                    + UserManager.COL_FOREIGN + "," + UserManager.COL_ADMIN + ","
                    + GroupManager.COL_FOREIGN + ") values(?,?,?)");

            stmt.setInt(1, userId);
            stmt.setBoolean(2, false);
            stmt.setInt(3, groupId);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            log.log(Level.SEVERE, "SQL error requesting", e);
            return 0;
        }

        return 1;

    }

    public Integer deleteUser(Integer groupId, Integer userId) throws IOException {

        Integer number = 0;
        String sql = "";

        try {

            sql = "DELETE FROM " + GroupManager.TABLE_REL_USERS + " WHERE " + GroupManager.COL_FOREIGN
                    + "=?" + " AND " + UserManager.COL_FOREIGN + "=?;";
            Connection conn = ConnectorManager.getConnection();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, groupId);
            stmt.setInt(2, userId);

            number = stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL error requesting", e);
            System.out.println(sql);
        }

        return number;

    }

    public Integer setUserAdmin(Integer groupId, Integer userId, Boolean admin) throws IOException {

        Integer number = 0;

        try {
            Connection conn = ConnectorManager.getConnection();

            PreparedStatement stmt = conn.prepareStatement("UPDATE " + GroupManager.TABLE_REL_USERS + " SET "
                    + UserManager.COL_ADMIN + "=? " + " WHERE " + GroupManager.COL_FOREIGN + "=?"
                    + " AND " + UserManager.COL_FOREIGN + "=?");

            stmt.setBoolean(1, admin);
            stmt.setInt(2, groupId);
            stmt.setInt(3, userId);

            number = stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL error requesting", e);

        }

        return number;

    }
    
    /**
     * @param userId
     * @return Map<String, Map<Integer, Group>> "groups" contains all User groups, "groupsAdmin" contains all groups wich user is admin
     * @throws IOException
     */
    public Map<String, Map<Integer, Group>> getGroupByUsers(Integer userId) throws IOException{
    	
    	 // Resetting the Hashmap (Prevent from caching users into)
        Map<String, Map<Integer, Group>> result = new HashMap<>();
        Map<Integer, Group> groups = new HashMap<>();
        Map<Integer, Group> groupsAdmin = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();

        try {

            String query = "SELECT DISTINCT * from " + GroupManager.TABLE_REL_USERS + " INNER JOIN "
                    + GroupManager.TABLE + " u ON u." + GroupManager.COL_ID + " = "
                    + GroupManager.TABLE_REL_USERS + "." + COL_FOREIGN + " WHERE "
                    + UserManager.COL_FOREIGN + "=?";

            // Not prepared statement
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Boolean admin = rs.getBoolean("admin");
                Group group = this.createFromResultSet(rs);
                groups.put(group.getId(), group);

                if (admin) {
                    groupsAdmin.put(group.getId(), group);
                }

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            log.log(Level.SEVERE, "SQL error requesting", e);

        }

        result.put("groups", groups);
        result.put("groupsAdmin", groupsAdmin);

        return result;
    }

    /**
     * Create a Group object from resultSet
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private Group createFromResultSet(ResultSet rs) throws SQLException {

        Group group = new Group();

        group.setId(rs.getInt(GroupManager.COL_ID));
        group.setName(rs.getString(GroupManager.COL_NAME));
        group.setDescription(rs.getString(GroupManager.COL_DESCRIPTION));
        group.setLogo(rs.getString(GroupManager.COL_LOGO));

        return group;

    }

}
