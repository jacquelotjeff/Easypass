package test.java.category;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class CategoryManagerInsertTest extends TestCase {
    
    private static CategoryManager categoryManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Insert category {3}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"Divertissement", "hobbies.png", 0, 7},
                {"Invalide 1", "", 1, 8},
                {"", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\2365667.png", 1, 9},
            }
        );
    }
    
    @Parameter(0)
    public String name;

    @Parameter(1)
    public String logo;
    
    @Parameter(2)
    public Integer countErrors;
    
    @Parameter(3)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException {
    	categoryManager = new CategoryManager();
    }
    
    @Test
    public void testInsertCategory(){
        
        Category  category = new Category(); 
        
        category.setName(name);
        category.setLogo(logo);
        
        log.log(Level.INFO, "We're checking the category validation...");
        assertEquals(countErrors, (Integer) category.isValid().size());
        
        //If user is valid check the insert in database.
        if (countErrors == 0) {
            Integer success = categoryManager.insertCategory(name, logo);
            log.log(Level.INFO, "We're checking to insert category in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                
                Map<Integer, Category> categories = categoryManager.getCategories();
                
                Category categoryDb = categories.get(id);
                
                assertNotNull(categoryDb);
                
                log.log(Level.INFO, "We're checking the category is correctly inserted...");
                assertEquals(name, categoryDb.getName());
                assertEquals(logo, categoryDb.getLogo());
                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get categories", e);
            }
            
        }
    }
}
