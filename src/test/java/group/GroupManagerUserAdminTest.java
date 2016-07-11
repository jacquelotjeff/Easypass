package test.java.group;

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
public class GroupManagerUserAdminTest extends TestCase {
    
    private static GroupManager groupManager;
    private static UserManager userManager;
    
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Insert group {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {4, 1, false},
                {4, 2, true},
            }
        );
    }
    
    @Parameter(0)
    public Integer groupId;

    @Parameter(1)
    public Integer userId;
    
    @Parameter(2)
    public Boolean admin;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        groupManager = new GroupManager();
        userManager = new UserManager();
    }
    
    @Test
    public void testUserAdminGroup(){
        
        try {
            
            Integer success = groupManager.setUserAdmin(groupId, userId, admin);
            assertEquals(1, success.intValue());
            
            Map<String, Map <Integer, User>> result = userManager.getUsersByGroup(groupId);
            
            Map<Integer, User> usersDb = result.get("groupUsers");
            Map<Integer, User> adminsDb = result.get("groupAdmins");
            
            assertTrue(usersDb.containsKey(userId));
            
            if (admin) {
                assertTrue(adminsDb.containsKey(userId));
            } else {
                assertFalse(adminsDb.containsKey(userId));
            }
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    

}
