package fr.easypass.test.java.group;

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
import fr.easypass.manager.GroupManager;
import fr.easypass.model.Group;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class GroupManagerDeleteTest extends TestCase {
    
    private static GroupManager groupManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Delete group {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"Useless", "Groupe useless", "12670213_1957609551131792_6738362166244948893_n.png", 2},
            }
        );
    }
    
    @Parameter(0)
    public String name;

    @Parameter(1)
    public String description;
    
    @Parameter(2)
    public String logo;
    
    @Parameter(3)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        groupManager = new GroupManager();
    }
    
    @Test
    public void testDeleteGroup(){
        try {
            
            log.log(Level.INFO, "We're testing an group deletion...");
            
            Group groupDb;
            groupDb = groupManager.getGroup(id);
            
            assertNotNull(groupDb);
            assertEquals(name, groupDb.getName());
            assertEquals(description, groupDb.getDescription());
            assertEquals(logo, groupDb.getLogo());
            
            Integer success = groupManager.deleteGroup(id);
            assertEquals(success, (Integer) 1);
            
            groupDb = groupManager.getGroup(id);
            
            assertNull(groupDb);
                    
            
        } catch (IOException e) {
            log.log(Level.SEVERE, "Impossible to get group from GroupManager", e);
        }
    }
}
