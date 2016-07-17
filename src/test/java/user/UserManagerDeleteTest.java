package test.java.user;

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
import fr.easypass.manager.UserManager;
import fr.easypass.model.User;
import junit.framework.TestCase;

@RunWith(Parameterized.class)
public class UserManagerDeleteTest extends TestCase {
    
    private static UserManager userManager;
    public static final Logger log = Logger.getLogger(CategoryManager.class.getName());
    
    @Parameters(name = "Delete user {0}")
    public static List<Object[]> data() {
        
        return Arrays.asList(
            new Object[][] {
                {"useless", "Use", "Less", "7b902e6ff1db9f560443f2048974fd7d386975b0", "useless@gmail.com", false, 4},
            }
        );
    }
    
    @Parameter(0)
    public String username;

    @Parameter(1)
    public String firstname;
    
    @Parameter(2)
    public String lastname;
    
    @Parameter(3)
    public String password;
    
    @Parameter(4)
    public String email;
    
    @Parameter(5)
    public Boolean admin;
    
    @Parameter(6)
    public Integer id;
    
    @BeforeClass
    public static void setUpBeforeClass() throws InstantiationException {
        userManager = new UserManager();
    }
    
    @Test
    public void testDeleteUser(){
        try {
            
            log.log(Level.INFO, "We're testing an user deletion...");
            
            User userDb;
            userDb = userManager.getUser(id);
            assertEquals(username, userDb.getUsername());
            assertEquals(firstname, userDb.getFirstname());
            assertEquals(lastname, userDb.getLastname());
            assertEquals(password, userDb.getPassword());
            assertEquals(email, userDb.getEmail());
            assertEquals(admin, userDb.getAdmin());
            
            Integer success = userManager.deleteUser(id);
            assertEquals(success, (Integer) 1);
            
            userDb = userManager.getUser(id);
            
            assertNull(userDb);
                    
            
        } catch (IOException e) {
            log.log(Level.SEVERE, "Impossible to get user from UserManager", e);
        }
    }
}
