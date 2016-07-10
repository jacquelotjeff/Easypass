package test.java.category;

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
    public static void setUpBeforeClass() throws Exception {
        categoryManager = new CategoryManager();
        categoriesDb = categoryManager.getCategories();
    }
    
    @Parameters(name = "Getting categories")
    public static Object data() {
        
        return new Object[][] {
            {1, "Forums", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\2365667.png"},
            {2, "Réseaux sociaux", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\menu-reseaux-sociaux.png"},
            {3, "Autre", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\License-unknown.png"},
            {4, "Boîte mail", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\2000px-Aiga_mail.svg.png"},
            {5, "Travail", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\Man-Black-icon.png"},
            {6, "Useless", "C:\\Users\\Adrien\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\easypass\\upload\\54150.png"},
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

