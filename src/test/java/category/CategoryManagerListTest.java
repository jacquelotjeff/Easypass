package test.java.category;

import java.io.IOException;
import java.util.Map;
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
public class CategoryManagerListTest extends TestCase {
    
    private static CategoryManager categoryManager;
    private static Map<Integer, Category> categoriesDb;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException, IOException {
        categoryManager = new CategoryManager();
        categoriesDb = categoryManager.getCategories();
    }
    
    @Parameters(name = "Getting categories")
    public static Object data() {
        
        return new Object[][] {
            {1, "Forums", "forum1.png"},
            {2, "Réseaux sociaux", "social.png"},
            {3, "Autre", "fake-path.png"},
            {4, "Boîte mail", "mail_2.png"},
            {5, "Travail", "Sad-after-having-a-work-load.png"},
            {6, "Useless", "12670213_1957609551131792_6738362166244948893_n.png"},
        };
        
    }
    
    @Parameter(0)
    public Integer id;

    @Parameter(1)
    public String name;
    
    @Parameter(2)
    public String logo;
    
    @Test
    public void testListCategory() {
                
        Category categoryDb = categoriesDb.get(id);
        
        assertNotNull(categoryDb);
        
        assertEquals(id, categoryDb.getId());
        assertEquals(name, categoryDb.getName());
        assertEquals(logo, categoryDb.getLogo());
    }
}

