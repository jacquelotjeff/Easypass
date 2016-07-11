package test.java.group;

import java.util.Map;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
import fr.easypass.model.Group;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class GroupManagerListTest extends TestCase {
    
    private static GroupManager groupManager;
    private static Map<Integer, Group> groupsDb;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        groupManager = new GroupManager();
        groupsDb = groupManager.getGroups();
    }
    
    @Parameters(name = "Getting groups")
    public static Object data() {
        
        return new Object[][] {
            {1, "Site Zoo", "Administrateur du site du Zoo", "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\elephants_8.1.2012_whytheymatter1_HI_247511.jpg"},
            {2, "Useless", "Groupe useless", "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\12670213_1957609551131792_6738362166244948893_n.png"},
            {3, "Page Facebook ESGI", "Facebook", "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\logo.png.jpg"},
            {4, "Forum Photographes", "Administrateur du forum de photographies", "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\appareil-photo-reflex.jpg"},
            {5, "Champions de Tennis", "Les meilleurs joueurs sur un seul site.", "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\slide-image-3.jpg"}
        };
        
    }
    
    @Parameter(0)
    public Integer id;

    @Parameter(1)
    public String name;
    
    @Parameter(2)
    public String description;
    
    @Parameter(3)
    public String logo;
    
    @Test
    public void testListGroup() {
                
        Group groupDb = groupsDb.get(id);
        
        assertNotNull(groupDb);
        
        assertEquals(id, groupDb.getId());
        assertEquals(name, groupDb.getName());
        assertEquals(description, groupDb.getDescription());
        assertEquals(logo, groupDb.getLogo());
       
    }
}

