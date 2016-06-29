package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import fr.easypass.model.Group;
import fr.easypass.model.Password;

public class PasswordManager {

    HashMap<Integer, Password> passwords;

    public static final String TABLE = "passwords";
    public static final String TABLE_REL_OWNER = "owner_password";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_PASSWORD = "password";
    public static final String COL_INFORMATIONS = "informations";
    public static final String COL_URL_SITE = "urlSite";
    public static final String COL_FOREIGN = "password_id";

    /**
     * Return list of Users
     * 
     * @return
     * @throws IOException
     */
    public HashMap<Integer, Password> getPasswords() throws IOException {

        // Resetting the Hashmap (Prevent from caching users into)
        this.passwords = new HashMap<>();

        Connection conn = ConnectorManager.getConnection();

        try {

            // Not prepared statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + PasswordManager.TABLE + ";");

            while (rs.next()) {

                Password password = this.createFromResultSet(rs);
                password.setId(rs.getInt(PasswordManager.COL_ID));
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
    
    public Map<Integer, Password> getPasswordsByUser(Integer userId) throws IOException{
    		
       Map<Integer, Password> passwords = new HashMap<>();

       Connection conn = ConnectorManager.getConnection();

       try {

           String query = "SELECT DISTINCT * from " + PasswordManager.TABLE_REL_OWNER + " INNER JOIN "
                   + PasswordManager.TABLE + " u ON u." + PasswordManager.COL_ID + " = "
                   + PasswordManager.TABLE_REL_OWNER + "." + PasswordManager.COL_FOREIGN + " WHERE "
                   + UserManager.COL_FOREIGN + "=?";

           // Not prepared statement
           PreparedStatement stmt = conn.prepareStatement(query);
           stmt.setInt(1, userId);

           ResultSet rs = stmt.executeQuery();

           while (rs.next()) {
               Password password = this.createFromResultSet(rs);
               password.setId(rs.getInt(PasswordManager.COL_FOREIGN));
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
     * @throws IOException
     */
    public Password getPassword(Integer passwordId) throws IOException {

        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("SELECT * from " + PasswordManager.TABLE + " WHERE id=?");
            stmt.setInt(1, passwordId);

            ResultSet rs = stmt.executeQuery();

            Password password = null;
            while (rs.next()) {
                password = this.createFromResultSet(rs);
                password.setId(rs.getInt(PasswordManager.COL_ID));
            }

            rs.close();
            stmt.close();
            conn.close();

            return password;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Integer insertPassword(String title, String site, String password, Integer category, String informations,
            Integer owner, String ownerType) {

        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + PasswordManager.TABLE + "(" + 
                     PasswordManager.COL_TITLE + "," + 
                     CategoryManager.COL_FOREIGN + "," +
                     PasswordManager.COL_URL_SITE + "," + 
                     PasswordManager.COL_PASSWORD + "," + 
                     PasswordManager.COL_INFORMATIONS + ") values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, title);
            stmt.setInt(2, category);
            stmt.setString(3, site);
            stmt.setString(4, password);
            stmt.setString(5, informations);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.addOwner(generatedKeys.getInt(1), owner, ownerType);
                } else {
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

    public Integer editPassword(Integer passwordId, String title, String site, String password, Integer category,
            String informations) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("UPDATE " + PasswordManager.TABLE + " SET " + PasswordManager.COL_TITLE + "=?,"
                    + CategoryManager.COL_FOREIGN + "=?," + PasswordManager.COL_URL_SITE + "=?,"
                    + PasswordManager.COL_PASSWORD + "=?," + PasswordManager.COL_INFORMATIONS + "=? WHERE id=?;");

            stmt.setString(1, title);
            stmt.setInt(2, category);
            stmt.setString(3, site);
            stmt.setString(4, password);
            stmt.setString(5, informations);
            stmt.setInt(6, passwordId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public Integer deletePassword(Integer passwordId) throws IOException {

        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM " + PasswordManager.TABLE + " WHERE " + PasswordManager.COL_ID + "=?");

            stmt.setInt(1, passwordId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {

            e.printStackTrace();
            return 0;
        }

    }

    public Integer addOwner(Integer passwordId, Integer ownerId, String ownerType) throws IOException {
        
        try {

            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO " + PasswordManager.TABLE_REL_OWNER + 
                    "(" + UserManager.COL_FOREIGN + "," + GroupManager.COL_FOREIGN + "," + PasswordManager.COL_FOREIGN + 
                    ") values(?,?,?)"
            );
            
            if (ownerType.equals(Password.OWNER_TYPE_USER)) {
                stmt.setInt(1, ownerId);
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
                stmt.setInt(2, ownerId);
            }
            
            stmt.setInt(3, passwordId);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;

    }

    /**
     * Create a Group object from resultSet
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private Password createFromResultSet(ResultSet rs) throws SQLException {

        Password password = new Password();
        
        password.setTitle(rs.getString(PasswordManager.COL_TITLE));
        password.setCategory(rs.getInt(CategoryManager.COL_FOREIGN));
        password.setInformations(rs.getString(PasswordManager.COL_INFORMATIONS));
        password.setPassword(rs.getString(PasswordManager.COL_PASSWORD));
        password.setSiteUrl(rs.getString(COL_URL_SITE));

        return password;

    }
}
