package fr.easypass.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import fr.easypass.model.Category;

public class CategoryManager {
    
private Map<Integer, Category> categories;
    
    public static final String TABLE = "categories";
        
    public static final String COL_ID = "id"; 
    public static final String COL_NAME = "name";
    public static final String COL_LOGO = "logo";
    public static final String COL_FOREIGN = "category_id";
    
    public CategoryManager()
    {
    }
    
    /**
     * Return Category object if existing into data
     * 
     * @param categoryId
     * @return
     * @throws IOException 
     */
    public Category getCategory(Integer categoryId) throws IOException {
        
        try {
            
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("SELECT * from "+CategoryManager.TABLE+" WHERE "+CategoryManager.COL_ID+"=?");
            stmt.setInt(1, categoryId);

            ResultSet rs = stmt.executeQuery();
            
            Category category = null;
            while (rs.next()) {
                category = this.createFromResultSet(rs);
            }
                    
            rs.close();
            stmt.close();
            conn.close();

            return category;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public Map<Integer, Category> getCategories() throws IOException {
        //Resetting the Hashmap (Prevent from caching groups into)
        this.categories = new HashMap<Integer, Category>();
        
        Connection conn = ConnectorManager.getConnection();

        try {
            
            //Not prepared statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from " + CategoryManager.TABLE + ";");

            while (rs.next()) {
                
                Category category = this.createFromResultSet(rs);
                this.categories.put(category.getId(), category);

            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.categories;
    }
    
    public Integer insertCategory(String name, String logo) {

        try {
            
            //On commence par insérer les groupe dans la table en récupérant son nouvel identifiant.
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO "+CategoryManager.TABLE+"("+ CategoryManager.COL_NAME + 
                    "," + CategoryManager.COL_LOGO + 
                    ") values(?,?)", Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, name);
            stmt.setString(2, logo);
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
         
            return 1;

        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
        
    }
    
    public Integer editCategory(Integer categoryId, String name, String logo) throws IOException{
        
        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt;

            stmt = conn.prepareStatement("UPDATE " + CategoryManager.TABLE + 
            " SET " + CategoryManager.COL_NAME + "=?, " +
            CategoryManager.COL_LOGO + "=? WHERE id=?;");

            stmt.setString(1, name);
            stmt.setString(2, logo);
            stmt.setInt(3, categoryId);

            Integer number = stmt.executeUpdate();
            stmt.close();
            conn.close();

            return number;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
    }
    
    public Integer deleteCategory(Integer categoryId) throws IOException {
        
        try {
            Connection conn = ConnectorManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + CategoryManager.TABLE + " WHERE " + CategoryManager.COL_ID + "=?");
            
            stmt.setInt(1, categoryId);

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
     * Create a Category object from resultSet
     * @param rs
     * @return
     * @throws SQLException
     */
    private Category createFromResultSet(ResultSet rs) throws SQLException{
        
        Category category = new Category();

        category.setId(rs.getInt(CategoryManager.COL_ID));
        category.setName(rs.getString(CategoryManager.COL_NAME));
        category.setLogo(rs.getString(CategoryManager.COL_LOGO));

        return category;

    }

}
