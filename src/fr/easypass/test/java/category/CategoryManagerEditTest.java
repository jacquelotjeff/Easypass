package fr.easypass.test.java.category;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.easypass.manager.CategoryManager;
import fr.easypass.model.Category;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class CategoryManagerEditTest extends TestCase {
    
    private static CategoryManager categoryManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Edit category {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"Autres", "License-unknown.png", 3}
            }
        );
    }
    
    @Parameter(0)
    public String name;

    @Parameter(1)
    public String logo;
    
    @Parameter(2)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        categoryManager = new CategoryManager();
    }
    
    @Test
    public void testEditCategory(){
        
    	Category category = new Category(); 
        
    	category.setName(name);
    	category.setLogo(logo);
    	
        assertEquals(0, category.isValid().size());
        
        //If user is valid check the insert in database.
        Integer success;
        try {
            
            success = categoryManager.editCategory(id, name, logo);
            log.log(Level.INFO, "We're checking to edit category in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                Category catgoryDb = categoryManager.getCategory(id);
                
                log.log(Level.INFO, "We're checking the category is correctly edited...");
                assertEquals(name, catgoryDb.getName());
                assertEquals(logo, catgoryDb.getLogo());
                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get users", e);
            }
            
        } catch (IOException e1) {
            log.log(Level.SEVERE, "Impossible to edit a category from CategoryManager (editCategory)", e1);
        }
    }
}
