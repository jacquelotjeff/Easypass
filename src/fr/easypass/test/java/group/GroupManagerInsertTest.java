package fr.easypass.test.java.group;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Group;
import fr.easypass.model.User;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class GroupManagerInsertTest extends TestCase {
    
    private static GroupManager groupManager;
    private static UserManager userManager;
    
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Insert group {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {6, "Youtube", "Chaîne Youtube",  "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\New_youtube_logo.png", "1;2;3", "1;3", 0},
                {7, "Useless edited", "",  "C:\\Users\\Adrien\\workspace\\easypass\\target\\m2e-wtp\\web-resources\\upload\\edited.png", "1|2|3", "1", 1},
                {8, "Useless edited", "Description edited",  "", "1|2|3", "1", 1},
            }
        );
    }
    
    @Parameter(0)
    public Integer id;

    @Parameter(1)
    public String name;
    
    @Parameter(2)
    public String description;
    
    @Parameter(3)
    public String logo;
    
    @Parameter(4)
    public String usersStr;
    
    @Parameter(5)
    public String adminsStr;
    
    @Parameter(6)
    public int countErrors;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        groupManager = new GroupManager();
        userManager = new UserManager();
    }
    
    @Test
    public void testInsertGroup(){
        
        Group  group = new Group(); 
        
        group.setName(name);
        group.setDescription(description);
        group.setLogo(logo);
        
        List<String> users = Arrays.asList(usersStr.split(";"));
        List<String> admins = Arrays.asList(adminsStr.split(";"));
        
        log.log(Level.INFO, "We're checking the group validation...");
        assertEquals(countErrors, group.isValid().size());
        
        //If group is valid check the insert in database.
        if (countErrors == 0) {
            Integer success = groupManager.insertGroup(name, description, logo, users, admins);
            
            log.log(Level.INFO, "We're checking to insert group in database...");
            assertEquals(success, (Integer) 1);
            
            try {
                
                Map<Integer, Group> groups = groupManager.getGroups();
                
                Group groupDb = groups.get(id);
                
                assertNotNull(groupDb);
                
                log.log(Level.INFO, "We're checking the group is correctly inserted...");
                
                assertEquals(name, groupDb.getName());
                assertEquals(description, groupDb.getDescription());
                assertEquals(logo, groupDb.getLogo());
                
                //TODO Check users
                Map<String, Map <Integer, User>> result = userManager.getUsersByGroup(id);
                
                Map<Integer, User> usersDb = result.get("groupUsers");
                Map<Integer, User> adminsDb = result.get("groupAdmins");
                
                for (String userId : users) {
                    User userDb = usersDb.get(NumberUtils.toInt(userId));
                    assertNotNull(userDb);
                }
                
                for (String adminId : admins) {
                    User adminDb = adminsDb.get(NumberUtils.toInt(adminId));
                    assertNotNull(adminDb);
                }
                
            } catch (IOException e) {
                log.log(Level.SEVERE, "Impossible to get groups", e);
            }
            
        }
    }
}
