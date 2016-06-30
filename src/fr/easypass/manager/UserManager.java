package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import fr.easypass.model.User;

public class UserManager {

    private Map<Integer, User> users;

    public static final String TABLE = "users";
    public static final String TABLE_REL_GROUP = "user_group";

    public static final String COL_ID = "id";
    public static final String COL_FIRSTNAME = "firstname";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_FOREIGN = "user_id";
    public static final String COL_ADMIN = "admin";

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
    public User getUser(Integer userId) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("SELECT * from users WHERE id=?");

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

    /**
     * Return list of Users from database
     * 
     * @return
     * @throws IOException
     */
    public Map<Integer, User> getUsers() throws IOException {

        // Resetting the Hashmap (Prevent from caching users into)
        this.users = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();

        try {

            // Not prepared statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + UserManager.TABLE + ";");

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

    /**
     * Return list of user by group
     * 
     * @return
     * @param Integer
     *            The group identifiant
     * @throws IOException
     */
    public Map<String, Map<Integer, User>> getUsersByGroup(Integer groupId) throws IOException {

        // Resetting the Hashmap (Prevent from caching users into)
        Map<String, Map<Integer, User>> result = new HashMap<>();
        Map<Integer, User> groupUsers = new HashMap<>();
        Map<Integer, User> groupAdmins = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();

        try {

            String query = "SELECT DISTINCT * from " + UserManager.TABLE_REL_GROUP + " INNER JOIN "
                    + UserManager.TABLE + " u ON u." + UserManager.COL_ID + " = "
                    + UserManager.TABLE_REL_GROUP + "." + COL_FOREIGN + " WHERE "
                    + GroupManager.COL_FOREIGN + "=?";

            // Not prepared statement
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, groupId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Boolean admin = rs.getBoolean("admin");
                User user = this.createFromResultSet(rs);
                groupUsers.put(user.getId(), user);

                if (admin) {
                    groupAdmins.put(user.getId(), user);
                }

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        result.put("groupUsers", groupUsers);
        result.put("groupAdmins", groupAdmins);

        return result;
    }

    /**
     * Return list of users not present in group
     * 
     * @return
     * @param Integer
     *            The group identifiant
     * @throws IOException
     */
    public Map<Integer, User> getUsersAvailableByGroup(Integer groupId) throws IOException {

        // Resetting the Hashmap (Prevent from caching users into)
        Map<Integer, User> groupUsersAvailable = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();
        String query = "";
        try {

            query = "SELECT * from " + UserManager.TABLE + " WHERE " + UserManager.COL_ID + " NOT IN(" + "SELECT "
                    + UserManager.COL_FOREIGN + " FROM " + UserManager.TABLE_REL_GROUP + " WHERE "
                    + GroupManager.COL_FOREIGN + "=?);";
            // Not prepared statement
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, groupId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                User user = this.createFromResultSet(rs);
                groupUsersAvailable.put(user.getId(), user);

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(query);
            e.printStackTrace();
        }

        return groupUsersAvailable;
    }

    public Integer insertUser(User user) {

        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + UserManager.TABLE + "("
                    + UserManager.COL_USERNAME + ", " + UserManager.COL_FIRSTNAME + ", " + UserManager.COL_LASTNAME
                    + ", " + UserManager.COL_PASSWORD + ", " + UserManager.COL_EMAIL + ", " + UserManager.COL_ADMIN + ") values(?,?,?,?,?,?)");

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getLastname());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getEmail());
            stmt.setBoolean(6, user.getAdmin());

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (Exception e) {
            e.printStackTrace();

            return 0;
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
    public Integer editUser(Integer userId, User user) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("UPDATE " + UserManager.TABLE + " SET " + UserManager.COL_USERNAME
                    + "=?, " + UserManager.COL_FIRSTNAME + "=?, " + UserManager.COL_LASTNAME + "=?, "
                    + UserManager.COL_PASSWORD + "=?, " + UserManager.COL_EMAIL + "=?," + UserManager.COL_ADMIN + "=?" + " WHERE id=?");

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getLastname());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getEmail());
            stmt.setBoolean(6, user.getAdmin());

            stmt.setInt(7, userId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Integer deleteUser(Integer userId) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM " + UserManager.TABLE + " WHERE " + UserManager.COL_ID + "=?");

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
     * Check if email and Password corresponds
     * 
     * @param email
     * @param password
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    public User checkMailWithPassword(String email, String password) throws IOException {
        
        Connection connection = ConnectorManager.getConnection();
        PreparedStatement stmt;
        User user = null;

        try {
            
            stmt = connection.prepareStatement("select * from "+ UserManager.TABLE +" where "+ UserManager.COL_EMAIL +"=?  and " + UserManager.COL_PASSWORD + "=?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                user = this.createFromResultSet(rs);
            }
            
            rs.close();
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return user;

    }

    /**
     * Return a created User object from a ResultSet
     * 
     * @return
     * @param ResultSet
     *            rs
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
        user.setAdmin(rs.getBoolean(UserManager.COL_ADMIN));

        return user;
    }

}
