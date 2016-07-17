package test.java.group;

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
import fr.easypass.manager.GroupManager;
import fr.easypass.model.Group;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class GroupManagerEditTest extends TestCase {
    
    private static GroupManager groupManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Edit group {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"Forum Photographies", "Administrateur du forum de photographes",  "appareil-photo-reflex.jpg", 4},
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
    public static void setUpBeforeClass() throws InstantiationException {
        groupManager = new GroupManager();
    }
    
    @Test
    public void testEditGroup(){
        
        Group  group = new Group(); 
        
        group.setName(name);
        group.setDescription(description);
        group.setLogo(logo);
        
        assertEquals(0, group.isValid().size());
        
        //If group is valid check the insert in database.
        Integer success;
        try {
            
            success = groupManager.editGroup(id, name, description, logo);
            log.log(Level.INFO, "We're checking to edit group in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                Map<Integer, Group> groups = groupManager.getGroups();
                
                Group groupDb = groups.get(id);
                
                log.log(Level.INFO, "We're checking the group is correctly edited...");
                assertEquals(name, groupDb.getName());
                assertEquals(description, groupDb.getDescription());
                assertEquals(logo, groupDb.getLogo());
                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get groups", e);
            }
            
        } catch (IOException e1) {
            log.log(Level.SEVERE, "Impossible to edit a group from GroupManager (editGroup)", e1);
        }
    }
}
