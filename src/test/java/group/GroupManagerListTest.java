package test.java.group;

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
import fr.easypass.manager.GroupManager;
import fr.easypass.model.Group;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class GroupManagerListTest extends TestCase {
    
    private static GroupManager groupManager;
    private static Map<Integer, Group> groupsDb;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException, IOException {
        groupManager = new GroupManager();
        groupsDb = groupManager.getGroups();
    }
    
    @Parameters(name = "Getting groups")
    public static Object data() {
        
        return new Object[][] {
            {1, "Site Zoo", "Administrateur du site du Zoo", "Monkey-Mia-MJK-Australia-650x433.jpg"},
            {2, "Useless", "Groupe useless", "12670213_1957609551131792_6738362166244948893_n.png"},
            {3, "Page Facebook ESGI", "Facebook", "logo.png.jpg"},
            {4, "Forum Photographes", "Administrateur du forum de photographies", "fake-path.jpg"},
            {5, "Champions de Tennis", "Les meilleurs joueurs sur un seul site.","slide-image-3.jpg"}
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

