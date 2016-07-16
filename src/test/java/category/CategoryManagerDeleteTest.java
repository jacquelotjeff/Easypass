package test.java.category;

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
public class CategoryManagerDeleteTest extends TestCase {
    
    private static CategoryManager categoryManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Delete cateogry {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"Useless", "12670213_1957609551131792_6738362166244948893_n.png", 6},
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
    public void testDeleteCategory(){
        
    	try {
            
            log.log(Level.INFO, "We're testing an category deletion...");
            
            Category categoryDb;
            categoryDb = categoryManager.getCategory(id);
            assertEquals(name, categoryDb.getName());
            assertEquals(logo, categoryDb.getLogo());
            
            Integer success = categoryManager.deleteCategory(id);
            assertEquals(success, (Integer) 1);
            
            categoryDb = categoryManager.getCategory(id);
            
            assertNull(categoryDb);
                    
            
        } catch (IOException e) {
            log.log(Level.SEVERE, "Impossible to get user from UserManager", e);
        }
    	
    }
}
