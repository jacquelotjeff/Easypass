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
    private static final String COL_REL_GROUPS_GROUP_ID = "group_id";

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

            String query = "SELECT DISTINCT * from " + UserManager.TABLE_NAME_REL_USERS + " INNER JOIN "
                    + UserManager.TABLE_NAME + " u ON u." + UserManager.COL_ID + " = "
                    + UserManager.TABLE_NAME_REL_USERS + "." + COL_REL_GROUPS_USER_ID + " WHERE "
                    + UserManager.COL_REL_GROUPS_GROUP_ID + "=?";

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

            query = "SELECT * from " + UserManager.TABLE_NAME + " WHERE " + UserManager.COL_ID + " NOT IN(" + "SELECT "
                    + UserManager.COL_REL_GROUPS_USER_ID + " FROM " + UserManager.TABLE_NAME_REL_USERS + " WHERE "
                    + UserManager.COL_REL_GROUPS_GROUP_ID + "=?);";
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

    public Integer insertUser(HttpServletRequest request) {

        // TODO Validation for parameters
        String username = request.getParameter(UserManager.COL_USERNAME);
        String firstname = request.getParameter(UserManager.COL_FIRSTNAME);
        String lastname = request.getParameter(UserManager.COL_LASTNAME);
        String password = request.getParameter(UserManager.COL_PASSWORD);
        String email = request.getParameter(UserManager.COL_EMAIL);

        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + UserManager.TABLE_NAME + "("
                    + UserManager.COL_USERNAME + ", " + UserManager.COL_FIRSTNAME + ", " + UserManager.COL_LASTNAME
                    + ", " + UserManager.COL_PASSWORD + ", " + UserManager.COL_EMAIL + ") values(?,?,?,?,?)");

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
    public Integer editUser(Integer userId, String username, String lastname, String firstname, String password, String email) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("UPDATE " + UserManager.TABLE_NAME + " SET " + UserManager.COL_USERNAME
                    + "=?, " + UserManager.COL_FIRSTNAME + "=?, " + UserManager.COL_LASTNAME + "=?, "
                    + UserManager.COL_PASSWORD + "=?, " + UserManager.COL_EMAIL + "=?" + " WHERE id=?");

            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, password);
            stmt.setString(5, email);

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

    public Integer deleteUser(Integer userId) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM " + UserManager.TABLE_NAME + " WHERE " + UserManager.COL_ID + "=?");

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
     * Check if User and Password corresponds
     * 
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    public Boolean checkLoginWithPassword(String username, String password) throws IOException {
        // TODO request by login and check the password
        Map<Integer, User> users = this.getUsers();
        User user = users.get(username);
        return (user.getPassword().equals(password));
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

        return user;
    }

}
